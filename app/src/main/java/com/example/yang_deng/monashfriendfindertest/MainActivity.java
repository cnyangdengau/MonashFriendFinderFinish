package com.example.yang_deng.monashfriendfindertest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpClient;
import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpClientForFriendship;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView SideBarName;
    TextView SideBarEmail;
    String jsonMyObject;
    String StuNum;
    private SharedPreferences ck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get Object from Login Activity;
        jsonMyObject=null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("myObject");
        }
        StudentProfile studentprofileFromLogin = new Gson().fromJson(jsonMyObject, StudentProfile.class);
        StuNum = studentprofileFromLogin.getStudentNumber().toString();

/*
        UpdateSidebarTask task = new UpdateSidebarTask();
        task.execute(studentprofileFromLogin);
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        SideBarName = (TextView)header.findViewById(R.id.textName);
        SideBarEmail = (TextView)header.findViewById(R.id.textEmail);
        SideBarName.setText(studentprofileFromLogin.getFirstName()+" "+studentprofileFromLogin.getLastName());
        SideBarEmail.setText(studentprofileFromLogin.getMonashEmail());


        Bundle bundle = new Bundle();
        bundle.putString("UserName", studentprofileFromLogin.getFirstName() + " " + studentprofileFromLogin.getLastName());
        FragmentManager fragmentManager = getFragmentManager();
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content_frame, mainFragment).commit();
    }

/*    private class UpdateSidebarTask extends AsyncTask<StudentProfile, Void, StudentProfile> {

        @Override
        protected StudentProfile doInBackground(StudentProfile... params) {
            StudentProfile sp = params[0];
            return sp;
        }

        @Override
        protected void onPostExecute(StudentProfile sp) {
            super.onPostExecute(sp);
            textView.setText(sp.getFirstName() + " " + sp.getLastName());
            textView1.setText(sp.getMonashEmail());
        }
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        if (id != R.id.nav_logout) {
            new AsyncTask<String, Void, String[]>() {
                @Override
                protected String[] doInBackground(String... params) {
                    String byStuNum = new RestHttpClientForFriendship().GetFriendshipByStuNum2(params[0]);
                    String byStuNum2 = new RestHttpClientForFriendship().GetFriendshipByStuNum2(params[0]);
                    String AllStudent = new RestHttpClient().GetAllStuProfile();
                    String JsonData;
                    String Flag;
                    String[] PassArray = new String[3];
                    if (byStuNum.isEmpty()) {
                        JsonData = byStuNum2;
                        Flag = "byStuNum2";
                    } else {
                        JsonData = byStuNum;
                        Flag = "byStuNum";
                    }
                    PassArray[0] = JsonData;
                    PassArray[1] = Flag;
                    PassArray[2] = AllStudent;
                    return PassArray;
                }

                @Override
                protected void onPostExecute(String[] PassArray) {
                    Fragment nextFragment = null;

                    switch (id) {
                        case R.id.nav_updateprofile:
                            Bundle bundle = new Bundle();
                            bundle.putString("StudentProfile", jsonMyObject);
                            nextFragment = new UpdateProfileFragment();
                            nextFragment.setArguments(bundle);
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);
                            break;
                        case R.id.nav_searchfriends:
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("StudentProfile", jsonMyObject);
                            nextFragment = new SearchFragment();
                            nextFragment.setArguments(bundle2);
                            FragmentManager fragmentManager2 = getFragmentManager();
                            fragmentManager2.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
                            DrawerLayout drawer2 = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer2.closeDrawer(GravityCompat.START);
                            break;
                        case R.id.nav_friends:
                            String[] StringArray = new String[3];
                            StringArray[0] = jsonMyObject;
                            StringArray[1] = PassArray[0];
                            StringArray[2] = PassArray[1];
                            Bundle bundle3 = new Bundle();
                            bundle3.putStringArray("ForFriends", StringArray);
                            nextFragment = new FriendsFragment();
                            nextFragment.setArguments(bundle3);
                            FragmentManager fragmentManager3 = getFragmentManager();
                            fragmentManager3.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
                            DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer3.closeDrawer(GravityCompat.START);
                            break;
                        case R.id.nav_reports:
                            Intent activity = new Intent(getApplicationContext(), ReportsActivity.class);
                            activity.putExtra("ForReports", PassArray[2]);
                            startActivity(activity);
                            break;
                        case R.id.nav_secreports:
                            Intent activity2 = new Intent(getApplicationContext(), SecReportActivity.class);
                            activity2.putExtra("StudentProfile", jsonMyObject);
                            startActivity(activity2);
                            break;
                    }
                }
                }.execute(StuNum);
                return true;
            }else{
                callPopupDialog("Are you sure to log out?");
                return false;
            }
        }


    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment nextFragment = null;

        if(id != R.id.nav_logout){
            switch (id) {
                case R.id.nav_updateprofile:
                    Bundle bundle = new Bundle();
                    bundle.putString("StudentProfile",jsonMyObject);
                    nextFragment = new UpdateProfileFragment();
                    nextFragment.setArguments(bundle);
                    break;
                case R.id.nav_searchfriends:
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("StudentProfile",jsonMyObject);
                    nextFragment = new SearchFragment();
                    nextFragment.setArguments(bundle2);
                    break;
                case R.id.nav_friends:
                    String[] StringArray = new String[3];
                    StringArray[0] = jsonMyObject;
                    StringArray[1] = jsonMyObject2;
                    StringArray[2] = jsonMyObject3;
                    Bundle bundle3 = new Bundle();
                    bundle3.putStringArray("ForFriends",StringArray);
                    nextFragment = new FriendsFragment();
                    nextFragment.setArguments(bundle3);
                    break;
            }
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }else{
            callPopupDialog("Are you sure to log out?");
            return false;
        }
    }*/

    private void callPopupDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage (message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ck = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        ck.edit().putBoolean("AUTO_ISCHECK",false).commit();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /*private class getFriendships extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String byStuNum = null;
            String byStuNum2 = new RestHttpClientForFriendship().GetFriendshipByStuNum2(params[0]);
            String JsonData;
            String Flag;
            if(byStuNum.isEmpty()){
                JsonData = byStuNum2;
                Flag = "byStuNum2";
            }else{
                JsonData = byStuNum;
                Flag = "byStuNum";
            }
            String[] BundlePass = new String[2];
            BundlePass[0] = JsonData;
            BundlePass[1] = Flag;
            Store1 = JsonData;
            Store2 = Flag;
            return BundlePass;
                            *//*Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            AllFriendship = gson.fromJson(JsonData, Friendship[].class);
                            return AllFriendship;*//*
        }
        @Override
        protected void onPostExecute(String[] Fs) {

        }
    }*/
}