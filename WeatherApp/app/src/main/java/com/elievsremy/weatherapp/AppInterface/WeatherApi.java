package com.elievsremy.weatherapp.AppInterface;

import com.elievsremy.weatherapp.Api.Api;
import com.elievsremy.weatherapp.Api.Weather;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ElievsRemy on 09/05/2016.
 */
public interface WeatherApi {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @GET("weather?&units=metric&lang=fr&appid=1f02930c1baa09598bd58bd4b2c8bcee")
    Call<Api> getWeather(
            @Query("lat") double latittude,
            @Query("lon") double longitude);

    class Factory{

        private static WeatherApi service;

        public static WeatherApi getInstance() {
            if(service == null) {

                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                WeatherApi service = retrofit.create(WeatherApi.class);
                return service;
            }else return service;
        }

    }
}
