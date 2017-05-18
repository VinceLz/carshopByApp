package com.car.contractcar.myapplication.keepcar.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Keep;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.entity.KeepCar;
import com.car.contractcar.myapplication.keepcar.bean.GetTnTask;
import com.car.contractcar.myapplication.keepcar.bean.KeepCarShopInfo;
import com.car.contractcar.myapplication.keepcar.bean.PayCode;
import com.car.contractcar.myapplication.keepcar.bean.RollInfo;
import com.car.contractcar.myapplication.keepcar.bean.YcOrder;
import com.car.contractcar.myapplication.loginandr.activity.LoginActivity;
import com.ronglian.ezfmp.EzfMpAssist;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.car.contractcar.myapplication.MyApplication.context;

public class KeepCar_Pay extends AppCompatActivity {
    public static String ORDER_STATUS = "com.xawl.order.close";
    @BindView(R.id.keep_car_server_name)
    TextView storeName;

    @BindView(R.id.keep_car_server_id)
    TextView serverName;

    @BindView(R.id.keep_car_price)
    TextView servicePrice;

    @BindView(R.id.youhuijuan)
    LinearLayout youhuijuan;

    @BindView(R.id.roll_name)
    TextView roll_name;
    String sname;
    double sprice;
    KeepCarShopInfo keepCarShopInfo;

    @BindView(R.id.goods_Send)
    Button goods_Send;
    int type;
    double currentPrice = -1;//去支付的钱
    RollInfo info;
    @BindView(R.id.id1)
    RadioButton id1;
    @BindView(R.id.id2)
    RadioButton id2;
    @BindView(R.id.id3)
    RadioButton id3;
    private Dialog loadingDialog = null;
    int bankId;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private static String mode = "01"; // 01测试地址 00 生产地址
    private GetTnTask getTnTask = null;
    private LoadingDialog dialog = null;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_car__pay);
        ButterKnife.bind(this);
        String code = getIntent().getExtras().getString("info");
        keepCarShopInfo = (KeepCarShopInfo) JsonUtils.json2Bean(code, KeepCarShopInfo.class);
        Log.d("----", keepCarShopInfo.toString());
        storeName.setText(keepCarShopInfo.getYcstore().getMbname());
        sprice = getIntent().getExtras().getDouble("price");
        Log.d("----", "我接收到是价格为" + sprice);
        sname = getIntent().getExtras().getString("sname");
        servicePrice.setText("¥" + sprice);
        type = getIntent().getExtras().getInt("type");
        serverName.setText(sname);
        //处理优惠劵问题
        youhuijuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeepCar_Pay.this, Keep_car_Roll.class);
                intent.putExtra("type", type);
                intent.putExtra("price", sprice);
                Log.d("-----", "当前价格" + sprice);
                startActivityForResult(intent, 1);
            }
        });
        goods_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.USER == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(KeepCar_Pay.this, "您还没有登陆", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(KeepCar_Pay.this, LoginActivity.class);
                            startActivity(i);
                        }
                    });
                    return;
                }
                if (dialog == null) {
                    dialog = new LoadingDialog(KeepCar_Pay.this, "生成订单中...");
                }
                dialog.show();
                //获取银行id
                if (id1.isChecked()) {
                    bankId = 991;
                }
                if (id2.isChecked()) {
                    bankId = 992;
                }
                if (id3.isChecked()) {
                    bankId = 999;
                }

                Map map = new HashMap();
                map.put("bmname", keepCarShopInfo.getYcstore().getMbname());
                if (currentPrice == -1) {
                    map.put("price", sprice);
                } else {
                    map.put("price", currentPrice);
                }
                if (info != null) {
                    map.put("ruid", info.getRuid());
                }
                map.put("sname", sname);
                map.put("mbid", keepCarShopInfo.getYcstore().getMbid());
                map.put("uid", Constant.USER.getUid());
                map.put("uname", Constant.USER.getUname());
                map.put("uphone", Constant.USER.getUphone());
                map.put("realprice", sprice);
                HttpUtil.post(Constant.HTTP_BASE + Constant.ORDER_ADD, map, new HttpUtil.callBlack() {
                    @Override
                    public void succcess(String code) {
                        String ycorder = JSONObject.parseObject(code).getString("ycorder");
                        YcOrder ycOrder = JSON.parseObject(ycorder, YcOrder.class);
                        //获得订单中的goodid
                        Constant.CURRENT_ORDER = ycOrder;
                        final String goodid = ycOrder.getGoodid();
                        Log.d("------", "订单" + ycorder);
                        //拿到订单后,就去支付，并且订单已经生成了所以应该讲本页面关闭
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                dialog.close();
                                loadingDialog = ProgressDialog.show(KeepCar_Pay.this, // context
                                        "", // title
                                        "处理中…", // message
                                        true, true, new OnCancelListener() {
                                            @Override
                                            public void onCancel(DialogInterface dialoginterface) {
                                                // 取消异步任务
                                                getTnTask.cancel(true);
                                            }
                                        });
                                loadingDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕不取消对话框
                                getTnTask = new GetTnTask(Constant.HTTP_BASE + Constant.PAY, goodid, bankId + "", KeepCar_Pay.this,
                                        loadingDialog, KeepCar_Pay.this);
                                getTnTask.execute();
                                currentPrice = -1;

                            }
                        });
                    }

                    @Override
                    public void fail(String code) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                dialog.close();
                                Toast.makeText(KeepCar_Pay.this, "生成订单/流水号失败", Toast.LENGTH_SHORT).show();
                                currentPrice = -1;
                            }
                        });
                    }

                    @Override
                    public void err() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                dialog.close();
                                Toast.makeText(KeepCar_Pay.this, "生成订单失败", Toast.LENGTH_SHORT).show();
                                currentPrice = -1;
                            }
                        });
                    }
                });
            }
        });
        IntentFilter intentFilter = new IntentFilter(ORDER_STATUS);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            RollInfo roll = (RollInfo) data.getExtras().getSerializable("info");
            if (roll != null) {
                info = roll;
                roll_name.setText((int) roll.getPrice() + roll.getRname());
                if (sub(sprice, roll.getPrice()) <= 0) {
                    servicePrice.setText("¥0");
                } else {
                    currentPrice = sub(sprice, roll.getPrice());
                    servicePrice.setText("¥" + sub(sprice, roll.getPrice()));
                }
            }
        } else {
            EzfMpAssist.handleIntent(this, data);
        }
    }

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
