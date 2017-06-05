package com.car.contractcar.myapplication.keepcar.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.http.PostHttp;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.DateUtil;
import com.car.contractcar.myapplication.common.utils.JsonUtils2;
import com.car.contractcar.myapplication.keepcar.Adapter.Roll_ListView_Adapter;
import com.car.contractcar.myapplication.keepcar.bean.RollInfo;
import com.car.contractcar.myapplication.user.adapter.Roll_ListView_AdapterShow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Keep_car_Roll extends AppCompatActivity {
    @BindView(R.id.roll_list)
    ListView roll_list;
    String type;
    Roll_ListView_Adapter roll_listView_adapter;
    double price;
    List<RollInfo> rollsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_car__roll);
        ButterKnife.bind(this);
        type = getIntent().getExtras().getString("type");
        price = getIntent().getExtras().getDouble("price");
        roll_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RollInfo rollInfo = rollsList.get(position);
                if (price < rollInfo.getCondition() || !DateUtil.compTo(rollInfo.getPastdate(), 0)) {
                    return;
                }
                Log.d("----", "rollInfo" + rollInfo);
                Intent it = new Intent();
                it.putExtra("info", rollInfo);
                setResult(1, it);
                finish();
            }
        });
        roll_list.setEmptyView(findViewById(R.id.layout_empty));
        initData();
    }

    private void initData() {
        Map map = new HashMap();
        map.put("type", type);
        HttpUtil.post(Constant.HTTP_BASE + Constant.HTTP_KEEP_CAR_ROLL, map, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                JSONObject object = JSONObject.parseObject(code);
                Log.d("----", "code" + code);
                List<RollInfo> rolls = JSON.parseArray(object.getString("rolls"), RollInfo.class);
                Log.d("----", "rolls" + rolls);
                if (rolls == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Keep_car_Roll.this, "网路异常", Toast.LENGTH_LONG).show();
                            return;
                        }
                    });
                } else {
                    rollsList = rolls;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            roll_listView_adapter = new Roll_ListView_Adapter(Keep_car_Roll.this, rollsList, price);
                            roll_list.setAdapter(roll_listView_adapter);
                        }
                    });

                }
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
