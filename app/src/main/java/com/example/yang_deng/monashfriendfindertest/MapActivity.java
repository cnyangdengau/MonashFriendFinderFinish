package com.example.yang_deng.monashfriendfindertest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpClientForLocation;
import com.example.yang_deng.monashfriendfindertest.Model.StudentLocation;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapActivity extends AppCompatActivity {

    private final LatLng SAN_FRAN = new LatLng(37.7749, -122.4194);
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private String Findedguys;
    private StudentProfile[] StudentProfilesFromMainActivity;
    GPSTracker gps;
    private double latitude;
    private double longitude;

    private final LatLng Mel_Mon = new LatLng(-37.876336, 145.043790);
    private LatLng CurrentLocation;
    private StudentLocation[] studentLocations;
    private ArrayList<String> FriendsStuNumberList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Findedguys = extras.getString("Findedguys");
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        StudentProfilesFromMainActivity = gson.fromJson(Findedguys, StudentProfile[].class);
/*        for (int i = 0; i <= (StudentProfilesFromMainActivity.length-1); i++) {
            FriendsStuNumberList.add(StudentProfilesFromMainActivity[i].getStudentNumber().toString());
        }*/
        for(StudentProfile sp:StudentProfilesFromMainActivity){
            FriendsStuNumberList.add(sp.getStudentNumber().toString());
        }
        // create class object
        gps = new GPSTracker(getApplicationContext());
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            CurrentLocation = new LatLng(latitude, longitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        MapboxAccountManager.start(getApplicationContext());
        setContentView(R.layout.fragment_map);
        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        new AsyncTask<StudentProfile[], Void, HashMap<StudentProfile, Double[]>>() {
            @Override
            protected HashMap<StudentProfile, Double[]> doInBackground(StudentProfile[]... params) {
                HashMap<StudentProfile, Double[]> returnHashMap = new HashMap<StudentProfile, Double[]>();
                for (int i = 0; i < params[0].length; i++) {
                    String Locations = new RestHttpClientForLocation().GetLocationsByStuNum(params[0][i].getStudentNumber().toString());
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                    studentLocations = gson.fromJson(Locations, StudentLocation[].class);
                    int k = 0;
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, 1990);
                    cal.set(Calendar.MONTH, 10);
                    cal.set(Calendar.DAY_OF_MONTH, 12);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    Date date = cal.getTime();
                    for (int j = 0; j < studentLocations.length; j++) {
                        if (studentLocations[j].getTimeStamp().compareTo(date) >= 0) {
                            k = j;
                        }
                    }
                    StudentLocation LatestLocation = studentLocations[k];
                    Double[] returnValue = new Double[2];
                    returnValue[0] = Double.parseDouble(LatestLocation.getLatitude());
                    returnValue[1] = Double.parseDouble(LatestLocation.getLongitude());
                    returnHashMap.put(params[0][i], returnValue);
                }
                return returnHashMap;
            }
            @Override
            protected void onPostExecute(final HashMap<StudentProfile, Double[]> returnHashMap) {
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(MapboxMap mapboxMap) {
                        // Create an Icon object for the marker to use
                        IconFactory iconFactory = IconFactory.getInstance(getApplicationContext());
                        Icon icon = iconFactory.fromResource(R.drawable.purple_marker);
                        mMapboxMap = mapboxMap;
                        mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, 13));
                        addMarker(mMapboxMap);
                        HashMap map = new HashMap<String, Double[]>();
                        map = returnHashMap;
                        Iterator iter = map.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            Object key = entry.getKey();
                            StudentProfile SP = (StudentProfile) key;
                            Object val = entry.getValue();
                            Double[] Location = (Double[])val;
                            LatLng FriendLocation = new LatLng(Location[0], Location[1]);
                            String StudentNumber = SP.getStudentNumber().toString();
                            String FirstName = SP.getFirstName();
                            String LastName = SP.getLastName();
                            addMarkerList(mMapboxMap,FriendLocation,"Stu No." + StudentNumber,"First Name: "+FirstName+"\nLast Name: "+LastName,icon);
                        }
                    }
                });
            }
        }.execute(StudentProfilesFromMainActivity);
    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CurrentLocation);
        markerOptions.title("Your location now");
        markerOptions.snippet("Your location");
        mapboxMap.addMarker(markerOptions);
    }

    private void addMarkerList(MapboxMap mapboxMap,LatLng Location,String StuNumber,String Snippet,Icon icon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Location);
        markerOptions.title(StuNumber);
        markerOptions.snippet(Snippet);
        markerOptions.icon(icon);
        mapboxMap.addMarker(markerOptions);
    }
}