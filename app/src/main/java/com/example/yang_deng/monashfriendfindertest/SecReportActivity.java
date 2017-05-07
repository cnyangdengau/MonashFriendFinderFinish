package com.example.yang_deng.monashfriendfindertest;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.yang_deng.monashfriendfindertest.Model.StudentLocation;
import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.example.yang_deng.monashfriendfindertest.RestHttpService.RestHttpClientForLocation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SecReportActivity extends AppCompatActivity {

    Button BarchartButton;
    EditText FromDate;
    EditText ToDate;
    BarChart barChart;
    String StudentProfile;
    StudentProfile studentProfile;
    Date dateFrom;
    Date dateTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_secreports);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            StudentProfile = extras.getString("StudentProfile");
        }
        studentProfile = new Gson().fromJson(StudentProfile, StudentProfile.class);

        barChart = (BarChart) findViewById(R.id.chart);
        BarchartButton =(Button) findViewById(R.id.BarchartButton);
        FromDate = (EditText) findViewById(R.id.FromDate);
        ToDate = (EditText) findViewById(R.id.ToDate);

        BarchartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FromDateString = FromDate.getText().toString().replace(" ","");
                String ToDateString = ToDate.getText().toString().replace(" ","");
                SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    dateFrom = DF.parse(FromDateString);
                    dateTo = DF.parse(ToDateString);
                }catch (Exception e){
                    e.printStackTrace();
                }


                new AsyncTask<StudentProfile, Void, ArrayList<String>>() {
                    @Override
                    protected ArrayList<String> doInBackground(StudentProfile... params) {
                        String StudentNumber = params[0].getStudentNumber().toString();
                        String Locations = new RestHttpClientForLocation().GetLocationsByStuNum(StudentNumber);
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                        StudentLocation[] studentLocations = gson.fromJson(Locations, StudentLocation[].class);
                        ArrayList<StudentLocation> FilteredLocations = DateFilter(studentLocations);
                        ArrayList<String> LocationNames = GetLocationNames(FilteredLocations);
                        return LocationNames;
                    }
                    @Override
                    protected void onPostExecute(ArrayList<String> LocationNames) {
                        Map<String, Integer> counts = new HashMap<String, Integer>();
                        for (String word : LocationNames) {
                            Integer count = counts.get(word);
                            if (count == null) {
                                counts.put(word, 1);
                            } else {
                                counts.put(word, count + 1);
                            }
                        }

                        List<String> KeyList = new ArrayList<String>(counts.keySet());
                        ArrayList<BarEntry> entries = new ArrayList<>();
                        ArrayList<String> labels = new ArrayList<String>();

                        for(int i=0;i<KeyList.size();i++){
                            entries.add(new BarEntry(counts.get(KeyList.get(i)),i));
                            labels.add(KeyList.get(i));
                        }
                        BarDataSet dataset = new BarDataSet(entries, "# of Times");
                        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        BarData data = new BarData(labels, dataset);
                        barChart.setData(data);
                        barChart.animateY(5000);
                    }
                }.execute(studentProfile);
            }
        });

        // HorizontalBarChart barChart= (HorizontalBarChart) findViewById(R.id.chart);

       /* ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");*/

        /* for create Grouped Bar chart
        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        group1.add(new BarEntry(12f, 3));
        group1.add(new BarEntry(18f, 4));
        group1.add(new BarEntry(9f, 5));

        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));
        group2.add(new BarEntry(15f, 4));
        group2.add(new BarEntry(10f, 5));

        BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<BarDataSet> dataset = new ArrayList<>();
        dataset.add(barDataSet1);
        dataset.add(barDataSet2);
        */

      /*  BarData data = new BarData(labels, dataset);
        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        barChart.setData(data);
        barChart.animateY(5000);*/

    }

    public ArrayList<String> GetLocationNames(ArrayList<StudentLocation> SList){
        ArrayList<String> LocationNames = new ArrayList<>();
        for(StudentLocation sl:SList) {
            Double latitude = Double.parseDouble(sl.getLatitude());
            Double longitude = Double.parseDouble(sl.getLongitude());
            Geocoder geocoder;
            List<Address> addresses;
            String city = null;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                city = addresses.get(0).getLocality();
                /*
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postalCode = addresses.get(0).getPostalCode();
                knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            LocationNames.add(city);
        }
        return LocationNames;
    }

    public ArrayList<StudentLocation> DateFilter(StudentLocation[] SL){
        ArrayList<StudentLocation> SList = new ArrayList<>();
        for(StudentLocation sl:SL){
            Date slDate = sl.getTimeStamp();
            if((slDate.compareTo(dateFrom)>0)&&(slDate.compareTo(dateTo)<0)){
                SList.add(sl);
            }
        }
        return SList;
    }

}
