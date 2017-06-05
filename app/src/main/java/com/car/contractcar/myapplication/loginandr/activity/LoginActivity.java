package com.car.contractcar.myapplication.loginandr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.loginandr.bean.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.keep_car_service_back)
    EduSohoIconView keepCarServiceBack;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.contry_sn)
    TextView contrySn;
    @BindView(R.id.bt_username_clear)
    Button btUsernameClear;
    @BindView(R.id.username_layout)
    FrameLayout usernameLayout;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.bt_pwd_eye)
    Button btPwdEye;
    @BindView(R.id.bt_pwd_clear)
    Button btPwdClear;
    @BindView(R.id.usercode_layout)
    FrameLayout usercodeLayout;
    @BindView(R.id.login_btn)
    Button longnBtn;
    @BindView(R.id.login_error)
    Button loginError;
    @BindView(R.id.register)
    Button register;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher;
    private LoadingDialog loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btUsernameClear.setOnClickListener(this);
        btPwdClear.setOnClickListener(this);
        btPwdEye.setOnClickListener(this);
        initWatcher();
        username.addTextChangedListener(username_watcher);
        password.addTextChangedListener(password_watcher);
        loginError.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                password.setText("");
                if (s.toString().length() > 0) {
                    btUsernameClear.setVisibility(View.VISIBLE);
                } else {
                    btUsernameClear.setVisibility(View.INVISIBLE);
                }
            }
        };

        password_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    btPwdClear.setVisibility(View.VISIBLE);
                } else {
                    btPwdClear.setVisibility(View.INVISIBLE);
                }
            }
        };

    }


    public void onClickLogin(View view) {
        login();
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.login_error: //无法登陆(忘记密码了吧)
                //TODO 忘记密码
                break;
            case R.id.register:    //注册新的用户
                UIUtils.startAnActivity(new Intent(LoginActivity.this, RegistActivity.class), this);
                finish();
                break;
            case R.id.bt_username_clear:
                username.setText("");
                password.setText("");
                break;
            case R.id.bt_pwd_clear:
                password.setText("");
                break;
            case R.id.bt_pwd_eye:
                if (password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    btPwdEye.setBackgroundResource(R.mipmap.button_eye_n);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    btPwdEye.setBackgroundResource(R.mipmap.button_eye_n);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                password.setSelection(password.getText().toString().length());
                break;
        }
    }

    private void login() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this, "登录中...");
        }
        loadingDialog.show();
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("ulogin", usernameText);
            map.put("upassword", passwordText);
            HttpUtil.post(Constant.HTTP_BASE + Constant.HTTP_LOGIN, map, new HttpUtil.callBlack() {

                @Override
                public void succcess(String code) {
                    final JSONObject jsonObject = (JSONObject) JSONObject.parse(code);
                    Log.d("----", "用户登陆返回" + code);
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            int status = (int) jsonObject.get("status");
                            if (status == 0) {
                                Toast.makeText(LoginActivity.this, "user error", Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                // TODO: 17/3/28  登录成功
                                loadingDialog.dismiss();
                                loadingDialog.close();
                                UIUtils.SpputString(Constant.USER_SP, jsonObject.getString("user"));
                                Constant.USER = JSON.parseObject(jsonObject.getString("user"), User.class);
                                //  UIUtils.startAnActivity(new Intent(LoginActivity.this, MainActivity.class), LoginActivity.this);
                                Intent i = new Intent();
                                i.putExtra("status", "yes");
                                setResult(2, i);
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
                            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                            loadingDialog.close();
                        }
                    });
                }

                @Override
                public void err() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                            loadingDialog.close();
                        }
                    });
                }
            });


        }

    }


}
