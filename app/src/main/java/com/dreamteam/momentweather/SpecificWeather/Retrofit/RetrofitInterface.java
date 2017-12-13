package com.dreamteam.momentweather.SpecificWeather.Retrofit;

import com.dreamteam.momentweather.SpecificWeather.CurrentWeather.CurrentWeatherModel.CurrentWeather;
import com.dreamteam.momentweather.SpecificWeather.DaysWeather.DaysModel.DaysModel;
import com.dreamteam.momentweather.SpecificWeather.HoursWeather.HoursModel.HoursModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("forecast/daily")
    Call<DaysModel> getDaysData(@Query("lat") double latitude, @Query("lon") double longitude,
                                @Query("units") String units, @Query("cnt") int cnt,
                                @Query("appid") String appId);
    @GET("forecast")
    Call<HoursModel> getHoursData(@Query("lat") double latitude, @Query("lon") double longitude,
                                  @Query("units") String units, @Query("appid") String appId);
    @GET("weather")
    Call<CurrentWeather> getCurrentWeatherData(@Query("lat") double latitude, @Query("lon") double longitude,
                                               @Query("units") String units, @Query("appid") String appId);
}
