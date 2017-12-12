package com.dreamteam.momentweather.SpecificWeather.DaysWeather;

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

public class DaysFragment extends Fragment implements SpecificWeatherFragment.DataIsReadyListener{

    @BindView(R.id.days_recycler_days) RecyclerView daysRecycler;
    @BindView(R.id.status_recycler_days) RecyclerView statusRecycler;
    @BindView(R.id.temp_recycler_days) RecyclerView tempRecycler;
    @BindView(R.id.wind_recycler_days) RecyclerView windRecycler;
    @BindView(R.id.humidity_recycler_days) RecyclerView humidityRecycler;
    @BindView(R.id.pressure_recycler_days) RecyclerView pressureRecycler;
    @BindView(R.id.days_days) TextView month;

    private int dragView = -1;
    private List<DaysList> daysLists;

    private WeatherElementsAdapter daysAdapter, statusAdapter, tempAdapter,
            windAdapter, humidityAdapter, pressureAdapter;

    public static DaysFragment newInstance() {

        Bundle args = new Bundle();

        DaysFragment fragment = new DaysFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_days, container, false);
        ButterKnife.bind(this, view);
        setMonth();

        return view;
    }


    @Override
    public void setData(Object object) {
        daysLists = ((DaysModel)object).getList();
        setAdapters();
    }

    private void setMonth(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        month.setText(simpleDateFormat.format(new Date()));
    }

    private void setAdapters(){
        daysAdapter = new WeatherElementsAdapter(getActivity(), daysLists, 0);
        statusAdapter = new WeatherElementsAdapter(getActivity(), daysLists, 2);
        tempAdapter = new WeatherElementsAdapter(getActivity(), daysLists, 3);
        windAdapter = new WeatherElementsAdapter(getActivity(), daysLists, 4);
        humidityAdapter = new WeatherElementsAdapter(getActivity(), daysLists, 5);
        pressureAdapter = new WeatherElementsAdapter(getActivity(), daysLists, 6);

        daysRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        statusRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        tempRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        windRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        humidityRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        pressureRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        daysRecycler.setAdapter(daysAdapter);
        statusRecycler.setAdapter(statusAdapter);
        tempRecycler.setAdapter(tempAdapter);
        windRecycler.setAdapter(windAdapter);
        humidityRecycler.setAdapter(humidityAdapter);
        pressureRecycler.setAdapter(pressureAdapter);

        daysRecycler.addOnScrollListener(scrollListener);
        statusRecycler.addOnScrollListener(scrollListener);
        tempRecycler.addOnScrollListener(scrollListener);
        windRecycler.addOnScrollListener(scrollListener);
        humidityRecycler.addOnScrollListener(scrollListener);
        pressureRecycler.addOnScrollListener(scrollListener);

    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(recyclerView == daysRecycler && newState == RecyclerView.SCROLL_STATE_DRAGGING){
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
            if(recyclerView == daysRecycler && dragView == 0){
                statusRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);

            }else if(recyclerView == statusRecycler && dragView == 2){
                daysRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);

            }else if(recyclerView == tempRecycler && dragView == 3){
                daysRecycler.scrollBy(dx, dy);
                statusRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);

            }else if(recyclerView == windRecycler && dragView == 4){
                daysRecycler.scrollBy(dx, dy);
                statusRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);

            }else if(recyclerView == humidityRecycler && dragView == 5){
                daysRecycler.scrollBy(dx, dy);
                statusRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                pressureRecycler.scrollBy(dx, dy);

            }else if(recyclerView == pressureRecycler && dragView == 6){
                daysRecycler.scrollBy(dx, dy);
                statusRecycler.scrollBy(dx, dy);
                tempRecycler.scrollBy(dx, dy);
                windRecycler.scrollBy(dx, dy);
                humidityRecycler.scrollBy(dx, dy);

            }
        }
    };
}
