package com.car.contractcar.myapplication.keepcar.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.ronglian.ezfmp.EzfMpAssist;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用于接收返回结果（微信除外）
 */
public class PayReturnActivity extends Activity {


    @BindView(R.id.order_name)
    TextView name;
    @BindView(R.id.order_sname)
    TextView sname;
    @BindView(R.id.googid)
    TextView googid;
    @BindView(R.id.qid)
    TextView qid;

    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.payresult_txt)
    TextView payresult_txt;
    @BindView(R.id.order_back)
    ImageView order_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ButterKnife.bind(this);
        if (Constant.CURRENT_ORDER != null) {
            name.setText(Constant.CURRENT_ORDER.getBmname());
            sname.setText(Constant.CURRENT_ORDER.getSname());
            googid.setText(Constant.CURRENT_ORDER.getGoodid());
            qid.setText(Constant.CURRENT_ORDER.getQid());
            price.setText(Constant.CURRENT_ORDER.getPrice() + "");
            date.setText(Constant.CURRENT_ORDER.getDate());
            phone.setText(Constant.CURRENT_ORDER.getUphone());
        }
        order_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭本页面，并返回到服务页面，吧支付页面也关掉
                Constant.CURRENT_ORDER = null;
                finish();
            }
        });
        handleIntent();
        //发送广播，关闭购买页面
        Intent i = new Intent();
        i.setAction(KeepCar_Pay.ORDER_STATUS);
        this.sendBroadcast(i);
    }

    private void handleIntent() {
        int status = 0;
        String msg = "";
        String result = EzfMpAssist.analyzeResult(getIntent());
        if ("Y".equalsIgnoreCase(result)) {
            msg = "支付成功";
            status = 3;
        } else if ("N".equalsIgnoreCase(result)) {
            msg = "支付失败";
            status = 2;
        } else if ("C".equalsIgnoreCase(result)) {
            msg = "支付取消";
            status = 1;
        } else if ("D".equalsIgnoreCase(result)) {
            msg = "处理中";
            status = 0;
        }
        Toast.makeText(PayReturnActivity.this, msg, Toast.LENGTH_LONG).show();
        payresult_txt.setText(msg);
        Map map = new HashMap<>();
        map.put("goodid", Constant.CURRENT_ORDER.getGoodid());
        map.put("status", status);
        HttpUtil.post(Constant.HTTP_BASE + Constant.BACK, map, null);
    }
}
