package com.car.contractcar.myapplication.common.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/24.
 */
public class jiexiJSON {

    public static int getPageNo(String json) throws JSONException {
        JSONObject object = new JSONObject(json).getJSONObject("page");
        return object.getInt("pageNo");
    }

    public static int getTotalPage(String json) throws JSONException {
        JSONObject object = new JSONObject(json).getJSONObject("page");
        return object.getInt("totalPage");
    }

    public static ArrayList<HashMap<String, Object>> Solve(String json) throws Exception {
        JSONObject object = new JSONObject(json).getJSONObject("page");
        ArrayList<HashMap<String, Object>> s = new ArrayList<>();


        String pageNo = object.getString("pageNo");
        String totalPage = object.getString("totalPage");


        JSONArray results = object.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            HashMap<String, Object> result = new HashMap<>();
            JSONObject o = results.getJSONObject(i);
            result.put("gid", o.get("gid"));
            result.put("gname", o.get("gname"));
            result.put("gprice", o.get("gprice"));
            result.put("sale", o.get("sale"));
            result.put("bname", o.get("bname"));
            result.put("gphone", o.get("gphone"));
            result.put("cname", o.get("cname"));
            s.add(result);
        }
        return s;
    }
}
