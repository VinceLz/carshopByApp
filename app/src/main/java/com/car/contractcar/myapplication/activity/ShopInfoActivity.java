package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.ui.LoadingPage;
import com.car.contractcar.myapplication.common.utils.AppUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.entity.CarInfo;
import com.car.contractcar.myapplication.entity.ShopInfo;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopInfoActivity extends AppCompatActivity {

    private static final String TAG = "XUZI";
    @BindView(R.id.view_pages_shopimg)
    RollPagerView viewPagesShopimg;
    @BindView(R.id.shop_car_list)
    ListView shopCarList;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.shop_address)
    TextView shopAddress;
    @BindView(R.id.shop_owner)
    TextView shopOwner;
    @BindView(R.id.shop_distance)
    TextView shopDistance;
    @BindView(R.id.shop_back)
    ImageView shopBack;
    @BindView(R.id.b_phone)
    ImageView phone;
    @BindView(R.id.location)
    ImageView location;
    private int bid;
    private ShopInfo shopInfo;
    private List<ShopInfo.BusinessBean.ChildsBean> cars;
    private int distance;
    private LoadingPage loadingPage;
    private LoadingDialog dialog;
    private int gid;
    String bphone;
    String longitude = null;
    String latitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        initView();
        initData();
        //打电话
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.intent.setData(Uri.parse("tel:" + bphone));
                startActivity(Constant.intent);
            }
        });
        //导航
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtil.openMap(ShopInfoActivity.this, shopInfo.getBusiness().getBname(), latitude, longitude)) {
                    UIUtils.Toast("您没有安装地图App，暂时无法导航", false);// TODO: 16/11/29
                }
            }
        });
        shopCarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final LoadingDialog loadingDialog = new LoadingDialog(ShopInfoActivity.this, "加载中...");
                loadingDialog.show();
                ShopInfo.BusinessBean.ChildsBean childsBean = cars.get(position);
                gid = childsBean.getGid();
                double minprice = childsBean.getMinprice();
                double maxprice = childsBean.getMaxprice();
                String gname = childsBean.getGname();
                String title = childsBean.getTitle();

                HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_CAR_INFO + gid, new HttpUtil.callBlack() {
                    @Override
                    public void succcess(final String code) {
                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ShopInfoActivity.this, CarInfoActivity.class);
                                intent.putExtra("gid", gid);
                                intent.putExtra("code", code);
                                intent.putExtra("phone", bphone);
                                intent.putExtra("latitude", latitude);
                                intent.putExtra("longitude", longitude);
                                intent.putExtra("distance", distance);
                                intent.putExtra("bname", shopInfo.getBusiness().getBname());
                                loadingDialog.dismiss();
                                loadingDialog.close();
                                startActivity(intent);
                                //    右往左推出效果
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                            }
                        });

                    }

                    @Override
                    public void fail(String code) {

                    }

                    @Override
                    public void err() {

                    }
                }, 3600 * 2);


            }
        });
    }

    private void initView() {
        dialog = new LoadingDialog(this, "玩命加载中...");
        dialog.show();
        //设置播放时间间隔
        viewPagesShopimg.setPlayDelay(3000);
        //设置透明度
        viewPagesShopimg.setAnimationDurtion(500);


        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        viewPagesShopimg.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));
        shopCarList.setFocusable(false);

    }

    /**
     * 从网络中获取数据
     */
    private void initData() {
        Intent intent = getIntent();
        //  bid = intent.getIntExtra("bid", 1);
        longitude = intent.getStringExtra("longitude");
        latitude = intent.getStringExtra("latitude");
        bphone = intent.getStringExtra("phone");
        distance = Integer.valueOf(intent.getStringExtra("distance"));
        String code = intent.getStringExtra("code");
        shopInfo = (ShopInfo) JsonUtils.json2Bean(code, ShopInfo.class);
        refreshView();
//        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_SHOP_INFO + bid, new HttpUtil.callBlack() {
//            @Override
//            public void succcess(String code) {
//                shopInfo = (ShopInfo) JsonUtils.json2Bean(code, ShopInfo.class);
//                UIUtils.runOnUIThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshView();
//                    }
//                });
//
//            }
//
//            @Override
//            public void fail(String code) {
//
//            }
//
//            @Override
//            public void err() {
//
//            }
//        }, false);
    }

    /**
     * 刷新UI
     */
    private void refreshView() {
        dialog.close();
        if (shopInfo.getBusiness() != null) {
            cars = shopInfo.getBusiness().getChilds();
            viewPagesShopimg.setAdapter(new StaticPagerAdapter() {

                @Override
                public View getView(ViewGroup container, int position) {
                    Log.e(TAG, "getView: " + shopInfo.getBusiness().getBimage().get(position));
                    SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                    ImageLoad.loadImg(imageView, shopInfo.getBusiness().getBimage().get(position));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    return imageView;
                }

                @Override
                public int getCount() {
                    return shopInfo.getBusiness().getBimage() != null ? shopInfo.getBusiness().getBimage().size() : 0;
                }
            });
            ShopInfo.BusinessBean business = shopInfo.getBusiness();
            shopName.setText(business.getBname());
            shopAddress.setText(business.getBaddress());
            shopOwner.setText("主营车型 : " + business.getMajorbusiness());

            if (distance < 1000) {
                shopDistance.setText(distance + "m");
            } else {
                float a = (float) distance / 1000;
                DecimalFormat fnum = new DecimalFormat("##.0");
                shopDistance.setText(fnum.format(a) + "Km");
            }
            if (cars != null && cars.size() > 0) {
                shopCarList.setAdapter(new MyAdapter());
                setListViewHeight(shopCarList);
            }

        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cars.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = UIUtils.getXmlView(R.layout.shop_cars_list);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ImageLoad.loadImg(viewHolder.shopCarImg, cars.get(position).getGshowImage());
            viewHolder.shopCarName.setText(cars.get(position).getGname());
            viewHolder.shopCarPrice.setText("指导价: " + cars.get(position).getMinprice() + "万 ~ " + cars.get(position).getMaxprice() + "万");
            String title = cars.get(position).getTitle();
            if (!TextUtils.isEmpty(title)) {
                viewHolder.shopCarTitle.setText(title);
            } else {
                viewHolder.shopCarTitle.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

    }

    static class ViewHolder {
        @BindView(R.id.shop_car_img)
        SimpleDraweeView shopCarImg;
        @BindView(R.id.shop_car_name)
        TextView shopCarName;
        @BindView(R.id.shop_car_price)
        TextView shopCarPrice;
        @BindView(R.id.shop_car_title)
        TextView shopCarTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @OnClick(R.id.shop_back)
    public void back(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 重新计算listview的高度
     *
     * @param
     */
    public void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
