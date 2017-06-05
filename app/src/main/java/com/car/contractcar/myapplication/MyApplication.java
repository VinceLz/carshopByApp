package com.car.contractcar.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.loginandr.bean.User;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;


/**
 * Created by Administrator on 2015/12/11.
 */
public class MyApplication extends Application {

    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static MyApplication app;
    public static int mainThreadId = 0;


    @Override
    public void onCreate() {
        context = getApplicationContext();
        app = this;
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
        new HttpUtil(this);//加载okhttp &  图片加载
        initUser();
        initErrorLog();
    }

    public static MyApplication getInst() {
        return app;
    }

    public void initErrorLog() {
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this); //App的策略Bean
//        strategy.setAppVersion(CommentUtil.getVersion(this));
//        strategy.setAppReportDelay(5000);  //设置SDK处理延时，毫秒
//        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
//
//            /**
//             *
//             * @param crashType 错误类型   CRASHTYPE_JAVA=0,CRASHTYPE_NATIVE=2;CRASHTYPE_U3D=3;CRASHTYPE_ANR=4
//             * @param errorType 错误类型名
//             * @param errorMessage 错误信息
//             * @param errorStack 错误堆栈
//             * @param //Map<String key, String value>  额外的自定义上传信息
//             * @return
//             */
//            @Override
//            public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
//                FileOutputStream fos = null;
//                try {
//                    File file = new File(Environment.getDataDirectory(), "/error.log");
//                    fos = new FileOutputStream(file);
//                    String time = CommentUtil.getDateFormat(System.currentTimeMillis());
//                    String error = "crashType----->" + crashType + "--errorType--" + errorType + "--errorMessage-->" + errorMessage + "--errorStack-->" + errorStack + "_发生异常时间：" + time;
//                    fos.write(error.getBytes());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (fos != null) {
//                        try {
//                            fos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        fos = null;
//                    }
//                }
//                //专注自杀的方法，就是杀死自己的进程， 早死早超生，但杀死进程后，将无法上传异常到腾讯bugly
//                //   android.os.Process.killProcess(android.os.Process.myPid());  后期做吧
//                return null;
//            }
//        });
        //Beta.enableNotification = false;  是否显示更新进度
        Beta.autoCheckUpgrade = false;//设置不自动检查
        Beta.showInterruptedStrategy = true;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Beta.canShowUpgradeActs.add(MainActivity.class);
        // Bugly.init(this, "b424e21a83", true, strategy); //自定义策略生效，必须在初始化SDK前调用
        Bugly.init(this, "b424e21a83", false);
    }

    private void initUser() {
        String s = UIUtils.SpgetString(Constant.USER_SP);
        Log.d("-----", s);
        if (s != null && !s.isEmpty()) {
            Constant.USER = JSON.parseObject(s, User.class);
        }

    }


}