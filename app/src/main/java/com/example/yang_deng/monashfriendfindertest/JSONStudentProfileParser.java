package com.example.yang_deng.monashfriendfindertest;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yang-Deng on 23/4/17.
 */
/*public class JSONStudentProfileParser {
    public static StudentProfile getStudentProfile(String data) throws JSONException {
        StudentProfile stringCourseJson = new StudentProfile();
        try {
            Gson gson = new Gson();
            data = data.replace("[","");
            data = data.replace("]","");
            stringCourseJson = gson.fromJson(data, StudentProfile.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return stringCourseJson;
    }
}*/


public class JSONStudentProfileParser {

    public static StudentProfile getStudentProfile(String data) throws JSONException {
        StudentProfile studentprofile = new StudentProfile();
/*
        JSONArray json = new JSONArray(data);
*/
        JSONObject jObj = new JSONObject(data);
       /* JSONObject JSONStudentProfile = (JSONObject) json.get(0);*/
/*       studentprofile.Address = getString("address", JSONStudentProfile);
        studentprofile.Student_Number = getInt("studentNumber", JSONStudentProfile);
        studentprofile.Course = getString("course", JSONStudentProfile);
        studentprofile.Current_Job = getString("currentJob", JSONStudentProfile);
        studentprofile.DOB = getString("dob", JSONStudentProfile);
        studentprofile.Favourite_Movie = getString("favouriteMovie", JSONStudentProfile);
        studentprofile.Favourite_Sport = getString("favouriteSport", JSONStudentProfile);
        studentprofile.Favourite_Unit_Code = getString("favouriteUnitCode", JSONStudentProfile);
        studentprofile.Gender = getString("gender", JSONStudentProfile);
        studentprofile.Monash_Email = getString("monashEmail", JSONStudentProfile);
        studentprofile.Native_Language = getString("nativeLanguage", JSONStudentProfile);
        studentprofile.Nationality = getString("nationality", JSONStudentProfile);
        studentprofile.Subscription_Time = getString("subsciptionTime", JSONStudentProfile);
        studentprofile.Subsription_Data = getString("subsciptionData", JSONStudentProfile);
        studentprofile.Subsurb = getString("suburb", JSONStudentProfile);
        studentprofile.Study_Mode = getString("studyMode",JSONStudentProfile);
        studentprofile.Password = getString("password", JSONStudentProfile);
        studentprofile.First_Name = getString("firstName", JSONStudentProfile);
        studentprofile.Last_Name = getString("lastName", JSONStudentProfile);
*/



        studentprofile.setStudentNumber(getInt("studentNumber", jObj));
        studentprofile.setAddress(getString("address", jObj));
        studentprofile.setCourse(getString("course", jObj));
        studentprofile.setCurrentJob(getString("currentJob", jObj));
        studentprofile.setDob(getDate("dob", jObj));
        studentprofile.setFavouriteMovie(getString("favouriteMovie", jObj));
        studentprofile.setFavouriteSport(getString("favouriteSport", jObj));
        studentprofile.setFavouriteUnitCode(getString("favouriteUnitCode", jObj));
        studentprofile.setGender(getString("gender", jObj));
        studentprofile.setFirstName(getString("firstName", jObj));
        studentprofile.setLastName(getString("lastName", jObj));
        studentprofile.setMonashEmail(getString("monashEmail", jObj));
        studentprofile.setNationality(getString("nationality", jObj));
        studentprofile.setNativeLanguage(getString("nativeLanguage", jObj));
        studentprofile.setPassword(getString("password", jObj));
        studentprofile.setSubsciptionData(getString("subsciptionData", jObj));
        studentprofile.setSubsciptionTime(getDate("subsciptionTime", jObj));
        studentprofile.setSuburb(getString("suburb", jObj));
        studentprofile.setStudyMode(getString("studyMode",jObj));


/*        JSONObject address = json.getJSONObject(0);
        JSONObject course = json.getJSONObject(1);
        JSONObject currentJob = json.getJSONObject(2);
        JSONObject dob = json.getJSONObject(3);
        JSONObject favouriteMovie = json.getJSONObject(4);
        JSONObject favouriteSport = json.getJSONObject(5);
        JSONObject favouriteUnitCode = json.getJSONObject(6);
        JSONObject firstName = json.getJSONObject(7);
        JSONObject gender = json.getJSONObject(8);
        JSONObject lastName = json.getJSONObject(9);
        JSONObject monashEmail = json.getJSONObject(10);
        JSONObject nationality = json.getJSONObject(11);
        JSONObject nativeLanguage = json.getJSONObject(12);
        JSONObject password = json.getJSONObject(13);
        JSONObject studentNumber = json.getJSONObject(14);
        JSONObject studyMode = json.getJSONObject(15);
        JSONObject suburb = json.getJSONObject(16);*//*


*/
/*        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // We start extracting the info
        Location loc = new Location();

        JSONObject coordObj = getObject("coord", jObj);
        loc.setLatitude(getFloat("lat", coordObj));
        loc.setLongitude(getFloat("lon", coordObj));

        JSONObject sysObj = getObject("sys", jObj);
        loc.setCountry(getString("country", sysObj));
        loc.setSunrise(getInt("sunrise", sysObj));
        loc.setSunset(getInt("sunset", sysObj));
        loc.setCity(getString("name", jObj));
        weather.location = loc;

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("weather");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
        weather.currentCondition.setDescr(getString("description", JSONWeather));
        weather.currentCondition.setCondition(getString("main", JSONWeather));
        weather.currentCondition.setIcon(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jObj);
        weather.currentCondition.setHumidity(getInt("humidity", mainObj));
        weather.currentCondition.setPressure(getInt("pressure", mainObj));
        weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weather.temperature.setTemp(getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = getObject("wind", jObj);
        weather.wind.setSpeed(getFloat("speed", wObj));
        weather.wind.setDeg(getFloat("deg", wObj));

        // Clouds
        JSONObject cObj = getObject("clouds", jObj);
        weather.clouds.setPerc(getInt("all", cObj));

        // We download the icon to show


        return weather;*/

        return studentprofile;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        if(jObj.has(tagName)) {
            return jObj.getString(tagName);
        }
        else {
            return null;
        }
    }

    private static Date getDate(String tagName, JSONObject jObj) throws JSONException {
        if(jObj.has(tagName)) {
            String trans = jObj.getString(tagName);
            Date parsedDate = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");;  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
                parsedDate = sdf.parse(trans);
            } catch (Exception e) {//this generic but you can control another types of exception
                e.printStackTrace();
            }
            return parsedDate;
        }
        else{
            return null;
        }
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    public static String ConvertDateType(String oldFormat) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        Date date = df.parse(oldFormat);

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df2.format(date);
    }
}
