package com.car.contractcar.myapplication.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostHttp {

	public static String RequstPostHttp(String strUrl) {
		URL url = null;
		String result="";
		try {
			url = new URL(strUrl);
			HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			urlconn.setRequestMethod("POST");
			urlconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlconn.setRequestProperty("Charset", "UTF-8");
			urlconn.setConnectTimeout(10000);
			urlconn.setReadTimeout(15000);

			urlconn.connect();

			OutputStream outStream = urlconn.getOutputStream();
			outStream.flush();
			outStream.close();


			InputStream is = urlconn.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String readLine = "";
			while ((readLine = bufferedReader.readLine()) != null) {
				result += readLine;
			}
			
			bufferedReader.close();
			urlconn.disconnect();
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return result;
	}

}
