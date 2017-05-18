package com.car.contractcar.myapplication.loginandr.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.ui.LineEditText;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.loginandr.bean.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends AppCompatActivity {

    @BindView(R.id.regist_back)
    EduSohoIconView registBack;
    @BindView(R.id.regist_user_phone)
    EditText registUserPhone;
    @BindView(R.id.regist_send_code)
    TextView registSendCode;
    @BindView(R.id.regist_code)
    LineEditText registCode;
    @BindView(R.id.regist_user_password)
    LineEditText registUserPassword;
    @BindView(R.id.regist_btn)
    Button registBtn;
    private String code1;
    private int i = 60;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0 && i > 0) {
                registSendCode.setText("发送中" + i--);
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                handler.removeMessages(msg.what);
                i = 60;
                registSendCode.setClickable(true);
                registSendCode.setTextColor(Color.parseColor("#999999"));
                registSendCode.setText("发送验证码");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.regist_btn)
    public void onClickRegist(View view) {

        String s = registUserPhone.getText().toString();
        String code = registCode.getText().toString();
        String pass = registUserPassword.getText().toString();
        if (TextUtils.isEmpty(s) || TextUtils.isEmpty(code) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "输入不完整", Toast.LENGTH_SHORT).show();
        } else {
            if (!code.equals(code1)) {
                Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("upassword", pass);
                map.put("code", code);
                HttpUtil.post(Constant.HTTP_BASE + Constant.HTTP_REGIST_L, map, new HttpUtil.callBlack() {
                    @Override
                    public void succcess(final String code) {

                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = (JSONObject) JSONObject.parse(code);
                                int status = (int) jsonObject.get("status");
                                if (status == 1) {
                                    Constant.USER = JSON.parseObject(jsonObject.getString("user"), User.class);
                                    UIUtils.SpputString(Constant.USER_SP, jsonObject.getString("user"));
                                    UIUtils.startAnActivity(new Intent(RegistActivity.this, MainActivity.class), RegistActivity.this);
                                    finish();
                                }
                            }
                        });
                    }

                    @Override
                    public void fail(String code) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void err() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }


    @OnClick(R.id.regist_send_code)
    public void onClickSendCode(View view) {
        String s = registUserPhone.getText().toString();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        } else {
            registSendCode.setTextColor(Color.parseColor("#88999999"));
            registSendCode.setClickable(false);
            handler.sendEmptyMessage(0);
            Map<String, Object> map = new HashMap<>();
            map.put("ulogin", s);
            HttpUtil.post(Constant.HTTP_BASE + Constant.HTTP_REGIST_F, map, new HttpUtil.callBlack() {
                @Override
                public void succcess(String code) {
                    String status1 = JSONObject.parseObject(code).getString("code");
                    Log.d("-----", "注册进来了");
                    code1 = status1;
                }

                @Override
                public void fail(String code) {
                    handler.removeMessages(0);
                    handler.sendEmptyMessage(1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistActivity.this, "手机号已注册", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void err() {

                }
            });
        }
    }


    @OnClick(R.id.regist_back)
    public void onClickBack(View view) {
        this.onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
