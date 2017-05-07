package com.example.yang_deng.monashfriendfindertest;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpPutClient;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;


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
public class UpdateProfileFragment extends android.app.Fragment implements View.OnClickListener {
    private View vfragmentUpdate;
/*    private EditText etUnitCode, etUnitName;
    private TextView tvFeedback;
    private RadioButton rbSem1, rbSem2, rbSummer;
    private Button bSubmit;
    private Spinner sYear;*/

    // UI references.
    private AutoCompleteTextView Email_update;
    private View mProgressView_update;
    private View mLoginFormView_update;
    private AutoCompleteTextView FirstName_update;
    private AutoCompleteTextView LastName_update;
    //private AutoCompleteTextView StudentNumber_update;
    private RadioButton Female_update;
    private RadioButton Male_update;
    private DatePicker datePicker_update;
    private EditText Password_update;
    private EditText Address_update;
    private EditText Suburb_update;
    private EditText FavouriteMovie_update;
    private EditText CurrentJob_update;
    private Spinner UnitCode_update;
    private Spinner Sport_update;
    private RadioButton HalfTime_update;
    private RadioButton FullTime_update;
    private Spinner NativeLanguage_update;
    private Spinner Nationality_update;
    private Spinner Course_update;
    private StudentProfile studentprofile;
    private Button bSubmit;
    private StudentProfile StudentProfileFromMainActivity;
    private StudentProfile newStudentProfile;
    private StudentProfile sp;
    private int id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vfragmentUpdate = inflater.inflate(R.layout.fragment_update_profile, container, false);
        String StudentProfile = getArguments().getString("StudentProfile");
        StudentProfileFromMainActivity = new Gson().fromJson(StudentProfile, StudentProfile.class);
        Email_update = (AutoCompleteTextView) vfragmentUpdate.findViewById(R.id.Email_update);
        Email_update.setText(StudentProfileFromMainActivity.getMonashEmail());
        FirstName_update = (AutoCompleteTextView) vfragmentUpdate.findViewById(R.id.First_Name_update);
        FirstName_update.setText(StudentProfileFromMainActivity.getFirstName());
        LastName_update = (AutoCompleteTextView) vfragmentUpdate.findViewById(R.id.Last_Name_update);
        LastName_update.setText(StudentProfileFromMainActivity.getLastName());
        Female_update = (RadioButton) vfragmentUpdate.findViewById(R.id.Female_update);
        Male_update = (RadioButton) vfragmentUpdate.findViewById(R.id.Male_update);
        if(StudentProfileFromMainActivity.getGender().equals("Male")){
            Male_update.setChecked(true);
            Female_update.setChecked(false);
        }
        else{
            Male_update.setChecked(false);
            Female_update.setChecked(true);
        }

