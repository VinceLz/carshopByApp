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
import com.facebook.drawee.view.SimpleDraweeView;

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
        mid = intent.getIntExtra("mid", 1);
        mname = intent.getStringExtra("mname");
        image = intent.getStringExtra("image");
        carOnlineCarName.setText(mname);
        if (!TextUtils.isEmpty(image)) {
            // HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(image)).into(carOnlineCarImg);
            ImageLoad.loadImg(carOnlineCarImg, image);
        }
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
                            myGridView.setAdapter(new Color_MyGridView());
                        }
                    });
                } else {
                    color = new ArrayList<String>();
                    color.add("默认");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myGridView.setAdapter(new Color_MyGridView());
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
        goumai1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        goumai2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        goumai3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        goumai4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        switch (v.getId()) {
            case R.id.goumai1:
                goumai1.setBackgroundColor(Color.parseColor("#CC8F33"));
                break;
            case R.id.goumai2:
                goumai2.setBackgroundColor(Color.parseColor("#CC8F33"));
                break;
            case R.id.goumai3:
                goumai3.setBackgroundColor(Color.parseColor("#CC8F33"));
                break;
            case R.id.goumai4:
                goumai4.setBackgroundColor(Color.parseColor("#CC8F33"));
                break;
        }
    }

    class Color_MyGridView extends BaseAdapter {

        @Override
        public int getCount() {
            return color == null ? 0 : color.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
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
            gouche1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            gouche2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            gouche3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            gouche4.setBackgroundColor(Color.parseColor("#FFFFFF"));
            switch (v.getId()) {
                case R.id.gouche1:
                    gouche1.setBackgroundColor(Color.parseColor("#CC8F33"));
                    break;
                case R.id.gouche2:
                    gouche2.setBackgroundColor(Color.parseColor("#CC8F33"));
                    break;
                case R.id.gouche3:
                    gouche3.setBackgroundColor(Color.parseColor("#CC8F33"));
                    break;
                case R.id.gouche4:
                    gouche4.setBackgroundColor(Color.parseColor("#CC8F33"));
                    break;
            }
        }
    }
}
