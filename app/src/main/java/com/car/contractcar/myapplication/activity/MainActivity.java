package com.car.contractcar.myapplication.activity;

import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.entity.BuyCarIndex2;
import com.car.contractcar.myapplication.fragment.BuycarFragment2;
import com.car.contractcar.myapplication.fragment.FindFragment;
import com.car.contractcar.myapplication.fragment.KeepcarFragment;
import com.car.contractcar.myapplication.fragment.MeFragment;
import com.tencent.bugly.beta.Beta;


public class MainActivity extends FragmentActivity {


    private static final String TAG = "XUZI";
    private FragmentTransaction ft;

    public BuycarFragment2 buycarFragment;
    private KeepcarFragment keepcarFragment;
    private FindFragment findFragment;
    private MeFragment meFragment;
    private FrameLayout content;
    private ImageView iv_buycar;
    private TextView tv_buycar;
    private LinearLayout ll_buycar;
    private ImageView iv_keepcar;
    private TextView tv_keepcar;
    private LinearLayout ll_keepcar;
    private ImageView iv_find;
    private TextView tv_find;
    private LinearLayout ll_find;
    private ImageView iv_me;
    private TextView tv_me;
    private LinearLayout ll_me;
    private LocationManager locationManager;
    private String locationProvider;
    public static LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    public static BDLocation bdLocation;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingDialog = new LoadingDialog(this, "定位中...");
// 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);
        initView();
        //  initLocation();
        // loadingDialog.show();
        setSelect(0);
        getLocation();
        Beta.checkUpgrade();//检查版本号
    /*
        定位测量： 先直接获取home.action接口的数据，然后启动一个异步线程，当拿到了经纬度，
        就去刷新首页的
        数据
     */

    }

    /**
     * 获得所在位置经纬度及详细地址
     */
    public LocationClient getLocation() {
        // 声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(0);// 设置发起定位请求的时间间隔 单位ms 0表示定位一次
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setOpenGps(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();
        return mLocationClient;
    }


    public void changeTab(View view) {
        switch (view.getId()) {
            case R.id.ll_buycar:
                setSelect(0);
                break;
            case R.id.ll_keepcar:
                setSelect(1);
                break;
            case R.id.ll_find:
                setSelect(2);
                break;
            case R.id.ll_me:
                setSelect(3);
                break;
        }
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        reSetTab();
        hideFragment();
        switch (i) {
            case 0:
                //购车
                if (buycarFragment == null) {
                    buycarFragment = new BuycarFragment2();
                    ft.add(R.id.content, buycarFragment);
                }
                iv_buycar.setImageResource(R.mipmap.bid01);
                tv_buycar.setTextColor(Color.parseColor("#18B4ED"));
                ft.show(buycarFragment);
                break;
            case 1:
                //养车
                if (keepcarFragment == null) {
                    keepcarFragment = new KeepcarFragment();
                    ft.add(R.id.content, keepcarFragment);
                }
                iv_keepcar.setImageResource(R.mipmap.bid03);
                tv_keepcar.setTextColor(Color.parseColor("#18B4ED"));
                ft.show(keepcarFragment);
                break;
            case 2:
                //发现
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    ft.add(R.id.content, findFragment);
                }
                iv_find.setImageResource(R.mipmap.bid07);
                tv_find.setTextColor(Color.parseColor("#18B4ED"));
                ft.show(findFragment);
                break;
            case 3:

                //我
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    ft.add(R.id.content, meFragment);
                }
                iv_me.setImageResource(R.mipmap.bid05);
                tv_me.setTextColor(Color.parseColor("#18B4ED"));
                ft.show(meFragment);
                break;
        }
        ft.commit();
    }

    private void reSetTab() {
        iv_buycar.setImageResource(R.mipmap.bid02);
        iv_keepcar.setImageResource(R.mipmap.bid04);
        iv_find.setImageResource(R.mipmap.bid08);
        iv_me.setImageResource(R.mipmap.bid06);
        tv_buycar.setTextColor(Color.parseColor("#878787"));
        tv_keepcar.setTextColor(Color.parseColor("#878787"));
        tv_find.setTextColor(Color.parseColor("#878787"));
        tv_me.setTextColor(Color.parseColor("#878787"));
    }

    private void hideFragment() {
        if (buycarFragment != null) {
            ft.hide(buycarFragment);
        }
        if (keepcarFragment != null) {
            ft.hide(keepcarFragment);
        }
        if (findFragment != null) {
            ft.hide(findFragment);
        }
        if (meFragment != null) {
            ft.hide(meFragment);
        }
    }

    private void initView() {
        content = (FrameLayout) findViewById(R.id.content);
        iv_buycar = (ImageView) findViewById(R.id.iv_buycar);
        tv_buycar = (TextView) findViewById(R.id.tv_buycar);
        ll_buycar = (LinearLayout) findViewById(R.id.ll_buycar);
        iv_keepcar = (ImageView) findViewById(R.id.iv_keepcar);
        tv_keepcar = (TextView) findViewById(R.id.tv_keepcar);
        ll_keepcar = (LinearLayout) findViewById(R.id.ll_keepcar);
        iv_find = (ImageView) findViewById(R.id.iv_find);
        tv_find = (TextView) findViewById(R.id.tv_find);
        ll_find = (LinearLayout) findViewById(R.id.ll_find);
        iv_me = (ImageView) findViewById(R.id.iv_me);
        tv_me = (TextView) findViewById(R.id.tv_me);
        ll_me = (LinearLayout) findViewById(R.id.ll_me);
        ll_buycar.setOnClickListener(MyLayoutCreator);
        ll_find.setOnClickListener(MyLayoutCreator);
        ll_keepcar.setOnClickListener(MyLayoutCreator);
        ll_me.setOnClickListener(MyLayoutCreator);
    }

    View.OnClickListener MyLayoutCreator = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeTab(view);
        }
    };

    private static int count = 0;
    long firClick;
    long secClick;

    @Override
    public void onBackPressed() {
        count++;
        if (count == 1) {
            firClick = System.currentTimeMillis();
            UIUtils.Toast("再次点击退出行吧", false);

        } else if (count == 2) {
            secClick = System.currentTimeMillis();
            if (secClick - firClick < 1500) {
                //双击事件
                super.onBackPressed();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            count = 0;
            firClick = 0;
            secClick = 0;
        }
    }


    /**
     * activity销毁时移除监听器
     */
    @Override
    protected void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
            mLocationClient.stop();
        }
        super.onDestroy();
