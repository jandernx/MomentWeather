package com.dreamteam.momentweather.SearchWeather;

/**
 * Created by roman on 06.07.17.
 */

public interface ChosenCityListener {
    void onCityChosen(String cityName, double latitude, double longitude);
}