        datePicker_update = (DatePicker) vfragmentUpdate.findViewById(R.id.datePicker2_update);
        Date dob = StudentProfileFromMainActivity.getDob();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dob);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePicker_update.updateDate(year,month,day);

        Password_update = (EditText) vfragmentUpdate.findViewById(R.id.password_update);
        Password_update.setText(StudentProfileFromMainActivity.getPassword());

        Address_update = (EditText) vfragmentUpdate.findViewById(R.id.Address_update);
        Address_update.setText(StudentProfileFromMainActivity.getAddress());

        Suburb_update = (EditText) vfragmentUpdate.findViewById(R.id.Suburb_update);
        Suburb_update.setText(StudentProfileFromMainActivity.getSuburb());

        FavouriteMovie_update = (EditText) vfragmentUpdate.findViewById(R.id.Favourite_Movie_update);
        FavouriteMovie_update.setText(StudentProfileFromMainActivity.getFavouriteMovie());

        CurrentJob_update = (EditText) vfragmentUpdate.findViewById(R.id.CurrentJob_update);
        CurrentJob_update.setText(StudentProfileFromMainActivity.getCurrentJob());

        UnitCode_update = (Spinner) vfragmentUpdate.findViewById(R.id.spinner_update);
        String compareValue = StudentProfileFromMainActivity.getFavouriteUnitCode();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Unit_Code_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UnitCode_update.setAdapter(adapter);
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            UnitCode_update.setSelection(spinnerPosition);
        }

        Sport_update = (Spinner) vfragmentUpdate.findViewById(R.id.spinner2_update);
        String compareValue2 = StudentProfileFromMainActivity.getFavouriteSport();
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.Favourite_Sport_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sport_update.setAdapter(adapter2);
        if (!compareValue2.equals(null)) {
            int spinnerPosition = adapter2.getPosition(compareValue2);
            Sport_update.setSelection(spinnerPosition);
        }

        HalfTime_update = (RadioButton) vfragmentUpdate.findViewById(R.id.Half_Time_update);
        FullTime_update = (RadioButton) vfragmentUpdate.findViewById(R.id.Full_Time_update);
        if(StudentProfileFromMainActivity.getStudyMode().equals("Half-time")){
            HalfTime_update.setChecked(true);
            FullTime_update.setChecked(false);
        }
        else{
            HalfTime_update.setChecked(false);
            FullTime_update.setChecked(true);
        }

        NativeLanguage_update = (Spinner) vfragmentUpdate.findViewById(R.id.spinner3_update);
        String compareValue3 = StudentProfileFromMainActivity.getNativeLanguage();
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.Native_Language_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NativeLanguage_update.setAdapter(adapter3);
        if (!compareValue3.equals(null)) {
            int spinnerPosition = adapter3.getPosition(compareValue3);
            NativeLanguage_update.setSelection(spinnerPosition);
        }

        Nationality_update = (Spinner) vfragmentUpdate.findViewById(R.id.spinner4_update);
        String compareValue4 = StudentProfileFromMainActivity.getNationality();
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getActivity(), R.array.Nationality_array, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Nationality_update.setAdapter(adapter4);
        if (!compareValue4.equals(null)) {
            int spinnerPosition = adapter4.getPosition(compareValue4);
            Nationality_update.setSelection(spinnerPosition);
        }


        Course_update = (Spinner) vfragmentUpdate.findViewById(R.id.spinner5_update);
        String compareValue5 = StudentProfileFromMainActivity.getCourse();
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getActivity(), R.array.Course_array, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Course_update.setAdapter(adapter5);
        if (!compareValue5.equals(null)) {
            int spinnerPosition = adapter5.getPosition(compareValue5);
            Course_update.setSelection(spinnerPosition);
        }

        id = StudentProfileFromMainActivity.getStudentNumber();
        bSubmit = (Button) vfragmentUpdate.findViewById(R.id.Update_button);
        bSubmit.setOnClickListener(this);
        return vfragmentUpdate;
    }

    /**
     * When the submit button is clicked
     */
    @Override
    public void onClick(View v) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                sp = new StudentProfile();
                sp = CreateNewStudentProfile();
                RestHttpPutClient.UpdateStudentProfile(sp);
                return "Your already update your profile successfully!";
            }
            @Override
            protected void onPostExecute(String response) {
                callPopupDialog(response);
            }
        }.execute();
    }

    private void callPopupDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage (message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

    private StudentProfile CreateNewStudentProfile() {
        newStudentProfile = new StudentProfile();
        String Gender;
        if (Female_update.isChecked()) {
            Gender = "Female";
        } else {
            Gender = "Male";
        }

        String Studymode;
        if (HalfTime_update.isChecked()) {
            Studymode = "HalfTime";
        } else {
            Studymode = "FullTime";
        }
        newStudentProfile.setStudentNumber(id);
        newStudentProfile.setGender(Gender);
        newStudentProfile.setStudyMode(Studymode);
        newStudentProfile.setFirstName(FirstName_update.getText().toString());
        newStudentProfile.setLastName(LastName_update.getText().toString());
        newStudentProfile.setAddress(Address_update.getText().toString());
        newStudentProfile.setCourse(Course_update.getSelectedItem().toString());
        newStudentProfile.setNationality(Nationality_update.getSelectedItem().toString());
        newStudentProfile.setMonashEmail(Email_update.getText().toString());
        newStudentProfile.setNativeLanguage(NativeLanguage_update.getSelectedItem().toString());
        newStudentProfile.setFavouriteMovie(FavouriteMovie_update.getText().toString());
        newStudentProfile.setFavouriteSport(Sport_update.getSelectedItem().toString());
        newStudentProfile.setFavouriteUnitCode(UnitCode_update.getSelectedItem().toString());
        newStudentProfile.setCurrentJob(CurrentJob_update.getText().toString());
        newStudentProfile.setSuburb(Suburb_update.getText().toString());
        java.util.Date Dob_update = getDateFromDatePicker(datePicker_update);
        newStudentProfile.setDob(Dob_update);
        Calendar calendar_update = Calendar.getInstance();
        java.util.Date SubscribeTime_update = calendar_update.getTime();
        newStudentProfile.setSubsciptionTime(StudentProfileFromMainActivity.getSubsciptionTime());
        newStudentProfile.setSubsciptionData(null);
        newStudentProfile.setPassword(md5(Password_update.getText().toString()));
        return newStudentProfile;
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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