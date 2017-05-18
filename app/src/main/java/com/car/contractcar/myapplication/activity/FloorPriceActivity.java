package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.entity.FloorPrice;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.ui.LineEditText;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

public class FloorPriceActivity extends AppCompatActivity {

    private static final String TAG = "XUZI";
    @BindView(R.id.fprice_back)
    EduSohoIconView fpriceBack;
    @BindView(R.id.fprice_car_name)
    TextView fpriceCarName;
    @BindView(R.id.fprice_phone)
    LineEditText fpricePhone;
    @BindView(R.id.fprice_name)
    LineEditText fpriceName;
    @BindView(R.id.fprice_ok)
    Button fpriceOk;
    @BindView(R.id.car_icon)
    SimpleDraweeView carIcon;
    @BindView(R.id.store_name)
    TextView storeName;
    @BindView(R.id.store_address)
    TextView storeAddress;
    @BindView(R.id.car_owner)
    TextView carOwner;
    @BindView(R.id.distance)
    TextView distance;
    @BindView(R.id.floor_price_title1)
    TextView floorPriceTitle1;
    @BindView(R.id.floor_price_title1_ly)
    LinearLayout floorPriceTitle1Ly;
    @BindView(R.id.floor_price_title2)
    TextView floorPriceTitle2;
    @BindView(R.id.floor_price_title2_ly)
    LinearLayout floorPriceTitle2Ly;
    @BindView(R.id.activity_floor_price)
    LinearLayout activityFloorPrice;
    private Intent intent;
    private int bid;
    private String gname;
    private String mname;
    private FloorPrice floorPrice;
    private String phone;
    private String uname;
    private int mid;
    int distance1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_price);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        intent = getIntent();
        bid = intent.getIntExtra("bid", 1);
        mid = intent.getIntExtra("mid", 1);
        mname = intent.getStringExtra("mname");
        distance1 = intent.getIntExtra("distance", 0);
        fpriceCarName.setText(mname);
        String s = Constant.HTTP_BASE + Constant.HTTP_FLOOR_PRICE + bid;
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_FLOOR_PRICE + bid, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                floorPrice = (FloorPrice) JsonUtils.json2Bean(code, FloorPrice.class);
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
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
        }, 3600 * 2);


    }

    private void refreshView() {
        if (floorPrice.getBusiness() != null) {
            //HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(floorPrice.getBusiness().getBshowImage())).into(carIcon);
            ImageLoad.loadImg(carIcon, floorPrice.getBusiness().getBshowImage());
            carOwner.setText("主营车型: " + floorPrice.getBusiness().getMajorbusiness());
            storeName.setText(floorPrice.getBusiness().getBname());
            distance.setText(distance1 + "m");
            storeAddress.setText(floorPrice.getBusiness().getBaddress());
            if (!TextUtils.isEmpty(floorPrice.getBusiness().getTitle1())) {
                floorPriceTitle1.setText(floorPrice.getBusiness().getTitle1());
            } else {
                floorPriceTitle1Ly.setVisibility(View.INVISIBLE);
            }

            if (!TextUtils.isEmpty(floorPrice.getBusiness().getTitle2())) {
                floorPriceTitle2.setText(floorPrice.getBusiness().getTitle2());
            } else {
                floorPriceTitle2Ly.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void getInData() {
        phone = fpricePhone.getText().toString();
        uname = fpriceName.getText().toString();

    }

    @OnClick(R.id.fprice_ok)
    public void fSubmit(View view) {
        getInData();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(uname)) {
            UIUtils.Toast("用户名或姓名不能为空", false);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("mid", mid);
            params.put("phone", phone);
            params.put("uname", uname);
            HttpUtil.post(Constant.HTTP_BASE + "/consult/add.action ", params, new HttpUtil.callBlack() {
                @Override
                public void succcess(String code) {
                    Log.e(TAG, "succcess: " + code);
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(FloorPriceActivity.this, "后台以收到，我们会及时联系您", Toast.LENGTH_LONG).show();
                            FloorPriceActivity.this.finish();
                        }
                    });
                }

                @Override
                public void fail(String code) {

                }

                @Override
                public void err() {

                }
            });
        }
    }

    @OnClick(R.id.fprice_back)
    public void fBack(View view) {
        this.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
