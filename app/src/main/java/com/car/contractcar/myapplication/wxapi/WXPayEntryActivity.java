package com.car.contractcar.myapplication.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.keepcar.view.KeepCar_Pay;
import com.ronglian.ezfmp.EzfMpAssist;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用于接收微信支付结果返回，由于微信官方限制该Activity必须命名WXPayEntryActivity 并实行IWXAPIEventHandler接口
 * 并且包名必须为package+.wxapi，否则无法接收到支付结果返回
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(this, "wx9b4839c6e3ffbe1e");
        api.handleIntent(getIntent(), this);
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
        //发送广播，关闭购买页面
        Intent i = new Intent();
        i.setAction(KeepCar_Pay.ORDER_STATUS);
        this.sendBroadcast(i);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.d("-----", "微信响应+"+req);
    }
    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        int status = 0;
        String msg = "";
        String result = EzfMpAssist.analyzeWechatResult(resp.errCode);
        if ("Y".equalsIgnoreCase(result)) {
            msg = "支付成功";
        } else if ("N".equalsIgnoreCase(result)) {
            msg = "支付失败";
        } else if ("C".equalsIgnoreCase(result)) {
            msg = "支付取消";
        } else if ("D".equalsIgnoreCase(result)) {
            msg = "处理中";
        }
        payresult_txt.setText(msg);
        Map map = new HashMap<>();
        map.put("goodid", Constant.CURRENT_ORDER.getGoodid());
        map.put("status", status);
        HttpUtil.post(Constant.HTTP_BASE + Constant.BACK, map, null);
    }

    private final OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
