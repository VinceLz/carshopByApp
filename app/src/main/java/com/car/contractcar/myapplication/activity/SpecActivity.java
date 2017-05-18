package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.entity.SelectDataList;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.ui.MyGridView;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpecActivity extends AppCompatActivity {

    @BindView(R.id.car_countries)
    MyGridView carCountries;
    @BindView(R.id.car_displacement)
    MyGridView carDisplacement;
    @BindView(R.id.car_drive)
    MyGridView carDrive;
    @BindView(R.id.car_fuel)
    MyGridView carFuel;
    @BindView(R.id.car_transmission_case)
    MyGridView carTransmissionCase;
    @BindView(R.id.car_production)
    MyGridView carProduction;
    @BindView(R.id.car_structure)
    MyGridView carStructure;
    @BindView(R.id.car_seat)
    MyGridView carSeat;
    @BindView(R.id.car_level)
    MyGridView carLevel;
    @BindView(R.id.select_ok)
    Button selectOk;
    @BindView(R.id.activity_spec)
    LinearLayout activitySpec;
    @BindView(R.id.car_minprice)
    EditText carMinprice;
    @BindView(R.id.car_maxprice)
    EditText carMaxprice;
    private List<MyGridView> viewList;
    @BindView(R.id.sousuo)
    EditText sousuo;
    String keyword;
    public static final String[] level = {"微型车", "小型车", "紧凑型车", "中型车", "中大型车", "大型车", "跑车", "MPV", "SUV", "微面", "微卡", "轻客", "皮卡"};
    public static final String[] country = {"中国", "德国", "日本", "美国", "韩国", "法国", "英国", "其他"};
    public static final String[] transmission = {"手动", "自动"};
    public static final String[] output = {"1.0及以下", "1.1-1.6L", "1.7-2.0L", "2.1-2.5L", "2.6-3.0L", "3.1-4.0L", "4.0L以上"};
    public static final String[] drive = {"前驱", "后驱", "四驱"};
    public static final String[] fuel = {"汽油", "柴油", "油电混合", "纯电动", "插电式混动", "增程式"};
    public static final String[] seat = {"2座", "4座", "5座", "6座", "7座", "7座以上"};
    public static final String[] structure = {"两厢", "三厢", "掀背", "旅行版", "硬顶敞篷车", "软顶敞篷车", "硬顶跑车", "客车", "货车"};
    public static final String[] produce = {"自主", "合资", "进口"};

    Map<String, String[]> datas = new HashMap<>();
    String[] keys = {"level", "country", "output", "drive", "fuel", "transmission", "produce", "structure", "seat"};

    List<String> selectDatas = new ArrayList<>();
    private String TAG = "XUZI";
    private SelectDataList selectDataList;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        dialog = new LoadingDialog(this, "玩命加载中...");
        viewList = new ArrayList<>();
        viewList.add(carLevel);
        datas.put("level", level);
        viewList.add(carCountries);
        datas.put("country", country);
        viewList.add(carDisplacement);
        datas.put("output", output);
        viewList.add(carDrive);
        datas.put("drive", drive);
        viewList.add(carFuel);
        datas.put("fuel", fuel);
        viewList.add(carTransmissionCase);
        datas.put("transmission", transmission);
        viewList.add(carProduction);
        datas.put("produce", produce);
        viewList.add(carStructure);
        datas.put("structure", structure);
        viewList.add(carSeat);
        datas.put("seat", seat);
    }

    private void initData() {
        for (int i = 0; i < viewList.size(); i++) {
            if (i == 0) {
                viewList.get(0).setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return level.length;
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
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = UIUtils.getXmlView(R.layout.level_item);
                        final TextView textView = (TextView) view.findViewById(R.id.car_text);
                        final ImageView imageView = (ImageView) view.findViewById(R.id.car_icon);
                        final LinearLayout carLevelItem = (LinearLayout) view.findViewById(R.id.car_level_item);
                        carLevelItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isSelect(keys[0] + ":" + textView.getText().toString())) {
                                    selectDatas.add(keys[0] + ":" + textView.getText().toString());
                                    imageView.setImageResource(R.mipmap.car_bg);
                                    textView.setTextColor(Color.parseColor("#5898D3"));
                                } else {
                                    selectDatas.remove(keys[0] + ":" + textView.getText().toString());
                                    imageView.setImageResource(R.mipmap.car_bgn);
                                    textView.setTextColor(Color.parseColor("#999999"));
                                }
                            }
                        });
                        textView.setText(level[position]);
                        imageView.setImageResource(R.mipmap.car_bgn);
                        return view;
                    }
                });

            } else {
                viewList.get(i).setAdapter(new MyAdapter(datas.get(keys[i]), i));
            }

        }
    }

    class MyAdapter extends BaseAdapter {
        private String[] data;
        private int dataPosition;

        MyAdapter(String[] data, int dataPosition) {
            this.data = data;
            this.dataPosition = dataPosition;
        }

        @Override
        public int getCount() {
            return data.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = UIUtils.getXmlView(R.layout.car_condition_item);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.select_lay);
            final TextView textView = (TextView) view.findViewById(R.id.select_content);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isSelect(keys[dataPosition] + ":" + textView.getText().toString())) {
                        selectDatas.add(keys[dataPosition] + ":" + textView.getText().toString());
                        textView.setBackgroundResource(R.drawable.condition_bg);
                        textView.setTextColor(Color.WHITE);
                    } else {
                        selectDatas.remove(keys[dataPosition] + ":" + textView.getText().toString());
                        textView.setBackgroundResource(R.drawable.condition_bgn);
                        textView.setTextColor(Color.parseColor("#666666"));
                    }
                }
            });
            textView.setText(data[position]);
            return view;
        }
    }

    /**
     * 判断是否选中
     *
     * @param selectData
     * @return
     */
    private boolean isSelect(String selectData) {
        if (selectDatas.size() == 0) {
            return false;
        }
        for (int i = 0; i < selectDatas.size(); i++) {
            if (selectData.equals(selectDatas.get(i))) {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.select_ok)
    public void select_ok(View view) {
        dialog.show();
        List<String> countrieslist = new ArrayList<>();
        List<String> outputtist = new ArrayList<>();
        List<String> drivelist = new ArrayList<>();
        List<String> fuellist = new ArrayList<>();
        List<String> transmissionCaselist = new ArrayList<>();
        List<String> productionlist = new ArrayList<>();
        List<String> structurelist = new ArrayList<>();
        List<String> seatlist = new ArrayList<>();
        List<String> levellist = new ArrayList<>();
        for (int i = 0; i < selectDatas.size(); i++) {
            String[] split = selectDatas.get(i).split(":");
            if (split[0].equals("country")) {
                countrieslist.add(split[1]);
            } else if (split[0].equals("output")) {
                outputtist.add(split[1].split("L")[0]);
            } else if (split[0].equals("drive")) {
                drivelist.add(split[1]);
            } else if (split[0].equals("fuel")) {
                fuellist.add(split[1]);
            } else if (split[0].equals("transmission")) {
                transmissionCaselist.add(split[1]);
            } else if (split[0].equals("produce")) {
                productionlist.add(split[1]);
            } else if (split[0].equals("structure")) {
                structurelist.add(split[1]);
            } else if (split[0].equals("seat")) {
                seatlist.add(split[1]);
            } else if (split[0].equals("level")) {
                levellist.add(split[1]);
            }
        }
        selectDataList = new SelectDataList(countrieslist, outputtist, drivelist, fuellist, transmissionCaselist, productionlist, structurelist, seatlist, levellist);
        if (!TextUtils.isEmpty(carMaxprice.getText().toString())) {
            selectDataList.setMaxprice(Double.parseDouble(carMaxprice.getText() + ""));
        }
        if (!TextUtils.isEmpty(carMinprice.getText().toString())) {
            selectDataList.setMinprice(Double.parseDouble(carMinprice.getText().toString()));
        }
        if (!TextUtils.isEmpty(sousuo.getText())) {
            selectDataList.setKeyword(sousuo.getText() + "");
        }
        String jsonString = JSON.toJSONString(selectDataList);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        Log.e(TAG, "select_ok: " + Constant.HTTP_BASE + Constant.HTTP_SELECT + "/" + jsonString + ".action");
        // get调用网络
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_SELECT + "/" + jsonString + ".action", new HttpUtil.callBlack() {
            @Override
            public void succcess(final String code) {
                Log.e(TAG, "succcess: " + code);
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.close();
                        Intent intent = new Intent(SpecActivity.this, CarModelsActivity.class);
                        intent.putExtra("data", code);
                        SpecActivity.this.startActivity(intent);
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
        }, 3600 * 2);
    }
}