//        if (locationManager != null) {
//            //移除监听器
//            locationManager.removeUpdates(locationListener);
//        }

    }

    class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            Log.d("-----", "MainActiviy 地址回调 " + location);
            // 非空判断
            if (location != null) {
                Log.d("-----", "location 不为空 ");
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                bdLocation = location;
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.i(TAG, "latitude:" + latitude
                        + " longitude:" + longitude + "---");
                // if (mLocationClient.isStarted()) {
                String url = Constant.HTTP_BASE + Constant.HTTP_HOME + "?longitude=" + bdLocation.getLongitude() + "&latitude=" + bdLocation.getLatitude();
                HttpUtil.get(url, new HttpUtil.callBlack() {
                    @Override
                    public void succcess(final String code) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BuyCarIndex2 buyCArIndex2 = (BuyCarIndex2) JsonUtils.json2Bean(code, BuyCarIndex2.class);
                                buycarFragment.setBuyCArIndex2(buyCArIndex2);
                                if (buycarFragment.status == 1) {
                                    //还没刷新呢
                                    buycarFragment.refreshView();
                                    mLocationClient.unRegisterLocationListener(mBDLocationListener);
                                    mLocationClient.stop();
                                    Log.d("-----", "location stop.....");
                                } else {
                                    Log.d("-----", "location getLocation()");
                                    onReceiveLocation(location);
                                }
                            }
                        });
                    }

                    @Override
                    public void fail(String code) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void err() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, 3600 * 2);
            }
        }
    }

}
