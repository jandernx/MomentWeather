package com.dreamteam.momentweather.SpecificWeather.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roman on 25.06.17.
 */

public class RetrofitModule {

    private static Retrofit retrofit;
    private static RetrofitInterface retrofitInterface;

    public static RetrofitInterface buildApi(){
        retrofit = new Retrofit.Builder().
                baseUrl("http://api.openweathermap.org/data/2.5/").
                addConverterFactory(GsonConverterFactory.create()).
                build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        return retrofitInterface;
    }
}
