package com.car.contractcar.myapplication.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.CarModelsActivity;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.activity.ShopInfoActivity;
import com.car.contractcar.myapplication.activity.SpecActivity;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.ui.LoadingPage;
import com.car.contractcar.myapplication.common.utils.AppUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.entity.BuyCarIndex2;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.loopj.android.http.RequestParams;
import com.tencent.bugly.beta.Beta;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.car.contractcar.myapplication.MyApplication.context;
import static com.car.contractcar.myapplication.common.utils.Constant.intent;
import static com.car.contractcar.myapplication.common.utils.UIUtils.getContext;
import static com.car.contractcar.myapplication.common.utils.UIUtils.runOnUIThread;

/**
 * Created by macmini2 on 16/11/5.
 */

public class BuycarFragment2 extends Fragment {
    @BindView(R.id.car_list)
    ListView carList;
    LinearLayout selectOk;
    HTextView hTextView;
    LinearLayout carModels;
    private BuyCarIndex2 buyCArIndex2;
    private List<BuyCarIndex2.HomeImageBean> homeImage = new ArrayList<>();
    private List<BuyCarIndex2.CarstoreBean> homeCarstore = new ArrayList<>();
    private List<BuyCarIndex2.HomeActiveBean> homeActive = new ArrayList<>();
    private LoadingPage loadingPage;
    private int statusBarHeight2;
    private static int count = 0;
    private boolean isContinue;
    RollPagerView mRollViewPager;
    StaticPagerAdapter staticPagerAdapter;
    MyListAdapter myListAdapter;
    public int status = 0;
    private Thread myThread;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                hTextView.animateText(homeActive.get(count % homeActive.size()).getTitle());
                count++;
                handler.sendEmptyMessageDelayed(0, 2000);

            }
        }
    };

    private String dd;
    private DecimalFormat fnum;
    private int[] position1;
    private int f;
    Location location;
    private BDLocation bdLocation;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        bdLocation = MainActivity.bdLocation;
        loadingPage.show();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(getActivity()) {
            @Override
            public int LayoutId() {
                return R.layout.fragment_index2;
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(BuycarFragment2.this, successView);
                if (!TextUtils.isEmpty(resultState.getContent())) {
                    if (buyCArIndex2 != null) {
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                initView();
                                refreshView();
                            }
                        });
                    } else {
                        buyCArIndex2 = (BuyCarIndex2) JsonUtils.json2Bean(resultState.getContent(), BuyCarIndex2.class);
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                initView();
                                refreshView();
                            }
                        });
                    }
                }
            }

            @Override
            protected RequestParams params() {
                return null;
            }

            @Override
            protected String url() {
                if (bdLocation != null) {
                    Log.e(TAG, Constant.HTTP_BASE + Constant.HTTP_HOME + "?longitude=" + bdLocation.getLongitude() + "&latitude=" + bdLocation.getLatitude());
                    return Constant.HTTP_BASE + Constant.HTTP_HOME + "?longitude=" + bdLocation.getLongitude() + "&latitude=" + bdLocation.getLatitude();
                } else {
                    return Constant.HTTP_BASE + Constant.HTTP_HOME;
                }
            }
        };
        return loadingPage;
    }

    public String TAG = "TAG";

    /**
     * 初始化ui
     */
    private void initView() {
        View xmlView = UIUtils.getXmlView(R.layout.buycar_hread);
        LinearLayout carModels = (LinearLayout) xmlView.findViewById(R.id.car_models);
        mRollViewPager = (RollPagerView) xmlView.findViewById(R.id.view_pages_carousel);
        hTextView = (HTextView) xmlView.findViewById(R.id.htext);
        LinearLayout selectOk = (LinearLayout) xmlView.findViewById(R.id.select_ok);
        mRollViewPager.setPlayDelay(2500);
        mRollViewPager.setAnimationDurtion(500);
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.WHITE, Color.parseColor("#aacccccc")));

        selectOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuycarFragment2.this.selectProgramme(v);
            }
        });
        carModels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuycarFragment2.this.carModels(v);
            }
        });


        carList.addHeaderView(xmlView);

        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("----", "size=" + homeCarstore.size() + "----" + position);
                final int item = (int) parent.getAdapter().getItem(position);
                final LoadingDialog loadingDialog = new LoadingDialog(getContext(), "加载中...");
                loadingDialog.show();
                HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_SHOP_INFO + homeCarstore.get(item).getBid(), new HttpUtil.callBlack() {
                    @Override
                    public void succcess(final String code) {
                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getActivity(), ShopInfoActivity.class);
                                // intent.putExtra("bid", homeCarstore.get(position).getBid());
                                intent.putExtra("distance", buyCArIndex2.getCarstore().get(item).getDistance() != null ?
                                        buyCArIndex2.getCarstore().get(item).getDistance() + "" : "0")
                                ;
                                intent.putExtra("phone", buyCArIndex2.getCarstore().get(item).getBphone());
                                intent.putExtra("code", code);
                                intent.putExtra("longitude", buyCArIndex2.getCarstore().get(item).getLongitude());
                                intent.putExtra("latitude", buyCArIndex2.getCarstore().get(item).getLatitude());
                                loadingDialog.dismiss();
                                loadingDialog.close();
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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


        hTextView.setAnimateType(HTextViewType.ANVIL);


    }

    /**
     * 刷新UI
     */
    public void refreshView() {

        if (buyCArIndex2 != null) {
            handler.sendEmptyMessage(0);
            //设置适配器
            homeImage.clear();
            homeCarstore.clear();
            homeActive.clear();
            homeImage.addAll(buyCArIndex2.getHomeImage());
            homeCarstore.addAll(buyCArIndex2.getCarstore());
            homeActive.addAll(buyCArIndex2.getHomeActive());
            if (staticPagerAdapter == null) {
                staticPagerAdapter = new StaticPagerAdapter() {
                    @Override
                    public View getView(ViewGroup container, int position) {
                        SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        ImageLoad.loadImg(imageView, homeImage.get(position).getImage());
                        return imageView;
                    }

                    @Override
                    public int getCount() {
                        return homeImage.size();
                    }
                };
                mRollViewPager.setAdapter(staticPagerAdapter);
            } else {
                staticPagerAdapter.notifyDataSetChanged();
            }
            if (myListAdapter == null) {
                myListAdapter = new MyListAdapter();
                carList.setAdapter(myListAdapter);
            } else {
                myListAdapter.notifyDataSetChanged();
            }

//            mRollViewPager.setAdapter(new StaticPagerAdapter() {
//                @Override
//                public View getView(ViewGroup container, int position) {
//                    SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                    ImageLoad.loadImg(imageView, homeImage.get(position).getImage());
//                    return imageView;
//                }
//
//                @Override
//                public int getCount() {
//                    return homeImage.size();
//                }
//            });
            //  carList.setAdapter(new MyListAdapter());

        }
        status = 1;
    }


    public void selectProgramme(View view) {
        startActivity(new Intent(getActivity(), SpecActivity.class));
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void carModels(View view) {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_SELECT + "/.action", new HttpUtil.callBlack() {
            @Override
            public void succcess(final String code) {
                Log.e(TAG, "succcess: " + code);
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), CarModelsActivity.class);
                        intent.putExtra("data", code);
                        getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void fail(String code) {
                Log.e(TAG, "fail: " + code);
            }

            @Override
            public void err() {
                Log.e(TAG, "err: ");
            }
        }, 0);

    }

    public BuyCarIndex2 getBuyCArIndex2() {
        return buyCArIndex2;
    }

    public void setBuyCArIndex2(BuyCarIndex2 buyCArIndex2) {
        this.buyCArIndex2 = buyCArIndex2;
    }

    /**
     * listView的适配器
     */
    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return homeCarstore.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d("----", "--" + position);
            ViewHolder holderView = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.car_item, null);
                holderView = new ViewHolder(convertView);
                convertView.setTag(holderView);
            } else {
                holderView = (ViewHolder) convertView.getTag();
            }
            holderView.storeAddress.setText(homeCarstore.get(position).getBaddress());
            holderView.storeName.setText(homeCarstore.get(position).getBname());
            holderView.carOwner.setText("主营车型 : " + homeCarstore.get(position).getMajorbusiness());

            if (!TextUtils.isEmpty(homeCarstore.get(position).getTitle1())) {
                holderView.homeTitle1Ly.setVisibility(View.VISIBLE);
                holderView.homeTitle1Text.setText(homeCarstore.get(position).getTitle1());
            } else {
                holderView.homeTitle1Ly.setVisibility(View.INVISIBLE);
            }
            if (!TextUtils.isEmpty(homeCarstore.get(position).getTitle2())) {
                holderView.homeTitle2Ly.setVisibility(View.VISIBLE);
                holderView.homeTitle2Text.setText(homeCarstore.get(position).getTitle2());
            } else {
                holderView.homeTitle2Ly.setVisibility(View.INVISIBLE);
            }

            f = Integer.parseInt(homeCarstore.get(position).getDistance() != null ?
                    homeCarstore.get(position).getDistance() : "0");
            if (f < 1000) {
                holderView.distance.setText(f + "m");
            } else {
                float a = (float) f / 1000;
                fnum = new DecimalFormat("##.0");
                dd = fnum.format(a);
                holderView.distance.setText(dd + "Km");
            }
            ImageLoad.loadImg(holderView.carImg, homeCarstore.get(position).getBshowImage());

            //电话
            ImageView car_call = (ImageView) convertView.findViewById(R.id.car_call);
            car_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.setData(Uri.parse("tel:" + homeCarstore.get(position).getBphone()));
                    startActivity(intent);
                }
            });
            //位置导航
            ImageView car_location = (ImageView) convertView.findViewById(R.id.car_location);
            car_location.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (!AppUtil.openMap(context, homeCarstore.get(position).getBname(), homeCarstore.get(position).getLatitude(), homeCarstore.get(position).getLongitude())) {
                                                        //没有安装
                                                        UIUtils.Toast("您没有安装地图App，暂时无法导航", false);// TODO: 16/11/29
                                                    }
                                                }
                                            }
            );
            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.car_img)
        SimpleDraweeView carImg;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.store_address)
        TextView storeAddress;
        @BindView(R.id.car_owner)
        TextView carOwner;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.car_location)
        ImageView carLocation;
        @BindView(R.id.car_call)
        ImageView carCall;
        @BindView(R.id.home_title1_text)
        TextView homeTitle1Text;
        @BindView(R.id.home_title1_ly)
        LinearLayout homeTitle1Ly;
        @BindView(R.id.home_title2_text)
        TextView homeTitle2Text;
        @BindView(R.id.home_title2_ly)
        LinearLayout homeTitle2Ly;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }


}
