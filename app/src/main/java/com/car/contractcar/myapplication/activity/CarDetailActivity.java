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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.ListViewUtlis;
import com.car.contractcar.myapplication.common.utils.AppUtil;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.JsonUtils2;
import com.car.contractcar.myapplication.entity.CarDetail;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

public class CarDetailActivity extends AppCompatActivity {

    private static final String TAG = "XUZI";
    @BindView(R.id.car_detail_img)
    RollPagerView carDetailImg;
    @BindView(R.id.car_detail_name)
    TextView carDetailName;
    @BindView(R.id.car_detail_price)
    TextView carDetailPrice;
    @BindView(R.id.car_detail_title)
    TextView carDetailTitle;
    @BindView(R.id.car_detail_phone)
    ImageView carDetailPhone;
    @BindView(R.id.car_detail_back)
    ImageView carDetailBack;
    @BindView(R.id.car_detail_hscrollview)
    LinearLayout carDetailHscrollview;
    @BindView(R.id.car_detail_online)
    ImageView carDetailOnline;
    @BindView(R.id.activity_car_detail)
    RelativeLayout activityCarDetail;
    @BindView(R.id.car_detail_fprice)
    ImageView carDetailFprice;
    @BindView(R.id.activity_car_conf)
    Button activity_car_conf;
    @BindView(R.id.locations)
    ImageView locations;


    private String configure;
    private int mid;
    private CarDetail carDetail;
    private CarDetail.CarBean car;
    private String mName;
    private int bid;
    private View xmlView;
    private LinkedHashMap<String, LinkedHashMap<String, String>> jsonToPojo = null;
    private LoadingDialog loadingDialog;
    String distance;
    List<String> image = new ArrayList<>();
    String longitude = null;
    String latitude = null;
    String phone = null;
    String bname = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xmlView = UIUtils.getXmlView(R.layout.activity_car_detail);
        setContentView(xmlView);
        ButterKnife.bind(this);
        distance = getIntent().getIntExtra("distance", 0) + "";
        phone = getIntent().getStringExtra("phone");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        bname = getIntent().getStringExtra("bname");
        initView();
        initData();
    }


    private void initView() {
        Intent intent = getIntent();
        mid = intent.getIntExtra("mid", 1);
        bid = intent.getIntExtra("bid", 1);
        carDetailImg.setPlayDelay(3000);
        carDetailImg.setAnimationDurtion(500);
        carDetailImg.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));

        //导航
        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtil.openMap(CarDetailActivity.this, bname, latitude, longitude)) {
                    UIUtils.Toast("您没有安装地图App，暂时无法导航", false);// TODO: 16/11/29
                }
            }
        });
    }

    private void initData() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(CarDetailActivity.this, "加载中...");
        }
        loadingDialog.show();
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_CAR_DETAIL + mid, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                carDetail = (CarDetail) JsonUtils.json2Bean(code, CarDetail.class);
                configure = carDetail.getCar().getConfigure();
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        refreshView();
                    }
                });
            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {

            }
        }, 3600);
    }


    private void refreshView() {
        if (carDetail != null) {
            car = carDetail.getCar();
            carDetailImg.setAdapter(new StaticPagerAdapter() {
                @Override
                public View getView(ViewGroup container, int position) {
                    SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    Log.d("----", "post" + position + "-----" + car.getMimage().size());
                    ImageLoad.loadImg(imageView, car.getMimage().get(position));
                    return imageView;
                }

                @Override
                public int getCount() {
                    return car.getMimage() != null ? car.getMimage().size() : 0;
                }
            });


            carDetailName.setText(car.getMname());
            carDetailPrice.setText("价格 : " + car.getGuidegprice() + "万");
            if (!TextUtils.isEmpty(car.getMtitle())) {
                carDetailTitle.setText(car.getMtitle());
            } else {
                carDetailTitle.setVisibility(View.INVISIBLE);
            }
            List<CarDetail.RecommendBean> recommend = carDetail.getRecommend();
            carDetailHscrollview.removeAllViews();
            if (recommend != null && recommend.size() > 0) {
                for (final CarDetail.RecommendBean recommendBean : recommend) {
                    LinearLayout recommendItemView = (LinearLayout) UIUtils.getXmlView(R.layout.car_detail_recommend_item);
                    SimpleDraweeView recommendItemImg = (SimpleDraweeView) recommendItemView.findViewById(R.id.car_detail_recommend_item_img);
                    TextView recommendItemTitle = (TextView) recommendItemView.findViewById(R.id.car_detail_recommend_item_title);
                    ImageLoad.loadImg(recommendItemImg, recommendBean.getMshowImage());
                    recommendItemTitle.setText(recommendBean.getMname() + "\n" + "指导价 : " + recommendBean.getGuidegprice() + "万");
                    carDetailHscrollview.addView(recommendItemView);
                    recommendItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mid = recommendBean.getMid();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            });
                        }
                    });
                }

            }


        }


    }

    @OnClick(R.id.car_detail_back)
    public void onBack(View view) {
        this.onBackPressed();

    }


    @OnClick(R.id.car_detail_online)
    public void onLien(View view) {
        Intent intent = new Intent(this, CarOnlineActivity.class);
        intent.putExtra("mid", car.getMid());
        intent.putExtra("mname", car.getMname());
        intent.putExtra("image", car.getMimage().get(0));
        startActivity(intent);
        //    右往左推出效果
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @OnClick(R.id.car_detail_phone)
    public void onPhone() {
        Constant.intent.setData(Uri.parse("tel:" + phone));
        startActivity(Constant.intent);
    }

    @OnClick(R.id.car_detail_fprice)
    public void onFprice() {
        Intent intent = new Intent(this, FloorPriceActivity.class);
        intent.putExtra("mid", mid);
        intent.putExtra("distance", Integer.valueOf(distance));
        intent.putExtra("mname", car.getMname());
        intent.putExtra("bid", car.getBid());
        startActivity(intent);
        //    右往左推出效果
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    @OnClick(R.id.activity_car_conf)
    public void activity_car_conf() {
        Intent intent = new Intent(CarDetailActivity.this, CarConfActivity.class);
        intent.putExtra("conf", configure);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog.close();
    }
}
