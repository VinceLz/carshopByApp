package com.car.contractcar.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.ui.widget.XListView;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.entity.SelectData;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

public class CarModelsActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private static final String TAG = "XUZI";
    @BindView(R.id.car_models_pages_carousel)
    RollPagerView carModelsPagesCarousel;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.car_models_ok)
    LinearLayout carModelsOk;
    @BindView(R.id.activity_car_models)
    LinearLayout activityCarModels;
    @BindView(R.id.car_models_back)
    EduSohoIconView carModelsBack;
    private SelectData selectData;
    private List<SelectData.ResultBean> datas = new ArrayList<>();
    private SelectData.ParamsBean param;
    private int currentPage = 1;
    boolean isRef = false;
    XListView carModelsList;
    MyXlistViewAdapt myXlistViewAdapt;
    float mLastY = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_models);
        ButterKnife.bind(this);
        carModelsList = (XListView) findViewById(R.id.car_models_list);
        initView();
        initData();
//        carModelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(CarModelsActivity.this, CarInfoActivity.class);
//                intent.putExtra("gid", datas.get(position).getGid());
//                CarModelsActivity.this.startActivity(intent);
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//            }
//        });
        carModelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int gid = datas.get(position - 1).getGid();
                HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_CAR_INFO + gid, new HttpUtil.callBlack() {
                    @Override
                    public void succcess(final String code) {
                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(CarModelsActivity.this, CarInfoActivity.class);
                                intent.putExtra("gid", gid);
                                intent.putExtra("code", code);
                                intent.putExtra("distance", 0);
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
        currentPage = 1;//默认是第一页
        carModelsList.setXListViewListener(this);
        carModelsList.setPullLoadEnable(true);
        carModelsList.setAutoLoadEnable(false);
        carModelsList.setPullRefreshEnable(false);
        carModelsPagesCarousel.setPlayDelay(3000);
        carModelsPagesCarousel.setAnimationDurtion(500);
        carModelsPagesCarousel.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));
    }

    private void initData() {
        String data = this.getIntent().getStringExtra("data");
        selectData = (SelectData) JsonUtils.json2Bean(data, SelectData.class);
        refreshView();
    }

    private void refreshView() {
        if (selectData != null) {
            carModelsPagesCarousel.setAdapter(new StaticPagerAdapter() {
                @Override
                public View getView(ViewGroup container, int position) {
                    SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    ImageLoad.loadImg(imageView, selectData.getImage().get(position).getImage());
                    return imageView;
                }

                @Override
                public int getCount() {
                    return selectData.getImage() != null ? selectData.getImage().size() : 0;
                }
            });
            if (selectData.getResult() != null) {
                datas.clear();
                datas.addAll(selectData.getResult());
                Log.d("----", "初始化加载的个数为" + datas.size());
                param = selectData.getParams();
                myXlistViewAdapt = new MyXlistViewAdapt();
                Log.e(TAG, "refreshView: " + datas.size());
                if (selectData.getResult().size() == 0) {
                    Log.d("----", "selectDate size=0");
                    carModelsList.mIsFooterReady = true;
                }
                carModelsList.setAdapter(myXlistViewAdapt);
                // setListViewHeight(carModelsList);
            }
        }

    }

    @Override
    public void onRefresh() {
        Log.d("----", "刷新了");
    }

    @Override
    public void onLoadMore() {
        if (!isRef) {
            isRef = true;
            Log.d("lz----", "下拉刷新");
            //去分页式的请求数据
            int pageNo = param.getPageNo();
            pageNo++;
            param.setPageNo(pageNo);
            String paramss = JsonUtils.bean2Json(param);
            Log.d("lz----", "分页请求的数据是" + paramss);
            HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_SELECT + "/" + paramss + ".action", new HttpUtil.callBlack() {
                @Override
                public void succcess(String code) {
                    SelectData selectData1 = (SelectData) JsonUtils.json2Bean(code, SelectData.class);
                    if (selectData1.getResult().size() == 0 || selectData1.getResult() == null) {
                        //没有下一页了
                        Log.d("----", "没有下一页了");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CarModelsActivity.this, "没有下一页了", Toast.LENGTH_SHORT).show();
                                onLoad();
                                return;
                            }
                        });
                    } else {
                        datas.addAll(selectData1.getResult());
                        Log.d("----", "加载的个数为" + datas.size());

                        param = selectData1.getParams();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onLoad();
                                myXlistViewAdapt.notifyDataSetChanged();
                            }
                        });

                    }

                }

                @Override
                public void fail(String code) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLoad();
                            Toast.makeText(CarModelsActivity.this, "出现异常", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                @Override
                public void err() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLoad();
                            Toast.makeText(CarModelsActivity.this, "出现异常", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }, 3600 * 2);
        }
    }


    private void onLoad() {
        isRef = false;
        carModelsList.stopLoadMore();
    }

    class MyXlistViewAdapt extends BaseAdapter {
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holderView = null;
            if (convertView == null) {
                convertView = UIUtils.getXmlView(R.layout.car_models_list_item);
                holderView = new ViewHolder(convertView);
                convertView.setTag(holderView);
            } else {
                holderView = (ViewHolder) convertView.getTag();
            }
            //HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(datas.get(position).getGshowImage())).into(holderView.carModelsListItemImg);
            ImageLoad.loadImg(holderView.carModelsListItemImg, datas.get(position).getGshowImage());
            holderView.carModelsListItemName.setText(datas.get(position).getGname());
            holderView.carModelsListItemPrice.setText("指导价 : " + datas.get(position).getMinprice() + "~" + datas.get(position).getMinprice());
            if (!TextUtils.isEmpty(datas.get(position).getTitle())) {
                holderView.carModelsListItemTitle.setText(datas.get(position).getTitle());
            } else {
                holderView.carModelsListItemTitle.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }

    /**
     * 重新计算listview的高度
     *
     * @param listView
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


    @OnClick(R.id.car_models_ok)
    public void selectCarModels(View view) {
        startActivity(new Intent(this, SpecActivity.class));
        this.finish();
        this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @OnClick(R.id.car_models_back)
    public void back(View view) {
        this.onBackPressed();
    }


//    @Override
//    public void onLoadMore() {
//
//    }


    static class ViewHolder {
        @BindView(R.id.car_models_list_item_img)
        SimpleDraweeView carModelsListItemImg;
        @BindView(R.id.car_models_list_item_name)
        TextView carModelsListItemName;
        @BindView(R.id.car_models_list_item_price)
        TextView carModelsListItemPrice;
        @BindView(R.id.car_models_list_item_title)
        TextView carModelsListItemTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
