package com.example.yang_deng.monashfriendfindertest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.yang_deng.monashfriendfindertest.DataBase.DBManagement;
import com.example.yang_deng.monashfriendfindertest.RestHttpService.GoogleAPISearch;
import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpClientForFriendship;
import com.example.yang_deng.monashfriendfindertest.Model.Friendship;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/**
 * Created by Yang-Deng on 2017/4/12.
 */
public class ViewFindFriendsFragment extends android.app.Fragment implements View.OnClickListener {
    private View vfragmentViewFriends;
    private StudentProfile[] StudentProfilesFromMainActivity;
    private StudentProfile studentprofile;
    private TextView[] textViewFindFriendList;
    private int size;
    private LinearLayout ViewFriendsLinearLayout;
    protected DBManagement dbManager;
    private Button MapButton;
    private String Findedguys;
    private ArrayList<String> FriendsStuNumberList = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vfragmentViewFriends = inflater.inflate(R.layout.fragment_view_find_friends, container, false);
        String[] JsonAndStudentProfileString = getArguments().getStringArray("JsonAndStudentProfiles");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        StudentProfilesFromMainActivity = gson.fromJson(JsonAndStudentProfileString[0], StudentProfile[].class);
 /*       for(StudentProfile sp:StudentProfilesFromMainActivity)
        {
            FriendsStuNumberList.add(sp.getStudentNumber().toString());
        }*/
        Findedguys = JsonAndStudentProfileString[0];
        Gson gson2 = new Gson();
        studentprofile = gson2.fromJson(JsonAndStudentProfileString[1], StudentProfile.class);
        size = StudentProfilesFromMainActivity.length;
        dbManager = new DBManagement(getActivity());
        ViewFriendsLinearLayout = (LinearLayout) vfragmentViewFriends.findViewById(R.id.ViewFriendsLinearLayout);
        MapButton = (Button) vfragmentViewFriends.findViewById(R.id.MapButton);
        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(getActivity(), MapActivity.class);
                activity.putExtra("Findedguys",Findedguys);
                startActivity(activity);
                /*new AsyncTask<List<String>,Void,HashMap<Double,Double>>(){
                    @Override
                    protected HashMap<Double,Double> doInBackground(List<String>... params) {
                        HashMap<Double,Double> returnValueHashMap = new HashMap<Double, Double>();
                        for(int i=0;i<params[0].size();i++) {
                            String Locations = new RestHttpClientForLocation().GetLocationsByStuNum(params[0].get(i));
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            StudentLocation[] studentLocations = gson.fromJson(Locations, StudentLocation[].class);
                            int k = 0;
                            Date date = new Date();
                            for (int j = 0; j < studentLocations.length; j++) {
                                if (studentLocations[j].getTimeStamp().compareTo(date) >= 0) {
                                    k = j;
                                }
                            }
                            StudentLocation LatestLocation = studentLocations[k];
                            Double[] returnValue = new Double[2];
                            returnValue[0] = Double.parseDouble(LatestLocation.getLatitude());
                            returnValue[1] = Double.parseDouble(LatestLocation.getLongitude());
                            returnValueHashMap.put(returnValue[0],returnValue[1]);
                        }
                        return returnValueHashMap;
                    }
                    @Override
                    protected void onPostExecute(HashMap<Double,Double> PassValue) {
                        Intent activity = new Intent(getActivity(), MapActivity.class);
                        activity.putExtra("Friends",Friends);
                        activity.putExtra("Locations",PassValue);
                        startActivity(activity);
                    }
                }.execute(FriendsStuNumberList);*/
            }
        });
        textViewFindFriendList = new TextView[size];
        for(int i=0;i<size;i++){
            textViewFindFriendList[i] = new TextView(getActivity());
            textViewFindFriendList[i].setText(
                    "\n\n\n\n" +
                            "First Name: " + StudentProfilesFromMainActivity[i].getFirstName() + "\n" +
                            "Last Name: " + StudentProfilesFromMainActivity[i].getLastName() + "\n" +
                            "Gender: " + StudentProfilesFromMainActivity[i].getGender() + "\n" +
                            "Date of Birth: " + StudentProfilesFromMainActivity[i].getDob().toString() + "\n" +
                            "Student Number: " + StudentProfilesFromMainActivity[i].getStudentNumber().toString() + "\n" +
                            "Email Address: " + StudentProfilesFromMainActivity[i].getMonashEmail() + "\n" +
                            "Address: " + StudentProfilesFromMainActivity[i].getAddress() +"\n" +
                            "Suburb: " + StudentProfilesFromMainActivity[i].getSuburb() + "\n" +
                            "Favourite Sport: " + StudentProfilesFromMainActivity[i].getFavouriteSport() + "\n" +
                            "Favourite Movie: " + StudentProfilesFromMainActivity[i].getFavouriteMovie() + "\n" +
                            "Favourite Unit Code: " + StudentProfilesFromMainActivity[i].getFavouriteUnitCode() + "\n" +
                            "Course: " + StudentProfilesFromMainActivity[i].getCourse() + "\n" +
                            "Study Mode: " + StudentProfilesFromMainActivity[i].getStudyMode() + "\n" +
                            "Native Language: " + StudentProfilesFromMainActivity[i].getNativeLanguage() + "\n" +
                            "Nationality: " + StudentProfilesFromMainActivity[i].getNationality() + "\n" +
                            "Current Job: " + StudentProfilesFromMainActivity[i].getCurrentJob() + "\n"
            );
            textViewFindFriendList[i].setId(i + size);
            Button addFriendButton = new Button(getActivity());
            addFriendButton.setText("Add Friend");
            final int j = i;
            addFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int max = readMaxNumber();
                    max = max + 1;
                    insertNumber(String.valueOf(max));
                    new AsyncTask<StudentProfile, Void, String>() {
                        @Override
                        protected String doInBackground(StudentProfile... params) {
                            Friendship friendshipPass = new Friendship();
                            if(params[0].getStudentNumber()>params[1].getStudentNumber()){
                                friendshipPass.setStudentNumber1(params[0]);
                                friendshipPass.setStudentNumber2(params[1]);
                            } else {
                                friendshipPass.setStudentNumber1(params[1]);
                                friendshipPass.setStudentNumber2(params[0]);
                            }
                            int max = readMaxNumber();
                            friendshipPass.setFriendshipId(max);
                            Calendar calendar = Calendar.getInstance();
                            java.util.Date MakeFriendTime = calendar.getTime();
                            friendshipPass.setFriendshipStartDate(MakeFriendTime);
                            friendshipPass.setFriendshipEndDate(null);
                            new RestHttpClientForFriendship().AddFriendship(friendshipPass);
                            return "Congratulation,You are Friends now!";
                        }
                        @Override
                        protected void onPostExecute(String OutPut) {
                            callPopupDialog(OutPut);
                        }
                    }.execute(StudentProfilesFromMainActivity[j],studentprofile);
                }
            });
            final String MovieName = StudentProfilesFromMainActivity[i].getFavouriteMovie();
            Button findMovieButton = new Button(getActivity());
            findMovieButton.setText("Find Movie");
            findMovieButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(MovieName);
                    new AsyncTask<String, Void, String[]>() {
                        String MetaJsondata;
                        String snippet = "";
                        String ImageURL = "";
                        String[] OutputPair = new String[2];
                        @Override
                        protected String[] doInBackground(String... params) {
                            MetaJsondata = (new GoogleAPISearch()).GetMovieJsonData(params[0]);
                            try {
                                JSONObject json = new JSONObject(MetaJsondata);
                                JSONArray jsonarray = json.getJSONArray("items");
                                if (jsonarray != null && jsonarray.length() > 0) {
                                    snippet = jsonarray.getJSONObject(0).getString("snippet");
                                    JSONObject pagemap = jsonarray.getJSONObject(0).getJSONObject("pagemap");
                                    JSONArray cse_image = pagemap.getJSONArray("cse_image");
                                    ImageURL = cse_image.getJSONObject(0).getString("src");
                                }
                            } catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                            OutputPair[0] = snippet;
                            OutputPair[1] = ImageURL;
                            return OutputPair;
                        }
                        @Override
                        protected void onPostExecute(String[] OutPut) {
                            callPopupDialogForMovie3(OutPut[0],OutPut[1]);
                        }
                    }.execute(MovieName);
                }
            });
            ViewFriendsLinearLayout.addView(textViewFindFriendList[i]);
            ViewFriendsLinearLayout.addView(addFriendButton);
            ViewFriendsLinearLayout.addView(findMovieButton);
        }
        return vfragmentViewFriends;
    }

    /**
     * When the submit button is clicked
     */
    @Override
    public void onClick(View v) {
/*        MainFragment nextFragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();*/
    }

    private void callPopupDialogForMovie(String snippet,String ImageURL) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage ("The Snippet of the Movie: \n" + snippet + "\n The Image of the Movie: \n" + ImageURL);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void callPopupDialogForMovie3(String snippet,String ImageURL) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.sample);
        dialog.setTitle("Movie");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.Movie_Snippet);
        text.setText(snippet);
        final ImageView image = (ImageView) dialog.findViewById(R.id.Movie_Image);
        //image.setImageResource(R.drawable.monashuni);
        new AsyncTask<String, Void, Drawable>() {

            @Override
            protected Drawable doInBackground(String... params) {
                try {
                    InputStream is = (InputStream) new URL(params[0]).getContent();
                    Drawable d = Drawable.createFromStream(is, "src name");
                    return d;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Drawable Output) {
                image.setImageDrawable(Output);
            }
        }.execute(ImageURL);

        Button dialogButton = (Button) dialog.findViewById(R.id.Movie_Button);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void callPopupDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage (message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public void insertNumber(String number){
        try {
            dbManager.open();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        //you can enter any data here
        dbManager.insertNumber(number);
        dbManager.close();
    }

    public int readMaxNumber(){
        try {
            dbManager.open();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        Cursor c = dbManager.getAllNumbers();
        String s="";
        int i=0;
        if (c.moveToFirst()) {
            do {
                if(Integer.parseInt(c.getString(0))>i)
                i = Integer.parseInt(c.getString(0));
            } while (c.moveToNext());
        }
        dbManager.close();
        return i;
    }
}