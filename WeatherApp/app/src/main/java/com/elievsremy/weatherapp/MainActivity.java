package com.elievsremy.weatherapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.elievsremy.weatherapp.Api.Api;
import com.elievsremy.weatherapp.AppInterface.GeoLocation;
import com.elievsremy.weatherapp.AppInterface.WeatherApi;
import com.elievsremy.weatherapp.Notification.TimeAlarm;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView city;
    private TextView temp;
    private TextView desc;
    public ImageView img_weather;
    public RelativeLayout weather_layout;

    GeoLocation gps;

    AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Alarm
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        ajouterAlarme(1, 2016, 4, 13, 04, 48);

        img_weather = (ImageView) findViewById(R.id.img_weather);
        weather_layout  = (RelativeLayout) findViewById(R.id.weather_layout);

        city = (TextView) findViewById(R.id.city);
        temp = (TextView) findViewById(R.id.temp);
        desc = (TextView) findViewById(R.id.desc);
        Typeface font = Typeface.createFromAsset(getAssets(), "JosefinSlab-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
        city.setTypeface(font2);
        temp.setTypeface(font);
        desc.setTypeface(font);
        gps = new GeoLocation(MainActivity.this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        if (gps.canGetLocation()){
            System.out.println("Latitude ===>" + latitude);
            System.out.println("Longitude ===>" + longitude);
        }else {
            gps.showGettingAlert();
        }


        WeatherApi.Factory.getInstance().getWeather(latitude,longitude).enqueue(new Callback<Api>() {
            @Override
            public void onResponse(Call<Api> call, Response<Api> response) {


                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("Name", String.valueOf(response.body().getName())).apply();
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("Temp", String.valueOf(response.body().getMain().getTemp())+"°C").apply();
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("Image", String.valueOf(response.body().getWeather().get(0).getIcon())).apply();
                System.out.println("Temperature ==>" + response.body().getMain().getTemp());
                city.setText(String.valueOf(response.body().getName()));
                temp.setText(String.valueOf(response.body().getMain().getTemp())+"°C");
                desc.setText(String.valueOf(response.body().getWeather().get(0).getDescription()));
                weather_background(response.body().getWeather().get(0).getIcon());
            }

            @Override
            public void onFailure(Call<Api> call, Throwable t) {
                Log.e("Failed",t.getMessage());
            }
        });
    }

    public void ajouterAlarme(int id, int year, int month, int day, int hour, int minute)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute);

        Intent intent = new Intent(this, TimeAlarm.class);
        PendingIntent operation = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
        //am.cancel(operation);
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 5000, operation);
    }

    public void weather_background(String icon){
        if (icon.equals("10d") || icon.equals("10n") ||
                icon.equals("09d") || icon.equals("09n")) {
            System.out.println("========================================");
            Picasso.with(this)
                    .load(R.drawable.rany)
                    .fit()
                    .centerCrop()
                    .into(img_weather);
            System.out.println("========================================");
        }
        else if(icon.equals("04d") || icon.equals("04n") ||
                icon.equals("03d") || icon.equals("03n") ||
                icon.equals("02d") || icon.equals("02n")){
            System.out.println("========================================");
            Picasso.with(this)
                    .load(R.drawable.cloudy)
                    .fit()
                    .centerCrop()
                    .into(img_weather);
            System.out.println("========================================");
        }
        else if(icon.equals("01d")){
            System.out.println("========================================");
            Picasso.with(this)
                    .load(R.drawable.sunny)
                    .fit()
                    .centerCrop()
                    .into(img_weather);
            System.out.println("========================================");
        }
        else{
            System.out.println("========================================");
            Picasso.with(this)
                    .load(R.drawable.moon)
                    .fit()
                    .centerCrop()
                    .into(img_weather);
            System.out.println("========================================");
        }
    }
}
