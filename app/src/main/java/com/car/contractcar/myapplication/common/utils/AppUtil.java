package com.car.contractcar.myapplication.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.car.contractcar.myapplication.MyApplication.context;

/**
 * Created by Administrator on 2017/5/12.
 */

public class AppUtil {


    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public static boolean openMap(Context context, String bname, String latitude, String longitude) {
        Intent intent3 = null;
        if (AppUtil.isAvilible(context, "com.autonavi.minimap")) {

            intent3 = new Intent();
            intent3.setData(Uri.parse("androidamap://viewMap?sourceApplication=行吧&poiname=" + bname + "&lat=" + latitude + "&lon=" + longitude + "&dev=0"));
            intent3.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent3);
            return true;

        } else if (AppUtil.isAvilible(context, "com.baidu.BaiduMap")) {
            intent3 = new Intent();
            intent3.setData(Uri.parse("baidumap://map/marker?location=" + latitude + "," + longitude + "&title=" + bname + "&content=" + bname + "&traffic=on"));
            intent3.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent3);
            return true;
        } else {
            return false;
        }
    }
}

