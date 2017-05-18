package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.loginandr.activity.LoginActivity;
import com.car.contractcar.myapplication.loginandr.bean.User;

import org.w3c.dom.Text;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.CAR, MODE_PRIVATE);
        final int start_info = sharedPreferences.getInt("start_info", 0);
        final String user = UIUtils.SpgetString(Constant.USER_SP);
        new Handler(new Handler.Callback() {
            //处理接收到的消息的方法
            @Override
            public boolean handleMessage(Message arg0) {
                if (start_info == 1) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(StartActivity.this, GuideActivity.class));
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
                StartActivity.this.finish();

                return false;
            }
        }).sendEmptyMessageDelayed(0, 2000); //表示延时三秒进行任务的执行
    }
}
