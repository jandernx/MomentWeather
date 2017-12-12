package com.dreamteam.momentweather.SpecificWeather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamteam.momentweather.R;
import com.dreamteam.momentweather.SpecificWeather.DaysWeather.DaysModel.DaysList;
import com.dreamteam.momentweather.SpecificWeather.HoursWeather.HoursModel.HoursList;
import com.dreamteam.momentweather.SpecificWeather.HoursWeather.HoursModel.HoursModel;
import com.dreamteam.momentweather.SpecificWeather.HoursWeather.HoursModel.Main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roman on 24.06.17.
 */

public class WeatherElementsAdapter extends RecyclerView.Adapter {

    final int MODE_DATE = 0;
    final int MODE_STATUS = 2;
    final int MODE_TEMP = 3;
    final int MODE_WIND = 4;
    final int MODE_HUMIDITY = 5;
    final int MODE_PRESSURE = 6;


    private Context context;
    private List<Object> elements;
    private int MODE_FEATURE = 0;
    private int itemCount;

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

    private String[] humidityIcons = {
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2Fhumidity%2Fhum0.png?alt=media&token=929dd1f6-96e6-479c-9b15-ac36f9bcab40",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2Fhumidity%2Fhum25.png?alt=media&token=87a6f77d-b909-4794-91f2-0bc7179937cc",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2Fhumidity%2Fhum50.png?alt=media&token=743d551a-7790-4457-8ef6-3c2555c9b906",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2Fhumidity%2Fhum75.png?alt=media&token=45ded0d7-6435-4e39-b471-b71855e4d1c0",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2Fhumidity%2Fhum100.png?alt=media&token=fb7e101b-6c50-4a36-b5dc-9c7b84fbe496"
    };

    private String[] windIcons = {
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2FFlugerUp.png?alt=media&token=fce04785-4f1b-4827-8eaa-672a71d50cf4",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2FFlugerBottom1.png?alt=media&token=2cf18d17-b367-430f-b9b2-30096c958b76",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2FFlugerRight.png?alt=media&token=2cef5d7b-d6bf-433c-a52e-066710c78c9a",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fwind%2FFlugerLeft.png?alt=media&token=25518858-2d96-452b-bd64-b1092c10d241"
    };



