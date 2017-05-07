package com.example.yang_deng.monashfriendfindertest.RestHttpService;

/**
 * Created by Yang-Deng on 22/4/17.
 */

import java.net.HttpURLConnection;
import java.net.URL;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.PrintWriter;

public class RestHttpPutClient {
    private static final String BASE_URI = "http://10.0.0.24:8080/TESTFOR5046ASS/webresources";

    public static void UpdateStudentProfile(StudentProfile studentprofile) {
        final String methodPath = "/test.studentprofile/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String id = null;
        //Making HTTP request
        try {
            id = studentprofile.getStudentNumber().toString();
            url = new URL(BASE_URI + methodPath + id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            String stringStudentProfileJson=gson.toJson(studentprofile);
            out.print(stringStudentProfileJson);
            out.flush();
            out.close();
            System.out.println("Hello");
            System.err.println(conn.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
}