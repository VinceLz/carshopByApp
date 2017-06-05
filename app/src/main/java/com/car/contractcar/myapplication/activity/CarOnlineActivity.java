package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.ui.MyGridView;
import com.car.contractcar.myapplication.common.ui.cityList.Activity01;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.keepcar.model.YcOrder;
import com.car.contractcar.myapplication.keepcar.view.KeepCarServiceInfoActivity;
import com.car.contractcar.myapplication.keepcar.view.KeepCar_Pay;
import com.car.contractcar.myapplication.loginandr.activity.LoginActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

public class CarOnlineActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.car_online_back)
    EduSohoIconView carOnlineBack;
    @BindView(R.id.car_online_car_name)
    TextView carOnlineCarName;
    @BindView(R.id.car_online_car_img)
    SimpleDraweeView carOnlineCarImg;
    private int mid;
    private String gname;
    private String mname;
    private String image;
    @BindView(R.id.color_list2)
    MyGridView myGridView;
    List<String> color;
    String current_color = null;
    @BindView(R.id.citylist)
    LinearLayout citylist;
    @BindView(R.id.city_text)
    TextView city_text;
    @BindView(R.id.city_text2)
    TextView city_text2;
    @BindView(R.id.citylist2)
    LinearLayout citylist2;
    @BindView(R.id.goumai4)
    LinearLayout goumai4;
    @BindView(R.id.goumai3)
    LinearLayout goumai3;
    @BindView(R.id.goumai2)
    LinearLayout goumai2;
    @BindView(R.id.goumai1)
    LinearLayout goumai1;

    @BindView(R.id.gouche4)
    LinearLayout gouche4;
    @BindView(R.id.gouche3)
    LinearLayout gouche3;
    @BindView(R.id.gouche2)
    LinearLayout gouche2;
    @BindView(R.id.gouche1)
    LinearLayout gouche1;
    @BindView(R.id.time1)
    TextView time1;
    @BindView(R.id.time2)
    TextView time2;
    @BindView(R.id.time3)
    TextView time3;
    @BindView(R.id.time4)
    TextView time4;

    @BindView(R.id.buyway1)
    TextView buy1;
    @BindView(R.id.buyway2)
    TextView buy2;
    @BindView(R.id.buyway3)
    TextView buy3;
    @BindView(R.id.buyway4)
    TextView buy4;

    @BindView(R.id.fprice_ok)
    Button fprice_ok;

    String bname = null;
    Color_MyGridView color_myGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_online);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        Intent intent = getIntent();
        bname = intent.getStringExtra("bname");
        mid = intent.getIntExtra("mid", 1);
        mname = intent.getStringExtra("mname");
        image = intent.getStringExtra("image");
        carOnlineCarName.setText(mname);
        if (!TextUtils.isEmpty(image)) {
            // HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(image)).into(carOnlineCarImg);
            ImageLoad.loadImg(carOnlineCarImg, image);
        }
        color_myGridView = new Color_MyGridView();
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = null;
                for (int i = 0; i < color.size(); i++) {
                    v = (View) myGridView.getChildAt(i);
                    if (i == position) {
                        current_color = color.get(position);
                        v.setBackgroundColor(Color.parseColor("#CC8F33"));
                    } else {
                        v.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
            }
        });
        citylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //城市选择
                Intent i = new Intent(CarOnlineActivity.this, Activity01.class);
                startActivityForResult(i, 1);
            }
        });
        citylist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //城市选择
                Intent i = new Intent(CarOnlineActivity.this, Activity01.class);
                startActivityForResult(i, 2);
            }
        });
        goumai1.setOnClickListener(this);
        goumai2.setOnClickListener(this);
        goumai3.setOnClickListener(this);
        goumai4.setOnClickListener(this);
        MyClick click = new MyClick();
        gouche1.setOnClickListener(click);
        gouche2.setOnClickListener(click);
        gouche3.setOnClickListener(click);
        gouche4.setOnClickListener(click);
        fprice_ok.setOnClickListener(this);
    }

    private void initData() {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_CAR_COLOR + mid, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                String colors = JSONObject.parseObject(code).getString("colors");
                if (!TextUtils.isEmpty(colors)) {
                    color = JSON.parseObject(colors, ArrayList.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myGridView.setAdapter(color_myGridView);
                        }
                    });
                } else {
                    color = new ArrayList<String>();
                    color.add("默认");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myGridView.setAdapter(color_myGridView);
                        }
                    });
                }
            }

            @Override
            public void fail(String code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CarOnlineActivity.this, "出现异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void err() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CarOnlineActivity.this, "出现异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 3600 * 2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @OnClick(R.id.car_online_back)
    public void back(View view) {
        this.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goumai1:
                goumai1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai1.setSelected(false);
                goumai2.setSelected(false);
                goumai3.setSelected(false);
                goumai4.setSelected(false);
                goumai2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai1.setBackgroundColor(Color.parseColor("#CC8F33"));
                goumai1.setSelected(true);
                break;
            case R.id.goumai2:
                goumai1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai1.setSelected(false);
                goumai2.setSelected(false);
                goumai3.setSelected(false);
                goumai4.setSelected(false);
                goumai2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai2.setBackgroundColor(Color.parseColor("#CC8F33"));
                goumai2.setSelected(true);
                break;
            case R.id.goumai3:
                goumai1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai1.setSelected(false);
                goumai2.setSelected(false);
                goumai3.setSelected(false);
                goumai4.setSelected(false);
                goumai2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai3.setBackgroundColor(Color.parseColor("#CC8F33"));
                goumai3.setSelected(true);
                break;
            case R.id.goumai4:
                goumai1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai1.setSelected(false);
                goumai2.setSelected(false);
                goumai3.setSelected(false);
                goumai4.setSelected(false);
                goumai2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                goumai4.setBackgroundColor(Color.parseColor("#CC8F33"));
                goumai4.setSelected(true);
                break;
            case R.id.fprice_ok:
                //提交按钮 條用
                if (Constant.USER == null) {
                    //没有登陆
                    Intent i = new Intent(CarOnlineActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(CarOnlineActivity.this, KeepCar_Pay.class);
                    List<YcOrder> order = new ArrayList<YcOrder>();
                    YcOrder yc = new YcOrder();
                    yc.setMid(mid); //后台会根据mid 查询所有的附属属性
                    yc.setBuyWay(selectBuyWay());
                    yc.setBuytime(selectBiyTime());
                    yc.setPrice(99d);
                    yc.setCity(city_text.getText().toString());
                    yc.setCardCity(city_text2.getText().toString());
                    yc.setColor(color_myGridView.getItem(myGridView.getSelectedItemPosition() + 1).toString())
                    ;
                    String sname = "在线订车套餐";
                    yc.setSname(sname);
                    yc.setBmname(bname);
                    order.add(yc);
                    i.putExtra("orders", (Serializable) order);
                    i.putExtra("sname", sname);
                    i.putExtra("bname", bname);
                    i.putExtra("price", 99D);
                    startActivity(i);
                    finish();
                }
                break;
        }
    }

    private String selectBiyTime() {
        String time = "";
        if (gouche1.isSelected()) {
            time = time1.getText().toString();
        }
        if (gouche2.isSelected()) {
            time = time2.getText().toString();
        }
        if (gouche3.isSelected()) {
            time = time3.getText().toString();
        }
        if (gouche4.isSelected()) {
            time = time4.getText().toString();
        }
        Log.d("----", "购车时间+" + time);
        return time;
    }

    private String selectBuyWay() {
        String buys = "";
        if (goumai1.isSelected()) {
            buys = buy1.getText().toString();
            Log.d("----", "第一个");
        }
        if (goumai2.isSelected()) {
            buys = buy2.getText().toString();
            Log.d("----", "第2个");
        }
        if (goumai3.isSelected()) {
            buys = buy3.getText().toString();
            Log.d("----", "第3个");
        }
        if (goumai4.isSelected()) {
            buys = buy4.getText().toString();
            Log.d("----", "4个");
        }
        Log.d("----", "购车方式+" + buys);
        return buys;
    }

    class Color_MyGridView extends BaseAdapter {

        @Override
        public int getCount() {
            return color == null ? 0 : color.size();
        }

        @Override
        public Object getItem(int position) {
            return color.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HoldView viewHolder = null;
            if (convertView == null) {
                convertView = UIUtils.getXmlView(R.layout.car_color_item);
                viewHolder = new HoldView(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HoldView) convertView.getTag();
            }
            viewHolder.textView.setText(color.get(position));
            return convertView;
        }
    }

    class HoldView {
        @BindView(R.id.car_color1_text)
        TextView textView;

        public HoldView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            String city = data.getStringExtra("city");
            city_text.setText(city);
        } else if (requestCode == 2 && resultCode == 2) {
            String city = data.getStringExtra("city");
            city_text2.setText(city);
        }
    }

    class MyClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            gouche1.setSelected(false);
            gouche2.setSelected(false);
            gouche3.setSelected(false);
            gouche4.setSelected(false);

            gouche1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            gouche2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            gouche3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            gouche4.setBackgroundColor(Color.parseColor("#FFFFFF"));
            switch (v.getId()) {
                case R.id.gouche1:
                    gouche1.setBackgroundColor(Color.parseColor("#CC8F33"));
                    gouche1.setSelected(true);
                    break;
                case R.id.gouche2:
                    gouche2.setBackgroundColor(Color.parseColor("#CC8F33"));
                    gouche2.setSelected(true);
                    break;
                case R.id.gouche3:
                    gouche3.setBackgroundColor(Color.parseColor("#CC8F33"));
                    gouche3.setSelected(true);
                    break;
                case R.id.gouche4:
                    gouche4.setBackgroundColor(Color.parseColor("#CC8F33"));
                    gouche4.setSelected(true);
                    break;
            }
        }
    }
}