    public WeatherElementsAdapter(Context context, Object elements, int MODE_FEATURE){
        this.context = context;
        this.elements = (List<Object>)elements;
        this.MODE_FEATURE = MODE_FEATURE;
        this.itemCount = this.elements.size();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);
        viewHolder = new WeatherViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(elements.get(position) instanceof HoursList) {
                switch (MODE_FEATURE) {
                    case MODE_DATE:
                        ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                        ((WeatherViewHolder) holder).textItemWeather.
                                setText((((HoursList) elements.get(position)).getDtTxt()).
                                        toString().substring(11, 16));
                        break;
                    case MODE_STATUS:
                        String status = ((HoursList) elements.get(position)).
                                getWeather().get(0).getDescription().toLowerCase();
                        ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                        ((WeatherViewHolder)holder).imageItemWeather.setVisibility(View.VISIBLE);
                        ((WeatherViewHolder) holder).textItemWeather.
                                setText((((HoursList) elements.get(position)).getWeather().
                                        get(0).getDescription()).toString());
                        Glide.with(((WeatherViewHolder)holder).context).
                                load(iconChooser(status)).
                                into(((WeatherViewHolder)holder).imageItemWeather);
                        break;
                    case MODE_TEMP:
                        ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                        int temp = (int) Math.round((((HoursList) elements.get(position)).getMain().
                                getTemp()));
                        ((WeatherViewHolder) holder).textItemWeather.
                                setText(Integer.toString(temp));
                        break;
                    case MODE_WIND:
                        ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                        ((WeatherViewHolder)holder).imageItemWeather.setVisibility(View.VISIBLE);
                        ((WeatherViewHolder)holder).descriptionItemWeather.setVisibility(View.VISIBLE);
                        int wind = (int) Math.round((((HoursList) elements.get(position)).getWind().
                                getSpeed()));
                        int degree = (int) Math.round((((HoursList) elements.get(position)).getWind().
                                getDeg()));
                        ((WeatherViewHolder) holder).descriptionItemWeather.
                                setText(windTypeChooser(wind));
                        Glide.with(context).
                                load(windIconChooser(degree)).
                                into(((WeatherViewHolder) holder).imageItemWeather);
                        ((WeatherViewHolder) holder).textItemWeather.
                                setText(Integer.toString(wind));
                        break;
                    case MODE_HUMIDITY:
                        ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                        ((WeatherViewHolder)holder).imageItemWeather.setVisibility(View.VISIBLE);
                        int humidity = ((HoursList)elements.get(position)).getMain().getHumidity();
                        Glide.with(context).
                                load(humidityIconChooser(humidity)).
                                into(((WeatherViewHolder) holder).imageItemWeather);
                        ((WeatherViewHolder) holder).textItemWeather.
                                setText(Integer.toString(humidity));
                        break;
                    case MODE_PRESSURE:
                        ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                        int pressure = (int) Math.round((((HoursList) elements.get(position)).getMain().
                                getPressure()));
                        ((WeatherViewHolder) holder).textItemWeather.
                                setText(Integer.toString(pressure));
                        break;
            }
        }else if(elements.get(position) instanceof DaysList){
            switch (MODE_FEATURE){
                case MODE_DATE:
                    ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                    ((WeatherViewHolder)holder).textItemWeather.setText(getDay(position));
                    break;
                case MODE_STATUS:
                    ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                    ((WeatherViewHolder)holder).imageItemWeather.setVisibility(View.VISIBLE);
                    String status = (((DaysList)elements.get(position)).getWeather().
                            get(0).getDescription()).toString();
                    ((WeatherViewHolder) holder).textItemWeather.
                            setText(status);
                    Glide.with(context).
                            load(iconChooser(status)).
                            into(((WeatherViewHolder)holder).imageItemWeather);
                    break;
                case MODE_TEMP:
                    ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                    int temp = (int)Math.round((((DaysList)elements.get(position)).getTemp().
                            getDay()));
                    ((WeatherViewHolder) holder).textItemWeather.
                            setText(Integer.toString(temp));
                    break;
                case MODE_WIND:
                    ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                    ((WeatherViewHolder)holder).imageItemWeather.setVisibility(View.VISIBLE);
                    ((WeatherViewHolder)holder).descriptionItemWeather.setVisibility(View.VISIBLE);
                    int wind = (int)Math.round((((DaysList)elements.get(position)).getSpeed()));
                    int degree = Math.round((((DaysList) elements.get(position)).
                            getDeg()));
                    Glide.with(context).
                            load(windIconChooser(degree)).
                            into(((WeatherViewHolder) holder).imageItemWeather);
                    ((WeatherViewHolder) holder).descriptionItemWeather.
                            setText(windTypeChooser(wind));
                    ((WeatherViewHolder) holder).textItemWeather.
                            setText(Integer.toString(wind));
                    break;
                case MODE_HUMIDITY:
                    ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                    ((WeatherViewHolder)holder).imageItemWeather.setVisibility(View.VISIBLE);
                    int humidity = ((DaysList)elements.get(position)).getHumidity();
                    Glide.with(context).
                            load(humidityIconChooser(humidity)).
                            into(((WeatherViewHolder) holder).imageItemWeather);
                    ((WeatherViewHolder) holder).textItemWeather.
                            setText(Integer.toString(humidity));
                    break;
                case MODE_PRESSURE:
                    ((WeatherViewHolder)holder).textItemWeather.setVisibility(View.VISIBLE);
                    int pressure = (int)Math.round((((DaysList)elements.get(position)).getPressure()));
                    ((WeatherViewHolder) holder).textItemWeather.
                            setText(Integer.toString(pressure));
                    break;
            }
        }
    }

    private String getDay(int dayAfter){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM.", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        Date date = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(dayAfter));
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

    private String windIconChooser(int status){
        String wind;
        if(status >= 40 && status < 130){
            wind = windIcons[2];
        }else if(status >= 130 && status < 220){
            wind = windIcons[1];
        }else if(status >= 220 && status < 310){
            wind = windIcons[3];
        }else{
            wind = windIcons[0];
        }
        return  wind;
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

    private String humidityIconChooser(int status){
        String humidity;
        if(status >= 0 && status <= 25){
            humidity = humidityIcons[0];
        }else if(status > 25 && status <= 50){
            humidity = humidityIcons[1];
        }else if(status > 50 && status <= 75){
            humidity = humidityIcons[2];
        }else{
            humidity = humidityIcons[3];
        }
        return humidity;
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_item_weather) TextView textItemWeather;
        @BindView(R.id.description_item_weather) TextView descriptionItemWeather;
        @BindView(R.id.image_item_weather) ImageView imageItemWeather;

        public Context context;

        public WeatherViewHolder(View itemView, Context context){
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
        }
    }


}
