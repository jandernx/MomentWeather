package com.dreamteam.momentweather.SpecificWeather.CurrentWeather;
import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamteam.momentweather.SpecificWeather.CurrentWeather.CurrentWeatherModel.CurrentWeather;
import com.dreamteam.momentweather.R;
import com.dreamteam.momentweather.SpecificWeather.SpecificWeatherFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentWeatherFragment extends Fragment implements SpecificWeatherFragment.DataIsReadyListener{

    @BindView(R.id.date_weather) TextView dateWeather;
    @BindView(R.id.sunrise_time_weather) TextView sunriseTimeWeather;
    @BindView(R.id.temperature_weather) TextView temperatureWeather;
    @BindView(R.id.units_weather) TextView unitsWeather;
    @BindView(R.id.sunset_time_weather) TextView sunsetTimeWeather;
    @BindView(R.id.icon_weather_weather)ImageView iconWeather;
    @BindView(R.id.status_weather) TextView statusWeather;
    @BindView(R.id.wind_weather) TextView windWeather;

    final String[] month = {
            "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November",
            "December"
    };

    private String[] weatherIcons = {
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2FCloudy.png?alt=media&token=25818fd2-1558-4911-b424-a5038e70986a", //Cloudy
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2FDrizzle.png?alt=media&token=30731406-0358-454d-8ec9-a3532a2ad0ec", //Drizzle
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2FHaze.png?alt=media&token=b89620b3-c893-4554-b7f0-003eb041d256", //Haze
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2FMostly%20Cloudy.png?alt=media&token=4c0096bf-f592-4b56-9532-8606cfab94f2", //Mostly cloudy
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2FSlight%20Drizzle.png?alt=media&token=8c3ba9e9-9958-47b7-8e1c-ff10d62273fe", //Slight Drizzle
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2FSnow.png?alt=media&token=0d51b002-28a7-43fe-9fa2-639ff04c1484", //Snow
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2FSunny.png?alt=media&token=aa89a2e8-7a84-4297-a319-092cd1c89b01", //Sunny
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2FThunderstorms.png?alt=media&token=c4048843-30e5-47c8-92b2-35ec7e13b02b" //Thunderstorm
    };


    public static CurrentWeatherFragment newInstance() {

        Bundle args = new Bundle();

        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setData(Object object) {
        CurrentWeather currentWeather = (CurrentWeather)object;
        dateWeather.setText(getDate(currentWeather.getDt()));
        sunriseTimeWeather.setText(getSunSetRise(currentWeather.getSys().getSunrise()));
        sunsetTimeWeather.setText(getSunSetRise(currentWeather.getSys().getSunset()));
        temperatureWeather.setText(Integer.toString(currentWeather.getMain().getTemp().intValue()));
        unitsWeather.setText("\u00b0" + "C");
        Glide.with(getActivity()).
                load(iconChooser(currentWeather.getWeather().get(0).getDescription())).
                into(iconWeather);
        statusWeather.setText(currentWeather.getWeather().get(0).getDescription());
        windWeather.setText(windTypeChooser(currentWeather.getWind().getSpeed().intValue()) +
                    ", " + currentWeather.getWind().getSpeed().intValue() + " mps");

    }

    private String getDate(long UTS){
        Date date = new Date(UTS * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM, HH:mm", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        String dateStr = simpleDateFormat.format(date);


        String currentTime;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
        simpleDateFormat1.setTimeZone(calendar.getTimeZone());
        currentTime = simpleDateFormat1.format(new Date());

        String s = dateStr.substring(0, dateStr.length() - 5) + currentTime;

        return s;
    }

    private String getSunSetRise(long UTS){
        Date date = new Date(UTS * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        simpleDateFormat.setTimeZone(calendar.getTimeZone());

        return simpleDateFormat.format(date);
    }

    private String iconChooser(String status){
        String currentIcon;
        if(status.equals("clear sky")){
            currentIcon = weatherIcons[6];
        }else if(status.equals("few clouds")){
            currentIcon = weatherIcons[3];
        }else if(status.equals("scattered clouds")){
            currentIcon = weatherIcons[0];
        }else if(status.equals("broken clouds")){
            currentIcon = weatherIcons[3];
        }else if(status.equals("shower rain")){
            currentIcon = weatherIcons[4];
        }else if(status.equals("moderate rain")){
            currentIcon = weatherIcons[4];
        }else if(status.equals("light rain")){
            currentIcon = weatherIcons[1];
        }else if(status.equals("rain")){
            currentIcon = weatherIcons[1];
        }else if(status.equals("thunderstorm")){
            currentIcon = weatherIcons[7];
        }else if(status.equals("snow")){
            currentIcon = weatherIcons[5];
        }else if(status.equals("mist")){
            currentIcon = weatherIcons[2];
        }else{
            currentIcon = weatherIcons[6];
        }
        return currentIcon;
    }

    private String windTypeChooser(int wind){
        String windType;
        if(wind == 0) windType = "Calm";
        else if(wind == 1) windType = "Light Air";
        else if(wind == 2) windType = "Light Breeze";
        else if(wind == 3) windType = "Gentle Breeze";
        else if(wind == 4) windType = "Moderate Breeze";
        else if(wind == 5) windType = "Fresh Breeze";
        else if(wind == 6) windType = "Strong Gale";
        else  windType = "Whole gale";
        return windType;
    }



}