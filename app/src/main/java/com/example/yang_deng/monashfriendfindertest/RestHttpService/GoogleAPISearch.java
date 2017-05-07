package com.example.yang_deng.monashfriendfindertest.RestHttpService;

/**
 * Created by Yang-Deng on 22/4/17.
 */

import android.util.Log;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GoogleAPISearch {
    private static final String BASE_URI = "https://www.googleapis.com/customsearch/v1?key=";
    private static final String API_key = "AIzaSyCKfT7SsLNvLlQVzm0DJmCL41F3Prcm51Q";
    private static final String SEARCH_ID_cx = "011120474831095172469:ixgqh47ix0u";

    public String GetMovieJsonData(String Keyword) {
        String API_key = "AIzaSyCKfT7SsLNvLlQVzm0DJmCL41F3Prcm51Q";
        String SEARCH_ID_cx = "011120474831095172469:ixgqh47ix0u";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + API_key + "&cx=" + SEARCH_ID_cx + "&q="+ Keyword + "&num=1");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine(); }
        } catch (Exception e) { e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
}