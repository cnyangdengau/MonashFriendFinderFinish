package com.example.yang_deng.monashfriendfindertest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpPostClient;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static java.security.spec.MGF1ParameterSpec.SHA1;

public class SubscribeActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private UserLoginTask mAuthTask_sub = null;

    // UI references.
    private AutoCompleteTextView Email_sub;
    private View mProgressView_sub;
    private View mLoginFormView_sub;
    private AutoCompleteTextView FirstName_sub;
    private AutoCompleteTextView LastName_sub;
    private AutoCompleteTextView StudentNumber_sub;
    private RadioButton Female;
    private RadioButton Male;
    private DatePicker datePicker;
    private EditText PasswordSub;
    private EditText PasswordReSub;
    private EditText Address;
    private EditText SuburbSub;
    private EditText FavouriteMovie;
    private EditText CurrentJob;
    private Spinner UnitCode;
    private Spinner Sport;
    private RadioButton HalfTime;
    private RadioButton FullTime;
    private Spinner NativeLanguage;
    private Spinner Nationality;
    private Spinner Course;
    private StudentProfile studentprofile;
    private String SHAHash;
    public static int NO_OPTIONS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subscribe);
        // Set up the login form.
        Email_sub = (AutoCompleteTextView) findViewById(R.id.Email_Sub);
        FirstName_sub = (AutoCompleteTextView) findViewById(R.id.First_Name_Sub);
        LastName_sub = (AutoCompleteTextView) findViewById(R.id.Last_Name_Sub);
        StudentNumber_sub = (AutoCompleteTextView) findViewById(R.id.Student_Number_Sub);
        Female = (RadioButton) findViewById(R.id.Female);
        Male = (RadioButton) findViewById(R.id.Male);
        datePicker = (DatePicker) findViewById(R.id.datePicker2);
        PasswordSub = (EditText) findViewById(R.id.password_sub);
        PasswordReSub = (EditText) findViewById(R.id.Password_Re_Sub);
        Address = (EditText) findViewById(R.id.Address_Sub);
        SuburbSub = (EditText) findViewById(R.id.Suburb_Sub);
        FavouriteMovie = (EditText) findViewById(R.id.Favourite_Movie_Sub);
        CurrentJob = (EditText) findViewById(R.id.CurrentJob_Sub);
        UnitCode = (Spinner) findViewById(R.id.spinner);
        Sport = (Spinner) findViewById(R.id.spinner2);
        HalfTime = (RadioButton) findViewById(R.id.Half_Time);
        FullTime = (RadioButton) findViewById(R.id.Full_Time);
        NativeLanguage = (Spinner) findViewById(R.id.spinner3);
        Nationality = (Spinner) findViewById(R.id.spinner4);
        Course = (Spinner) findViewById(R.id.spinner5);
        populateAutoComplete();



/*        mPasswordView_sub = (EditText) findViewById(R.id.password_sub);
        mPasswordView_sub.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/

        Button SubscribeButton = (Button) findViewById(R.id.sign_up_button_sub);
/*        SubscribeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentProfile postdata = PostSubscriptionData();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });*/

        SubscribeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        StudentProfile Studentpro = PostSubscriptionData();
                        RestHttpPostClient.createStudentProfile(Studentpro);
                        return "Student was added";
                    }
                    @Override
                    protected void onPostExecute(String response) {
                        callPopupDialog(response);
                    }
                }.execute();
            } });

        mLoginFormView_sub = findViewById(R.id.login_form_sub);
        mProgressView_sub = findViewById(R.id.login_progress_sub);
    }

    private void callPopupDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SubscribeActivity.this);
        builder1.setMessage (message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                });
/*

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
*/

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(Email_sub, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private StudentProfile PostSubscriptionData() {
        studentprofile = new StudentProfile();
        String Gender;
        if (Female.isChecked()) {
            Gender = "Female";
        } else {
            Gender = "Male";
        }


        String Studymode;
        if (HalfTime.isChecked()) {
            Studymode = "HalfTime";
        } else {
            Studymode = "FullTime";
        }

        studentprofile.setGender(Gender);
        studentprofile.setStudyMode(Studymode);
        studentprofile.setFirstName(FirstName_sub.getText().toString());
        studentprofile.setLastName(LastName_sub.getText().toString());
        studentprofile.setAddress(Address.getText().toString());
        studentprofile.setCourse(Course.getSelectedItem().toString());
        studentprofile.setNationality(Nationality.getSelectedItem().toString());
        studentprofile.setMonashEmail(Email_sub.getText().toString());
        studentprofile.setNativeLanguage(NativeLanguage.getSelectedItem().toString());
        studentprofile.setFavouriteMovie(FavouriteMovie.getText().toString());
        studentprofile.setFavouriteSport(Sport.getSelectedItem().toString());
        studentprofile.setFavouriteUnitCode(UnitCode.getSelectedItem().toString());
        studentprofile.setCurrentJob(CurrentJob.getText().toString());
        studentprofile.setSuburb(SuburbSub.getText().toString());
        studentprofile.setStudentNumber(Integer.parseInt(StudentNumber_sub.getText().toString()));
        java.util.Date Dob = getDateFromDatePicker(datePicker);
        studentprofile.setDob(Dob);
        Calendar calendar = Calendar.getInstance();
        java.util.Date SubscribeTime = calendar.getTime();
        studentprofile.setSubsciptionTime(SubscribeTime);
        studentprofile.setSubsciptionData(null);
        String Password = PasswordSub.getText().toString();
        String Hashed = md5(Password);
        studentprofile.setPassword(Hashed);
        return studentprofile;
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private void attemptLogin() {
        if (mAuthTask_sub != null) {
            return;
        }

        // Reset errors.
        Email_sub.setError(null);
        PasswordSub.setError(null);

        // Store values at the time of the login attempt.
        String email = Email_sub.getText().toString();
        String password = PasswordSub.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            PasswordSub.setError(getString(R.string.error_invalid_password));
            focusView = PasswordSub;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Email_sub.setError(getString(R.string.error_field_required));
            focusView = Email_sub;
            cancel = true;
        } else if (!isEmailValid(email)) {
            Email_sub.setError(getString(R.string.error_invalid_email));
            focusView = Email_sub;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask_sub = new UserLoginTask(email, password);
            mAuthTask_sub.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView_sub.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView_sub.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView_sub.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView_sub.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView_sub.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView_sub.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView_sub.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView_sub.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SubscribeActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        Email_sub.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
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

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask_sub = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                PasswordSub.setError(getString(R.string.error_incorrect_password));
                PasswordSub.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask_sub = null;
            showProgress(false);
        }
    }
}

