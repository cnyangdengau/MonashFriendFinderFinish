package com.example.yang_deng.monashfriendfindertest.RestHttpService;

import android.util.Log;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yang-Deng on 24/4/17.
 */

public class RestHttpPostClient {
    private static final String BASE_URI = "http://10.0.0.24:8080/TESTFOR5046ASS/webresources";
    public static void createStudentProfile(StudentProfile studentprofile){ //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/test.studentprofile/";
        try {
            /*Gson gson =new Gson();*/
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            String stringStudentProfileJson=gson.toJson(studentprofile);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringStudentProfileJson.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringStudentProfileJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) { e.printStackTrace();
        } finally {
            conn.disconnect(); }
    }
/*    public static void updateStudentProfile(StudentProfile studentprofile){ //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/test.studentprofile/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            String stringStudentProfileJson=gson.toJson(studentprofile);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("PUT"); //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringStudentProfileJson.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringStudentProfileJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) { e.printStackTrace();
        } finally {
            conn.disconnect(); }
    }*/

}
