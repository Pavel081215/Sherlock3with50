package com.startandroid.android_beginners_weather_forecast_app;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final String TAB  = "MyLOG";

    TextView dateTxtDisplay;
    TextView cityTxtDisplay;
    ImageView imageWeather;
    TextView weatherTextView;
    TextView tempratureTxtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //    ImageView mImageView;
        tempratureTxtDisplay = (TextView) findViewById(R.id.TempatureTextView);
        cityTxtDisplay = (TextView)findViewById(R.id.CityTextView);
        weatherTextView = (TextView) findViewById(R.id.WeatherTextView4) ;
        tempratureTxtDisplay.setText("4400");
        dateTxtDisplay = (TextView) findViewById(R.id.DateTextView);
        imageWeather = (ImageView) findViewById(R.id.imageView);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Brovary,ua&appid=d52b5cbca43a92265e2cfc70be19e128&units=metric";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // mTxtDisplay.setText("Response: " + response.toString());

                try {
                    JSONObject mainJSONObject = response.getJSONObject("main");
                    JSONArray weatherJSONArray = response.getJSONArray("weather");
                    JSONObject weatherJSONObject = weatherJSONArray.getJSONObject(0);

                    String weather = weatherJSONObject.getString("description");
                    weatherTextView.setText(weather);
                    String temp = Integer.toString((int) Math.round(mainJSONObject.getDouble("temp")));
                    tempratureTxtDisplay.setText(temp);
                   String city = response.getString("name");
                    cityTxtDisplay.setText(city);

                    dateTxtDisplay.setText(getCurrentDate());


                    int iconResourceId = getResources().getIdentifier("icon_" + weather.replace(" ",""), "drawable", getPackageName());


                    Log.i(TAB,"getPackageName())   -  " + getPackageName());
                    Log.i(TAB,"weather.replace - " + weather.replace(" ", ""));
                    Log.i(TAB,"int iconResourceId = getResources()  - " + iconResourceId);

                    imageWeather.setImageResource(iconResourceId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });

// Access the RequestQueue through your singleton class.
        //  MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }
    private  String getCurrentDate ()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE,MMM dd");
        return  dateFormat.format(calendar.getTime());

    }

}
