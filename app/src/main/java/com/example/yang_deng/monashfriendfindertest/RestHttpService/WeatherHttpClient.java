package com.example.yang_deng.monashfriendfindertest.RestHttpService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {
	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
	private static String APIkey = "16fe9c84c9003c84d351a5f81a47f4e6";

	public String getWeatherData(String transfer) {
		HttpURLConnection con = null ;
		InputStream is = null;
		String[] parameter = transfer.split(" ");
		String latitude = parameter[0];
		String longitude = parameter[1];
		try {
			con = (HttpURLConnection) ( new URL(BASE_URL + "lat=" + latitude + "&lon=" + longitude + "&APPID=" + APIkey)).openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			
			// Let's read the response
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (  (line = br.readLine()) != null )
				buffer.append(line + "\r\n");
			
			is.close();
			con.disconnect();
			return buffer.toString();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}
		return null;
	}
}
