package com.example.yang_deng.monashfriendfindertest;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yang_deng.monashfriendfindertest.MultiSpinner.MultiSpinner;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.example.yang_deng.monashfriendfindertest.RestHttpService.SearchRestHttpClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangDeng on 28/4/17.
 */

public class SearchFragment extends android.app.Fragment implements MultiSpinner.OnMultipleItemsSelectedListener, View.OnClickListener {
    private View vfragmentSearch;
    private StudentProfile StudentProfileFromMainActivity;
    private MultiSpinner multiSelectionSpinner;
    private Button searchButton;
    private ArrayList<String> parameters = new ArrayList<>();
    private String StudentProfile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vfragmentSearch = inflater.inflate(R.layout.fragment_search, container, false);
        StudentProfile = getArguments().getString("StudentProfile");
        StudentProfileFromMainActivity = new Gson().fromJson(StudentProfile, com.example.yang_deng.monashfriendfindertest.Model.StudentProfile.class);
        String[] array = {"course", "currentJob", "favouriteMovie", "favouriteSport", "favouriteUnitCode", "gender", "nativeLanguage", "studyMode"};
        multiSelectionSpinner = (MultiSpinner) vfragmentSearch.findViewById(R.id.mySpinner);
        multiSelectionSpinner.setItems(array);
        parameters.add("course");
/*
        multiSelectionSpinner.setSelection(new int[]{0, 1});
*/
        multiSelectionSpinner.setListener(this);
        searchButton = (Button) vfragmentSearch.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        return vfragmentSearch;
    }

    /**
     * When the submit button is clicked
     */

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        parameters = new ArrayList<>(strings);
        Toast.makeText(getActivity(), "You already selected:   " + strings.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String returnJsonValue;
                int studentnumber = StudentProfileFromMainActivity.getStudentNumber();
                returnJsonValue = SearchRestHttpClient.SearchFriend(studentnumber,parameters);
                return returnJsonValue;
            }
            @Override
            protected void onPostExecute(String response) {
                if(response.length()<3) {
                    callPopupDialogforNull("There is no matching friend for you, please reselect attributes.");
                }else{
                    callPopupDialog("Find some friends for you",response);
                }
            }
        }.execute();
    }

    private void callPopupDialogforNull(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage (message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void callPopupDialog(String message,String returnJsonValue) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage (message);
        builder1.setCancelable(true);
        final String passJsonValue = returnJsonValue;
        builder1.setPositiveButton(
                "View",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Bundle bundle2 = new Bundle();
                        String[] JsonAndSP = new String[2];
                        JsonAndSP[0] = passJsonValue;
                        JsonAndSP[1] = StudentProfile;
                        bundle2.putStringArray("JsonAndStudentProfiles",JsonAndSP);
                        ViewFindFriendsFragment nextFragment = new ViewFindFriendsFragment();
                        nextFragment.setArguments(bundle2);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}