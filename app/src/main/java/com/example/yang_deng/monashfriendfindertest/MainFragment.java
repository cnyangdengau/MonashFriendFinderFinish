package com.example.yang_deng.monashfriendfindertest;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yang_deng.monashfriendfindertest.RestHttpService.WeatherHttpClient;
import com.example.yang_deng.monashfriendfindertest.Model.Weather;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.widget.Toast;


public class MainFragment extends android.app.Fragment {
    View vMain;
    GPSTracker gps;
    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;
    private TextView hum;
    private TextView timeDateLab;
    private LocationManager locManager;
    Calendar calendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        double latitude;
        double longitude;
        String coordinate= null;
        String StuName = getArguments().getString("UserName");
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        cityText = (TextView) vMain.findViewById(R.id.cityText);
        condDescr = (TextView) vMain.findViewById(R.id.condDescr);
        temp = (TextView) vMain.findViewById(R.id.temp);
        hum = (TextView) vMain.findViewById(R.id.hum);
        press = (TextView) vMain.findViewById(R.id.press);
        windSpeed = (TextView) vMain.findViewById(R.id.windSpeed);
        windDeg = (TextView) vMain.findViewById(R.id.windDeg);
        timeDateLab = (TextView) vMain.findViewById(R.id.timeDatelab);



        // create class object
        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            coordinate = Double.toString(latitude) + " " + Double.toString(longitude);
            // \n is for new line
            Toast.makeText(getActivity(), "Welcome, " + StuName + "\n Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{coordinate});
        return vMain;
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);
                // Let's retrieve the icon
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("" + weather.wind.getDeg() + "°");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String timeDate = df.format(calendar.getTime());
            timeDateLab.setText("" + timeDate);
        }
    }
}
