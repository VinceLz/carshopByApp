package com.car.contractcar.myapplication.common.utils;

import android.content.Intent;

import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.keepcar.bean.YcOrder;
import com.car.contractcar.myapplication.loginandr.bean.User;

/**
 * Created by macmini2 on 16/11/6.
 */

public class Constant {
    public static final String CAR = "CAR";
    public static final int BUY_CAR = 1;
    public static final int KEEP_CAR = 0;
    //外网
    public static final String HTTP_BASE = "http://59.110.5.105/carshop/";
    //内网
    //  public static final String HTTP_BASE = "http://172.16.120.65:8080/carshop/";
    public static final String HTTP_HOME = "home.action";
    public static final String HTTP_KEEP_CAR = "/ycstore/home.action";
    public static final String HTTP_KEEP_CAR_SHOP_INFO = "/ycstore/get.action?mbid=";
    public static final String HTTP_KEEP_CAR_ROLL = "/roll/get.action";
    public static final String HTTP_SHOP_INFO = "store/getall.action?bid=";
    public static final String HTTP_CAR_INFO = "car/getModels.action?gid=";
    public static final String HTTP_CAR_DETAIL = "car/models/get.action?mid=";
    public static final String HTTP_FLOOR_PRICE = "store/get.action?bid=";
    public static final String HTTP_SELECT = "home/car";
    public static final String HTTP_CAR_COLOR = "car/models/getColor.action?mid=";
    public static final String HTTP_LOGIN = "/user/login.action";
    public static final String HTTP_REGIST_F = "/user/registfrist.action";
    public static final String HTTP_REGIST_L = "/user/registlast.action";
    public static final String[] TransmissionCase = {"手动", "自动"};
    public static final String[] displacement = {"1.0及以下", "1.1-1.6L", "1.7-2.0L", "2.1-2.5L", "2.6-3.0L", "3.1-4.0L", "4.0L以上"};
    public static final String[] drive = {"前驱", "后驱", "四驱"};
    public static final String[] Fuel = {"汽油", "柴油", "油电混合", "纯电动", "插电式混动", "增程式"};
    public static final String[] countries = {"中国", "德国", "日本", "美国", "韩国", "法国", "英国", "其他"};
    public static final String[] seat = {"2座", "4座", "5座", "6座", "7座", "7座以上"};
    public static final String[] structure = {"两厢", "三厢", "掀背", "旅行版", "硬顶敞篷车", "软顶敞篷车", "硬顶跑车", "客车", "货车"};
    public static final String[] production = {"自主", "合资", "进口"};
    public static final String USER_SP = "USER_SP";
    public static final String ORDER_ADD = "/ycorder/add.action";
    public static final String PAY = "/ycorder/payment.action";
    public static final String BACK = "/ycorder/sblack.action";
    public static final String QUERY_LIST = "/ycstore/query.action";
    public static final String ORDER_CAR_ADD = "/order/up.action";
    public static final String MY_ORDER = "/order/myOrder.action";
    public static final String HTTP_CAR_ROLL = "/roll/getall.action";
    public static final String USER_LOGOUT = "/user/logout.action";
    public static final String NEW_CATALOG = "/new/getCatalog.action";
    public static final String NEW_GETALL = "/new/getAll.action";
    public static final String NEW_CONTENT = "/new/get.action";
    public static YcOrder CURRENT_ORDER = null;
    public static User USER = null;
    public static Intent intent = new Intent(Intent.ACTION_CALL);

    public static String New_ZAN = "/new/zanAdd.action";
}
