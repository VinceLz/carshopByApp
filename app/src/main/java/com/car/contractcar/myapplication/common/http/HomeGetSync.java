package com.car.contractcar.myapplication.common.http;

import android.os.AsyncTask;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/24.
 */

public class HomeGetSync extends AsyncTask<String, Void, Map<String, String>> {
    @Override
    protected Map<String, String> doInBackground(String... params) {
        //在这里应该去获取百度的位置 一旦获取到，就去刷新数据
        return null;
    }


}
