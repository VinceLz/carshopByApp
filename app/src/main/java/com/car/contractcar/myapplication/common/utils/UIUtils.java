package com.car.contractcar.myapplication.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.car.contractcar.myapplication.MyApplication;
import com.car.contractcar.myapplication.R;


/**
 * Created by Administrator on 2015/12/11.
 */
public class UIUtils {


    private static final String CAR_SP = "CAR_SP";

    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    public static View getXmlView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    /**
     * 1dp---1px;
     * 1dp---0.75px;
     * 1dp---0.5px;
     * ....
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    ;

    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    public static Context getContext() {
        return MyApplication.context;
    }

    public static Handler getHandler() {
        return MyApplication.handler;
    }

    /**
     * 保证runnable对象的run方法是运行在主线程当中
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    private static boolean isInMainThread() {
        //当前线程的id
        int tid = android.os.Process.myTid();
        if (tid == MyApplication.mainThreadId) {
            return true;
        }
        return false;
    }


    public static void Toast(String text, boolean isLong) {
        Toast.makeText(getContext(), text, isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }


    public static void startAnActivity(Intent intent, Activity activity) {
        activity.startActivity(intent);
        //    右往左推出效果
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    public static String SpgetString(String key) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(CAR_SP, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void SpputString(String key, String value) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(CAR_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }
}
