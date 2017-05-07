package com.example.yang_deng.monashfriendfindertest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpClientForLocation;
import com.example.yang_deng.monashfriendfindertest.Model.StudentLocation;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapquest.mapping.maps.MapView;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapActivityForFriends extends AppCompatActivity implements View.OnClickListener {
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private String Friends;
    private StudentProfile[] StudentProfilesFromMainActivity;
    private ArrayList<StudentProfile> StuProArrayList;
    GPSTracker gps;
    private double latitude;
    private double longitude;
    private LatLng CurrentLocation;
    private StudentLocation[] studentLocations;
    private ArrayList<String> FriendsStuNumberList = new ArrayList<String>();
    private ArrayList<String> LocationList = new ArrayList<String>();
    private Button FriendCheck;
    private Spinner Distance;
    private ArrayList<Marker> markerOptionsesContainer = new ArrayList<>();
    private HashMap<StudentProfile, Double[]> returnHashMap2 = new HashMap<StudentProfile, Double[]>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Friends = extras.getString("CurrentFriends");
        }
        Gson gson = new Gson();
        StudentProfilesFromMainActivity = gson.fromJson(Friends, StudentProfile[].class);
        StuProArrayList = new ArrayList<StudentProfile>(Arrays.asList(StudentProfilesFromMainActivity));
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
/*        Distance = (Spinner) findViewById(R.id.spinner_distance);
        FriendCheck = (Button) findViewById(R.id.button_distance);
        FriendCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String text = Distance.getSelectedItem().toString();
                String[] distanceArray = text.split(" ");
                int distance = Integer.parseInt(distanceArray[0]);
                distance = distance * 1000;
                System.out.print(distance);
            }
        });*/
        MapboxAccountManager.start(getApplicationContext());
        setContentView(R.layout.fragment_map2);
        Distance = (Spinner) findViewById(R.id.spinner_distance);
        FriendCheck = (Button) findViewById(R.id.button_distance);
        FriendCheck.setOnClickListener(this);
        mMapView = (MapView) findViewById(R.id.mapquestMapView2);
        mMapView.onCreate(savedInstanceState);
        new AsyncTask<ArrayList<StudentProfile>, Void, HashMap<StudentProfile, Double[]>>() {
            @Override
            protected HashMap<StudentProfile, Double[]> doInBackground(ArrayList<StudentProfile>... params) {
                HashMap<StudentProfile, Double[]> returnHashMap = new HashMap<StudentProfile, Double[]>();
                for (int i = 0; i < params[0].size(); i++) {
                    String Locations = new RestHttpClientForLocation().GetLocationsByStuNum(params[0].get(i).getStudentNumber().toString());
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
                    LocationList.add(LatestLocation.getLatitude()+" "+LatestLocation.getLongitude());
                    returnHashMap.put(params[0].get(i), returnValue);
                }
                returnHashMap2 = returnHashMap;
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
                        Iterator iter = returnHashMap.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            Object key = entry.getKey();
                            StudentProfile sp = (StudentProfile) key;
                            String FirstName = sp.getFirstName();
                            String LastName = sp.getLastName();
                            String StuNumber = sp.getStudentNumber().toString();
                            Object val = entry.getValue();
                            Double[] Location = (Double[])val;
                            LatLng FriendLocation = new LatLng(Location[0], Location[1]);
                            //addMarkerList(mMapboxMap,FriendLocation,StudentNumber,icon);
                            markerOptionsesContainer.add(mMapboxMap.addMarker(MarkerOptionsCreater(FriendLocation,StuNumber,FirstName+" "+LastName,icon)));
                        }
/*
                        mapBackup = mMapboxMap;
*/
                    }
                });
            }
        }.execute(StuProArrayList);
    }

    @Override
    public void onClick(View v) {
        ArrayList<Marker> deleteArray = new ArrayList<Marker>();
        for(Marker mk:markerOptionsesContainer){
            mMapboxMap.removeMarker(mk);
            deleteArray.add(mk);
        }
        markerOptionsesContainer.removeAll(deleteArray);
        String text = Distance.getSelectedItem().toString();
        String[] distanceArray = text.split(" ");
        int distanceM = Integer.parseInt(distanceArray[0]);
        Double DistanceDouble = Double.parseDouble(String.valueOf(distanceM));
        Iterator iter = returnHashMap2.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            StudentProfile sp = (StudentProfile) key;
            Object val = entry.getValue();
            Double[] Location = (Double[])val;
            if(DistanceCheck(sp,Location,DistanceDouble)){
                IconFactory iconFactory = IconFactory.getInstance(getApplicationContext());
                Icon icon = iconFactory.fromResource(R.drawable.purple_marker);
                String StuNumber = sp.getStudentNumber().toString();
                String FirstName = sp.getFirstName();
                String LastName = sp.getLastName();
                LatLng FriendLocation = new LatLng(Location[0], Location[1]);
                markerOptionsesContainer.add(mMapboxMap.addMarker(MarkerOptionsCreater(FriendLocation,StuNumber,FirstName+" "+LastName,icon)));
            }
        }
    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CurrentLocation);
        markerOptions.title("Your location now");
        markerOptions.snippet("");
        mapboxMap.addMarker(markerOptions);
    }

    private MarkerOptions MarkerOptionsCreater(LatLng Location,String StuNumber,String Snippet,Icon icon){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Location);
        markerOptions.title("StuNo."+StuNumber);
        markerOptions.snippet("Name."+Snippet);
        markerOptions.icon(icon);
        return markerOptions;
    }

    public static double CalculateDistance(double lat1, double long1, double lat2, double long2) {
        double GapA, GapB, Radius;
        Radius = 6378137; // earth radius（m）
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        GapA = lat1 - lat2;
        GapB = (long1 - long2) * Math.PI / 180.0;
        double distance;
        double sa2, sb2;
        sa2 = Math.sin(GapA/2.0);
        sb2 = Math.sin(GapB/2.0);
        distance = 2 * Radius * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(sa2) * Math.cos(lat2) * sb2 * sb2));
        return distance;
    }

    public boolean DistanceCheck (StudentProfile SP,Double[] Location, Double Distance){
            double latitude1 = Location[0];
            double longitude1 = Location[1];
            double latitude2 = latitude;
            double longitude2 = longitude;
            double DistanceByCal = CalculateDistance(latitude1,longitude1,latitude2,longitude2);
            if(DistanceByCal <= Distance){
                return true;
            }
            else{
                return false;
            }
    }
}
