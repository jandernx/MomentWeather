package com.dreamteam.momentweather.SearchWeather.History;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamteam.momentweather.R;
import com.dreamteam.momentweather.SearchWeather.Favorites.FavoritesDatabase.DBQueries;
import com.dreamteam.momentweather.SpecificWeather.CurrentWeather.CurrentWeatherModel.CurrentWeather;
import com.dreamteam.momentweather.SpecificWeather.Retrofit.RetrofitModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * Created by roman on 01.07.17.
 */

public class HistoryFragment extends Fragment {

    @BindView(R.id.search_result_recycler) RecyclerView historyRecycler;
    @BindView(R.id.history_text) TextView historyTextView;

    private LinearLayoutManager linearLayoutManager;
    private HistoryAdapter historyAdapter;
    private List<HistoryElement> historyElements;

    public static HistoryFragment newInstance() {

        Bundle args = new Bundle();

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        historyElements = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getActivity(), historyElements);
        if(historyElements.isEmpty()) {
            historyRecycler.setVisibility(View.GONE);
            historyTextView.setVisibility(View.VISIBLE);
        }
        historyRecycler.setLayoutManager(linearLayoutManager);
        historyRecycler.setAdapter(historyAdapter);
        getHistoryList();
        return view;
    }

    private void getHistoryList(){
        List<HistoryElement> historyElementList = DBQueries.getTable(getActivity(), "tableHISTORY");
        if(historyElementList != null) {
            int last = historyElementList.size() - 1;
            for(int i = 0; i < historyElementList.size(); i++){
                new GetCityWeatherTask().execute(Double.toString(historyElementList.get(last - i).getLatitude()),
                        Double.toString(historyElementList.get(last - i).getLongitude()),
                        historyElementList.get(last - i).getCityName(),
                        historyElementList.get(last - i).getId(),
                        historyElementList.get(last - i).getFavorite());
            }
        }

        historyRecycler.setVisibility(View.GONE);
        historyTextView.setVisibility(View.VISIBLE);

    }

    class GetCityWeatherTask extends AsyncTask<String, Void, CurrentWeather>{

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
        private String cityName, id, favorite;
        final String APP_ID = "28e909fe816297eafabdac9118bfa097";
        double latitude, longitude;
        @Override
        protected CurrentWeather doInBackground(String... strings) {
            Response<CurrentWeather> response;
            latitude = Double.parseDouble(strings[0]);
            longitude = Double.parseDouble(strings[1]);
            cityName = strings[2];
            id = strings[3];
            favorite = strings[4];

                try {
                    response = RetrofitModule.buildApi().getCurrentWeatherData(latitude,
                            longitude, "metric",
                            APP_ID).execute();
                    return response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){}

            return null;
        }

        @Override
        protected void onPostExecute(CurrentWeather currentWeather) {
            super.onPostExecute(currentWeather);
            try {
                historyElements.add(new HistoryElement(
                        iconChooser(currentWeather.getWeather().get(0).getDescription()),
                        cityName, currentWeather.getMain().getTemp().intValue(),
                        latitude, longitude, id, favorite));
                historyAdapter.notifyDataSetChanged();
                historyRecycler.setVisibility(View.VISIBLE);
                historyTextView.setVisibility(View.GONE);
            }catch (NullPointerException e){}
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
    }
}
