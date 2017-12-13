package com.dreamteam.momentweather.SearchWeather;


public interface ChosenCityListener {
    void onCityChosen(String cityName, double latitude, double longitude);
}
