package com.example.yang_deng.monashfriendfindertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.yang_deng.monashfriendfindertest.Model.StudentProfile;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    String AllStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            AllStudents = extras.getString("ForReports");
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        StudentProfile[] ALLStudentProfiles = gson.fromJson(AllStudents, StudentProfile[].class);

        setContentView(R.layout.fragment_reports);

        PieChart pieChart = (PieChart) findViewById(R.id.Piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        final String[] xData = { "FIT5046", "FIT5148", "FIT5047", "FIT5211", "FIT5083" };

        // set a chart value selected listener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(getApplicationContext(),
                        xData[e.getXIndex()] + " = " + e.getVal(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        Integer FIT5046 = 0;
        Integer FIT5148 = 0;
        Integer FIT5047 = 0;
        Integer FIT5211 = 0;
        Integer FIT5083 = 0;

        for(StudentProfile sp: ALLStudentProfiles){
            if(sp.getFavouriteUnitCode().equals("FIT5046"))
            {
                FIT5046 += 1;
            }else if(sp.getFavouriteUnitCode().equals("FIT5148")){
                FIT5148 += 1;
            }else if(sp.getFavouriteUnitCode().equals("FIT5047")){
                FIT5047 += 1;
            }else if(sp.getFavouriteUnitCode().equals("FIT5211")){
                FIT5211 += 1;
            }else{
                FIT5083 += 1;
            }
        }

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(FIT5046, 0));
        entries.add(new Entry(FIT5148, 1));
        entries.add(new Entry(FIT5047, 2));
        entries.add(new Entry(FIT5211, 3));
        entries.add(new Entry(FIT5083, 4));

        PieDataSet dataset = new PieDataSet(entries, "# of Count");


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("FIT5046");
        labels.add("FIT5148");
        labels.add("FIT5047");
        labels.add("FIT5211");
        labels.add("FIT5083");


        PieData data = new PieData(labels, dataset);
        data.setValueTextSize(14f);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        pieChart.setDescription("Favourite Unit Code Share");
        pieChart.setData(data);
        data.setValueFormatter(new PercentFormatter());

        pieChart.animateY(5000);

        pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image
    }
}
