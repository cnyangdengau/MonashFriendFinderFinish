package com.example.yang_deng.monashfriendfindertest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpClientForFriendship;
import com.example.yang_deng.monashfriendfindertest.Model.Friendship;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Yang-Deng on 2017/4/12.
 */
public class FriendsFragment extends android.app.Fragment implements View.OnClickListener {
    private View vfragmentFriends;
    /*    private Button addFriendButton;
        private Button ImageButton;*/
    private Friendship[] AllFriendship;
    private StudentProfile studentprofile;
    private TextView textViewFindFriend1;
    private TextView textViewFindFriend2;
    private TextView[] textViewFindFriendList;
    private String StuNum;
    private String Flag;
    private int Size;
    private LinearLayout ViewFriendsLinearLayout;
    private StudentProfile sp;
    private Button FriendsMap_button;
    private ArrayList<StudentProfile> FriendsForPass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] StringArrayFromMain = getArguments().getStringArray("ForFriends");
        final Gson gson = new Gson();
        studentprofile = gson.fromJson(StringArrayFromMain[0], StudentProfile.class);
        StuNum = studentprofile.getStudentNumber().toString();
        Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        AllFriendship = gson2.fromJson(StringArrayFromMain[1], Friendship[].class);
        String Flag = StringArrayFromMain[2];
        vfragmentFriends = inflater.inflate(R.layout.fragment_friends, container, false);
        System.out.println(AllFriendship.length);
        Size = AllFriendship.length;
        ViewFriendsLinearLayout = (LinearLayout) vfragmentFriends.findViewById(R.id.FriendsLinearLayout);
        FriendsMap_button = (Button) vfragmentFriends.findViewById(R.id.button_FriendsMap);
        FriendsMap_button.setOnClickListener(this);
        FriendsForPass = new ArrayList<>();
        /*button_FriendsMap = (Button) vfragmentFriends.findViewById(R.id.button_FriendsMap);
        button_FriendsMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(getActivity(), MapActivityForFriends.class);
                Gson gson = new Gson();
                String stringStudentProfileJson=gson.toJson(FriendsForPass);
                activity.putExtra("CurrentFriends",stringStudentProfileJson);
                startActivity(activity);
            }
        });*/
        textViewFindFriendList = new TextView[Size];
        for(int i=0;i<Size;i++){
            final String ID = AllFriendship[i].getFriendshipId().toString();
            final int j = i;
            textViewFindFriendList[i] = new TextView(getActivity());
            if(Flag.equals("byStuNum")) {
                sp = AllFriendship[i].getStudentNumber1();
            }else{
                sp = AllFriendship[i].getStudentNumber2();
            }
            /*FriendsForPass.add(sp);*/
            textViewFindFriendList[i].setText(
                    "\n\n\n\nFirst Name: " + sp.getFirstName() + "\n" +
                            "Last Name: " + sp.getLastName() + "\n" +
                            "Gender: " + sp.getGender() + "\n" +
                            "Date of Birth: " + sp.getDob().toString() + "\n" +
                            "Student Number: " + sp.getStudentNumber().toString() + "\n" +
                            "Email Address: " + sp.getMonashEmail() + "\n" +
                            "Address: " + sp.getAddress() +"\n" +
                            "Suburb: " + sp.getSuburb() + "\n" +
                            "Favourite Sport: " + sp.getFavouriteSport() + "\n" +
                            "Favourite Movie: " + sp.getFavouriteMovie() + "\n" +
                            "Favourite Unit Code: " + sp.getFavouriteUnitCode() + "\n" +
                            "Course: " + sp.getCourse() + "\n" +
                            "Study Mode: " + sp.getStudyMode() + "\n" +
                            "Native Language: " + sp.getNativeLanguage() + "\n" +
                            "Nationality: " + sp.getNationality() + "\n" +
                            "Current Job: " + sp.getCurrentJob() + "\n"
            );
            textViewFindFriendList[i].setId(i + Size);
            FriendsForPass.add(sp);
            final Button DeleteFriendButton = new Button(getActivity());
            DeleteFriendButton.setText("Delete");
            DeleteFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPopupDialogForDeleteFriends("Are you Sure to delete the friends?",ID,j,DeleteFriendButton);
                }
            });
            ViewFriendsLinearLayout.addView(textViewFindFriendList[i]);
            ViewFriendsLinearLayout.addView(DeleteFriendButton);
        }
        return vfragmentFriends;
    }

    /**
     * When the submit button is clicked
     */
    @Override
    public void onClick(View v) {
        Intent activity = new Intent(getActivity(), MapActivityForFriends.class);
        Gson gson = new Gson();
        String stringStudentProfileJson=gson.toJson(FriendsForPass);
        activity.putExtra("CurrentFriends",stringStudentProfileJson);
        startActivity(activity);
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


    private void callPopupDialogForDeleteFriends(String message,final String ID,final int j,final Button button) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage (message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                String returnValue = new RestHttpClientForFriendship().DeleteFriendshipByID(params[0]);
                                return returnValue;
                            }
                            @Override
                            protected void onPostExecute(String returnValue) {
                                ViewFriendsLinearLayout.removeView(textViewFindFriendList[j]);
                                ViewFriendsLinearLayout.removeView(button);
                            }
                        }.execute(ID);
                        dialog.cancel();
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

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}