package com.car.contractcar.myapplication.keepcar.bean;

import java.net.URLDecoder;
import java.security.cert.LDAPCertStoreParameters;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.ronglian.ezfmp.EzfMpAssist;

/**
 * @author ylyang ylyang@ronglian.cc
 * @ClassName: GetQidTask
 * @Description: 异步任务获取银联流水号，完成后调用银联插件
 * @date 2013-12-9 下午3:38:26
 */
public class GetTnTask extends AsyncTask<String, Void, Map<String, String>> {

    private static final String LOG_TAG = "GetTnTask";

    private static String mode = "01"; // 01测试地址 00 生产地址
    /*
     * 商户后台地址 http://test.ezf123.com/ezfmp_demo/payment.action
     * 是一个简单的演示模拟程序，仅用于测试！商户需要根据自己的实际地址进行修改。
     */
    private String baseUrl = null;
    private String goodsId = null;
    private String bankId = null;
    private Context context = null;
    private Dialog dialog = null;
    private Activity activity;

    public GetTnTask(String BaseUrl, String goodsId, String bankId, Context context,
                     Dialog dialog, Activity activity) {
        super();
        this.baseUrl = BaseUrl;
        this.goodsId = goodsId;
        this.bankId = bankId;
        this.context = context;
        this.dialog = dialog;
        this.activity = activity;
        Log.d("----", goodsId + "----" + bankId + "------");
    }

    /**
     * 后台获取流水号
     */
    @Override
    protected Map<String, String> doInBackground(String... param) {

        // Log.i(LOG_TAG, "doInBackground called");
        String response = null;
        Map<String, String> map = new HashMap<String, String>();
        map.put("respCode", "00");
        if (!TextUtils.isEmpty(goodsId)) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("goodid", goodsId);
            params.put("bankId", bankId);
            try {
                response = HttpUtil.postMap(baseUrl, params);
                Log.d("-----", response);
                response = com.alibaba.fastjson.JSONObject.parseObject(response).getString("paycode");
            } catch (Exception e) {
                Log.e(LOG_TAG, "获取交易流水号出错：" + e);
                map.put("respCode", "01");// 网络出错
            }
        }
        if (TextUtils.isEmpty(response)) {
            map.put("respCode", "01");
        }
        map.put("respMsg", response);
        return map;
    }

    /**
     * 获取流水号后 调用支付插件
     */
    @Override
    protected void onPostExecute(Map<String, String> result) {
        // Log.i(LOG_TAG, "onPostExecute called");
        String respCode = result.get("respCode");
        if ("00".equals(respCode)) {
            String respMsg = result.get("respMsg");
            if (!TextUtils.isEmpty(respMsg)) {
                String tn = responseResolver(respMsg);
                if (!TextUtils.isEmpty(tn)) {
                    Constant.CURRENT_ORDER.setQid(tn);
                    // 启动支付插件
                    Log.d("----", "tn=" + tn + "准备支付了");
                    EzfMpAssist.startPay(context, tn, mode,
                            "ronglian10001mobilepay", dialog);
                    // Toast.makeText(context, tn, Toast.LENGTH_LONG).show();
                } else {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Log.e(LOG_TAG, "流水号为空");
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    builder.setCancelable(false);
                    builder.setTitle("提示");
                    builder.setMessage("获取订单号失败,稍后请重试!");
                    builder.setNegativeButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }

        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("提示");
            builder.setMessage("网络连接异常,稍后请重试!");
            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }

    }

    /**
     * 取消异步任务
     */
    @Override
    protected void onCancelled() {
    }

    /**
     * 异步任务前准备
     */
    @Override
    protected void onPreExecute() {
    }

    /**
     * 解析服务器返回数据
     *
     * @param response
     * @return
     */
    private String responseResolver(String response) {
        String qid = null;
        if (!TextUtils.isEmpty(response)) {
            try {
                Log.i(LOG_TAG, "服务器响应原始串：" + response);
                JSONObject jsonObj = new JSONObject(response);
                String respCode = jsonObj.getString("respCode");
                String respMsg = URLDecoder.decode(
                        jsonObj.getString("respMsg"), "UTF-8");
                if ("00".equals(respCode)) {
                    qid = jsonObj.getString("qid");
                } else {
                    Toast.makeText(context, respMsg, Toast.LENGTH_LONG).show();
                }
                Log.i(LOG_TAG, "流水号：" + qid + " | 响应码：" + respCode + " | 响应信息："
                        + respMsg);
            } catch (Exception e) {
                Log.e(LOG_TAG, "返回数据解析错误" + e);
                Toast.makeText(context, "数据解析错误", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e(LOG_TAG, "返回数据为空");
            Toast.makeText(context, "查询交易流水号出错", Toast.LENGTH_LONG).show();
        }
        return qid;
    }
}
