package com.dreamteam.momentweather.SpecificWeather.HoursWeather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamteam.momentweather.R;
import com.dreamteam.momentweather.SpecificWeather.DaysWeather.DaysModel.DaysList;
import com.dreamteam.momentweather.SpecificWeather.DaysWeather.DaysModel.DaysModel;
import com.dreamteam.momentweather.SpecificWeather.DaysWeather.DaysModel.Weather;
import com.dreamteam.momentweather.SpecificWeather.HoursWeather.HoursModel.HoursList;
import com.dreamteam.momentweather.SpecificWeather.HoursWeather.HoursModel.HoursModel;
import com.dreamteam.momentweather.SpecificWeather.SpecificWeatherFragment;
import com.dreamteam.momentweather.SpecificWeather.WeatherElementsAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roman on 24.06.17.
 */

public class HoursFragment extends Fragment implements SpecificWeatherFragment.DataIsReadyListener{

    @BindView(R.id.hours_recycler_hours) RecyclerView hoursRecycler;
    @BindView(R.id.status_recycler_hours) RecyclerView statusRecycler;
    @BindView(R.id.temp_recycler_hours) RecyclerView tempRecycler;
    @BindView(R.id.wind_recycler_hours) RecyclerView windRecycler;
    @BindView(R.id.humidity_recycler_hours) RecyclerView humidityRecycler;
    @BindView(R.id.pressure_recycler_hours) RecyclerView pressureRecycler;
    @BindView(R.id.hours_hours) TextView hoursWeather;

    private int dragView = -1;
    private WeatherElementsAdapter hoursAdapter, statusAdapter, tempAdapter,
                                    windAdapter, humidityAdapter, pressureAdapter;

    private List<HoursList> hoursLists;

    public static HoursFragment newInstance() {
        Bundle args = new Bundle();
        HoursFragment fragment = new HoursFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hours, container, false);
        ButterKnife.bind(this, view);
        setDay();
        return view;
    }


    @Override
    public void setData(Object object) {
        hoursLists = ((HoursModel)object).getList();
        cutUnnecessary();
        setAdapters();
    }

    private void setDay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        hoursWeather.setText(simpleDateFormat.format(new Date()));
    }

    private void setAdapters(){
        hoursAdapter = new WeatherElementsAdapter(getActivity(), hoursLists, 0);
        statusAdapter = new WeatherElementsAdapter(getActivity(), hoursLists, 2);
        tempAdapter = new WeatherElementsAdapter(getActivity(), hoursLists, 3);
        windAdapter = new WeatherElementsAdapter(getActivity(), hoursLists, 4);
        humidityAdapter = new WeatherElementsAdapter(getActivity(), hoursLists, 5);
        pressureAdapter = new WeatherElementsAdapter(getActivity(), hoursLists, 6);

        hoursRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        statusRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        tempRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        windRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        humidityRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        pressureRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        hoursRecycler.setAdapter(hoursAdapter);
        statusRecycler.setAdapter(statusAdapter);
        tempRecycler.setAdapter(tempAdapter);
        windRecycler.setAdapter(windAdapter);
        humidityRecycler.setAdapter(humidityAdapter);
        pressureRecycler.setAdapter(pressureAdapter);

        hoursRecycler.addOnScrollListener(scrollListener);
        statusRecycler.addOnScrollListener(scrollListener);
        tempRecycler.addOnScrollListener(scrollListener);
        windRecycler.addOnScrollListener(scrollListener);
        humidityRecycler.addOnScrollListener(scrollListener);
        pressureRecycler.addOnScrollListener(scrollListener);

    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener(){
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(recyclerView == hoursRecycler && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                dragView = 0;
            }else if(recyclerView == statusRecycler && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                dragView = 2;
            }else if(recyclerView == tempRecycler && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                dragView = 3;
            }else if(recyclerView == windRecycler && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                dragView = 4;
            }else if(recyclerView == humidityRecycler && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                dragView = 5;
            }else if(recyclerView == pressureRecycler && newState == RecyclerView.SCROLL_STATE_DRAGGING){
                dragView = 6;
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(recyclerView == hoursRecycler && dragView == 0){
                statusRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);
            }else if(recyclerView == statusRecycler && dragView == 2){
                hoursRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);
            }else if(recyclerView == tempRecycler && dragView == 3){
                hoursRecycler.scrollBy(dx, dy);
                statusRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);
            }else if(recyclerView == windRecycler && dragView == 4){
                hoursRecycler.scrollBy(dx, dy);
                statusRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);
            }else if(recyclerView == humidityRecycler && dragView == 5){
                hoursRecycler.scrollBy(dx, dy);
                statusRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);
            }else if(recyclerView == pressureRecycler && dragView == 6){
                hoursRecycler.scrollBy(dx, dy);
                statusRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
            }
        }

    };

    private void cutUnnecessary(){
        int cut = -1;
        int size = hoursLists.size();
        for(int i = 0; i < size; i++){
            int time = Integer.parseInt(hoursLists.get(i).getDtTxt().substring(12, 13));
            if(i != 0 && time == 0){
                cut = i;
                break;
            }
        }
        for(int i = size - 1; i > cut; i--) hoursLists.remove(i);

    }
}
