package com.dreamteam.momentweather.SearchWeather.Favorites;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamteam.momentweather.MainActivity;
import com.dreamteam.momentweather.R;
import com.dreamteam.momentweather.SearchWeather.Favorites.FavoritesDatabase.DBQueries;
import com.dreamteam.momentweather.SearchWeather.History.HistoryAdapter;
import com.dreamteam.momentweather.SearchWeather.History.HistoryElement;
import com.dreamteam.momentweather.SearchWeather.SearchWeatherFragment;
import com.dreamteam.momentweather.SpecificWeather.CurrentWeather.CurrentWeatherModel.CurrentWeather;
import com.dreamteam.momentweather.SpecificWeather.Retrofit.RetrofitModule;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class FavoritesFragment extends Fragment{

    @BindView(R.id.favorites_recycler) RecyclerView favoritesRecycler;
    @BindView(R.id.imageView) ImageView imageViewLocation;
    @BindView(R.id.city_name) TextView cityNameLocation;
    @BindView(R.id.temperature) TextView temperatureLocation;
    @BindView(R.id.favorites_text) TextView favoritesText;

    private LinearLayoutManager linearLayoutManager;
    private HistoryAdapter favoritesAdapter;
    private List<HistoryElement> favoritesElementList;
    public Handler handler;

    public static FavoritesFragment newInstance() {

        Bundle args = new Bundle();

        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        favoritesElementList = new ArrayList<>();
        favoritesAdapter = new HistoryAdapter(getActivity(), favoritesElementList);
        if(favoritesElementList.isEmpty()) {
            favoritesRecycler.setVisibility(View.GONE);
            favoritesText.setVisibility(View.VISIBLE);
        }
        favoritesRecycler.setLayoutManager(linearLayoutManager);
        favoritesRecycler.setAdapter(favoritesAdapter);
        handler = new Handler();
        getFavoritesList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void getFavoritesList(){
        List<HistoryElement> favoritesElementList = DBQueries.getTable(getActivity(), DBQueries.tableFAVORITES);
        if(favoritesElementList != null) {
            int last = favoritesElementList.size() - 1;
            for(int i = 0; i < favoritesElementList.size(); i++){
                new GetCityWeatherTask().execute(Double.toString(favoritesElementList.get(last - i).getLatitude()),
                        Double.toString(favoritesElementList.get(last - i).getLongitude()),
                        favoritesElementList.get(last - i).getCityName(),
                        favoritesElementList.get(last - i).getId(),
                        favoritesElementList.get(last - i).getFavorite());
            }
        }

        favoritesRecycler.setVisibility(View.GONE);
        favoritesText.setVisibility(View.VISIBLE);
    }
    public void setLocation(double latitude, double longitude) {
        new GetCityWeatherTask().execute(Double.toString(latitude),
                Double.toString(longitude));
    }



    public Fragment getThis(){ return  this; }




    class GetCityWeatherTask extends AsyncTask<String, Void, CurrentWeather> {

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
        final String APP_ID = "28e909fe816297eafabdac9118bfa097";
        private String cityName, id, favorite;
        double latitude, longitude;

        @Override
        protected CurrentWeather doInBackground(String... strings) {
            Response<CurrentWeather> response;
            latitude = Double.parseDouble(strings[0]);
            longitude = Double.parseDouble(strings[1]);
            try {
                cityName = strings[2];
                id = strings[3];
                favorite = strings[4];
            }catch (IndexOutOfBoundsException e){}

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
        protected void onPostExecute(final CurrentWeather currentWeather) {
            super.onPostExecute(currentWeather);
            if(cityName != null){
                try {
                    favoritesElementList.add(new HistoryElement(
                            iconChooser(currentWeather.getWeather().get(0).getDescription()),
                            cityName, currentWeather.getMain().getTemp().intValue(),
                            latitude, longitude, id, favorite));
                    favoritesAdapter.notifyDataSetChanged();
                    favoritesRecycler.setVisibility(View.VISIBLE);
                    favoritesText.setVisibility(View.GONE);
                }catch (NullPointerException e){}
            }else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            RelativeLayout loadingRelative = ButterKnife.findById(getThis().getActivity(), R.id.loading_relative);
                            loadingRelative.setVisibility(View.GONE);

                            SearchWeatherFragment searchWeatherFragment =
                                    (SearchWeatherFragment)((MainActivity)(getThis().getActivity())).searchWeatherFragment;
                            CardView cardView = searchWeatherFragment.getLocationCard();
                            Glide.with(getThis().getActivity()).
                                    load(iconChooser(currentWeather.getWeather().get(0).getDescription())).
                                    into((ImageView)ButterKnife.findById(cardView, R.id.imageView));
                            ((TextView)ButterKnife.findById(cardView, R.id.city)).setText(
                                    currentWeather.getName());
                            ((TextView)ButterKnife.findById(cardView, R.id.status)).setText(
                                    currentWeather.getWeather().get(0).getDescription());
                            ((TextView)ButterKnife.findById(cardView, R.id.temperature)).setText(
                                    Integer.toString(currentWeather.getMain().getTemp().intValue()) + "°");
                            ((TextView)ButterKnife.findById(cardView, R.id.wind)).setText(
                                    Integer.toString(currentWeather.getWind().getSpeed().intValue()) + "mph");
                            ((TextView)ButterKnife.findById(cardView, R.id.pressure)).setText(
                                    Integer.toString(currentWeather.getMain().getPressure().intValue()) + "mPa");

                            searchWeatherFragment.setAnimationSet();
                            cityNameLocation.setText(currentWeather.getName().toString());
                            Glide.with(getActivity()).
                                    load(iconChooser(currentWeather.getWeather().get(0).getDescription())).
                                    into(imageViewLocation);
                            temperatureLocation.setText(
                                    Integer.toString(currentWeather.getMain().getTemp().intValue()) + "°");
                        } catch (NullPointerException e) {
                        }
                    }
                }, 500);

            }
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
