package com.example.yang_deng.monashfriendfindertest.RestHttpService;

/**
 * Created by YangDeng on 28/4/17.
 */
import android.util.Log;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class SearchRestHttpClient {
    private static final String BASE_URI = "http://10.0.0.24:8080/TESTFOR5046ASS/webresources/";

    public static String SearchFriend(int studentnumber, List<String> parameters) {
        final String methodPath = "test.studentprofile/ReportWSd/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String Keywords = "/";
        for(String keywords:parameters){
            Keywords = Keywords + keywords + ",";
        }
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath + studentnumber + Keywords);
            //open the connection
/*
            url = new URL("http://10.0.0.24:8080/TESTFOR5046ASS/webresources/test.studentprofile/ReportWSd/28215701/nativeLanguage/");
*/
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static String QueryByCourses(String Input) {
        final String methodPath = "/week4.student/findByCourse/" + Input;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
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
            conn.disconnect(); }
        return textResult;
    }

    public static void deleteStudent(int studentId){
        final String methodPath ="/week4.student/"+ studentId;
        URL url = null; HttpURLConnection conn = null; String txtResult="";
        // Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the connection method to GET
            conn.setRequestMethod("DELETE");
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) { e.printStackTrace();
        } finally {
            conn.disconnect(); }
    }
}

