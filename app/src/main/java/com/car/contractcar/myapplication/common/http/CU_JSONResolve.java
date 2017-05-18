package com.car.contractcar.myapplication.common.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CU_JSONResolve {

	private static String getSTR(JSONObject jO, String key) {

		String value = "";

		if (key.equals(""))
			return value;

		try {
			if (jO.has(key))
				value = jO.getString(key);
		} catch (JSONException e) {

		}

		return value.equals("null") ? "" : value;
	}


	private static HashMap<String, Object> getHashMap1(JSONObject jO, String STR_field[]) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		if (STR_field == null)
			return hashMap;

		if (STR_field != null && STR_field.length != 0)
			for (int i = 0; i < STR_field.length; i++)
				hashMap.put(STR_field[i], getSTR(jO, STR_field[i]));

		return hashMap;
	}


	private static HashMap<String, Object> getHashMap2(JSONObject jO, String STR1_field[], String LIST1_field[], ArrayList<String[]> aL_STR2_field) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		if (STR1_field == null && LIST1_field == null)
			return hashMap;

		if (STR1_field != null && STR1_field.length != 0)
			for (int i = 0; i < STR1_field.length; i++)
				hashMap.put(STR1_field[i], getSTR(jO, STR1_field[i]));

		if (LIST1_field != null && LIST1_field.length != 0)
			if (aL_STR2_field != null && aL_STR2_field.size() == LIST1_field.length)
				for (int i = 0; i < LIST1_field.length; i++)
					if (aL_STR2_field.get(i) != null && aL_STR2_field.get(i).length != 0)
						hashMap.put(LIST1_field[i], getLIST1(jO, LIST1_field[i], aL_STR2_field.get(i)));
		return hashMap;
	}


	private static ArrayList<HashMap<String, Object>> getLIST1(JSONObject jO, String key, String STR_field[]) {

		ArrayList<HashMap<String, Object>> value = new ArrayList<HashMap<String, Object>>();

		if (key.equals(""))
			return value;

		try {
			if (jO.has(key)) {
				JSONArray jA = jO.getJSONArray(key);
				if (jA != null && jA.length() != 0)
					for (int i = 0; i < jA.length(); i++) {
						JSONObject jO_item = jA.getJSONObject(i);
						value.add(getHashMap1(jO_item, STR_field));
					}
			}
		} catch (JSONException e) {

			try {
				JSONObject jsonobject = jO.getJSONObject(key);
				value.add(getHashMap1(jsonobject, STR_field));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return value;
	}


	private static ArrayList<HashMap<String, Object>> getLIST2(JSONObject jO, String key, String STR1_field[], ArrayList<String[]> aL_LIST1_field, ArrayList<ArrayList<String[]>> aLL_STR2_field) {

		ArrayList<HashMap<String, Object>> value = new ArrayList<HashMap<String, Object>>();

		if (key.equals(""))
			return value;

		try {
			if (jO.has(key)) {
				JSONArray jA = jO.getJSONArray(key);
				if (jA != null && jA.length() != 0)
					for (int i = 0; i < jA.length(); i++) {
						JSONObject jO_item = jA.getJSONObject(i);
						value.add(getHashMap2(jO_item, STR1_field, aL_LIST1_field.get(0), aLL_STR2_field.get(0)));
					}
			}
		} catch (JSONException e) {

		}

		return value;
	}



	public static HashMap<String, Object> parseHashMap1(String jsonStr, String STR1_field[]) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		try {
			if (jsonStr == null)
				return hashMap;

			if (STR1_field == null)
				return hashMap;

			JSONObject jO0 = new JSONObject(jsonStr);
			if (jO0.has("success"))
				if (!jO0.getBoolean("success"))
					return hashMap;

			if (STR1_field != null && STR1_field.length != 0)
				for (int i = 0; i < STR1_field.length; i++)
					hashMap.put(STR1_field[i], getSTR(jO0, STR1_field[i]));

		} catch (JSONException e) {

		}
		return hashMap;
	}


	public static HashMap<String, Object> parseHashMap2(String jsonStr, String STR1_field[], String LIST1_field[], ArrayList<String[]> aL_STR2_field) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		try {
			if (jsonStr == null)
				return hashMap;

			if (STR1_field == null && LIST1_field == null)
				return hashMap;

			JSONObject jO0 = new JSONObject(jsonStr);
//			if (jO0.has("success"))
//				if (!jO0.getBoolean("success"))
//					return hashMap;

			if (STR1_field != null && STR1_field.length != 0)
				for (int i = 0; i < STR1_field.length; i++)
					hashMap.put(STR1_field[i], getSTR(jO0, STR1_field[i]));

			if (LIST1_field != null && LIST1_field.length != 0)
				if (aL_STR2_field != null && aL_STR2_field.size() == LIST1_field.length)
					for (int i = 0; i < LIST1_field.length; i++)
						if (aL_STR2_field.get(i) != null && aL_STR2_field.get(i).length != 0)
							hashMap.put(LIST1_field[i], getLIST1(jO0, LIST1_field[i], aL_STR2_field.get(i)));

		} catch (JSONException e) {

		}
		return hashMap;
	}


	public static HashMap<String, Object> parseHashMap3(String jsonStr, String STR1_field[], String LIST1_field[], ArrayList<String[]> aL_STR2_field, ArrayList<String[]> aL_LIST2_field, ArrayList<ArrayList<String[]>> aLL_STR3_field) {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		try {
			if (jsonStr == null)
				return hashMap;

			if (STR1_field == null && LIST1_field == null)
				return hashMap;

			JSONObject jO0 = new JSONObject(jsonStr);
			if (jO0.has("success"))
				if (!jO0.getBoolean("success"))
					return hashMap;

			if (STR1_field != null && STR1_field.length != 0)
				for (int i = 0; i < STR1_field.length; i++)
					hashMap.put(STR1_field[i], getSTR(jO0, STR1_field[i]));

			if (LIST1_field != null && LIST1_field.length != 0)
				if (aL_STR2_field != null && aL_STR2_field.size() == LIST1_field.length)
					for (int i = 0; i < LIST1_field.length; i++)
						if (aL_STR2_field.get(i) != null && aL_STR2_field.get(i).length != 0)
							hashMap.put(LIST1_field[i], getLIST2(jO0, LIST1_field[i], aL_STR2_field.get(i), aL_LIST2_field, aLL_STR3_field));

		} catch (JSONException e) {

		}
		return hashMap;
	}
}