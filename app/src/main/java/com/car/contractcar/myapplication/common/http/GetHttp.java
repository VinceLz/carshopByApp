package com.car.contractcar.myapplication.common.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHttp {

    //通过URL获取字符串
    public static String getStringByUrlOnce(String url) {

        String str = "";
        try {
            Log.v("getStringByUrlOnce", "xxx " + url);
            URL myUrl = new URL(url);
            HttpURLConnection myconn = (HttpURLConnection) myUrl.openConnection();
            myconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            myconn.setConnectTimeout(5000);
            myconn.setReadTimeout(5000);
            InputStream stream = myconn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            str = sb.toString();

            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
            //Log.v("getStringByUrl err", err);
            return null;
        }
        return str;
    }


    public static InputStream getIoByUrlOnce(String url) {

        String str = "";
        InputStream stream = null;
        Log.v("getStringByUrlOnce", "xxx " + url);
        try {
            URL myUrl = new URL(url);
            HttpURLConnection myconn = (HttpURLConnection) myUrl.openConnection();
            myconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            myconn.setConnectTimeout(5000);
            myconn.setReadTimeout(5000);
            stream = myconn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }
}
