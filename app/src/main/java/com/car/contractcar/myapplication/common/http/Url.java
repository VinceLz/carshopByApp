package com.car.contractcar.myapplication.common.http;

/**
 * Created by Administrator on 2016/8/24.
 */
public class Url {
    public  static  String getService(String url){
        return "http://172.16.120.65:8080/shop"+url;
    }
}
