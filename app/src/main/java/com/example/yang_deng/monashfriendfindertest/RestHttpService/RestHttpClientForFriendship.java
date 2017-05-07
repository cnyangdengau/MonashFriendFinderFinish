package com.example.yang_deng.monashfriendfindertest.RestHttpService;

/**
 * Created by Yang-Deng on 22/4/17.
 */

import android.util.Log;

import com.example.yang_deng.monashfriendfindertest.Model.Friendship;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestHttpClientForFriendship {
    private static final String BASE_URI = "http://10.0.0.24:8080/TESTFOR5046ASS/webresources";

    public static String GetFriendshipByStuNum(String StuNum) {
        final String methodPath = "/test.friendship/findByStudentNumber/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath + StuNum);
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

    public static String GetFriendshipByStuNum2(String StuNum) {
        final String methodPath = "/test.friendship/findByStudentNumber2/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath + StuNum);
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

    public static String DeleteFriendshipByID(String ID) {
        final String methodPath = "/test.friendship/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath + ID);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("DELETE");
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

    public static String AddFriendship(Friendship friendship) {
        final String methodPath = "/test.friendship/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            /*Gson gson =new Gson();*/
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            String stringFriendship=gson.toJson(friendship);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringFriendship.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringFriendship);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) { e.printStackTrace();
        } finally {
            conn.disconnect(); }
        return textResult;
    }
}

