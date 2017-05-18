package com.car.contractcar.myapplication.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by xuzi on 8/10/2016.
 */
public class JsonUtils {

    public static Object json2Bean(String json, Class clazz) {
        Object o = JSON.parseObject(json, clazz);
        return o;
    }


    public static List json2BeanList(String json, Class Order) {
        List objects = JSON.parseArray(json, Order);
        return objects;
    }

    public static String bean2Json(Object object) {
        return JSON.toJSONString(object);
    }



}
