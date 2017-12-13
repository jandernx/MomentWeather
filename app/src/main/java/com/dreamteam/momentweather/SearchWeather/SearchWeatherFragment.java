package com.dreamteam.momentweather.SearchWeather;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dreamteam.momentweather.MainActivity;
import com.dreamteam.momentweather.R;
import com.dreamteam.momentweather.SearchWeather.Favorites.FavoritesDatabase.DBQueries;
import com.dreamteam.momentweather.SearchWeather.Favorites.FavoritesFragment;
import com.dreamteam.momentweather.SearchWeather.History.HistoryElement;
import com.dreamteam.momentweather.SearchWeather.History.HistoryFragment;
import com.dreamteam.momentweather.SpecificWeather.WeatherViewPager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchWeatherFragment extends Fragment{

    private SearchWeatherFragmentAdapter searchWeatherFragmentAdapter;
    private Fragment[] fragments = new Fragment[2];
    private PlaceAutocompleteFragment placeAutoCompleteFragment;
    private ChosenCityListener chosenCityListener;
    private Handler handler;

    @BindView(R.id.tab_layout_slider) TabLayout tabLayout;
    @BindView(R.id.view_pager_slider) WeatherViewPager weatherViewPager;
    @BindView(R.id.location_fragment) CardView locationCard;
    @BindView(R.id.loading_relative) RelativeLayout loadingFrame;
    @BindView(R.id.moon_rotated) ImageView moonRotated;
    @BindView(R.id.location_moving_card_background) FrameLayout locationBackground;
    @BindView(R.id.repeat_btn) Button repeatButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        chosenCityListener = (ChosenCityListener)context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        chosenCityListener = (ChosenCityListener)activity;
    }

    public static SearchWeatherFragment newInstance() {

        Bundle args = new Bundle();

        SearchWeatherFragment fragment = new SearchWeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_weather, container, false);
        ButterKnife.bind(this, view);
        final Animation rotateAnimation = AnimationUtils.
                loadAnimation(getActivity(), R.anim.animation_rotate);
        moonRotated.startAnimation(rotateAnimation);
        loadingFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        locationBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingFrame.setVisibility(View.GONE);
                locationBackground.setVisibility(View.GONE);
            }
        });

        if(getArguments().getBoolean("back")) {
            loadingFrame.setVisibility(View.GONE);
            locationBackground.setVisibility(View.GONE);
        }

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).requestLocation();
            }
        });


        placeAutoCompleteFragment = (PlaceAutocompleteFragment) getChildFragmentManager().
                findFragmentById(R.id.place_autocomplete_fragment);
        if(placeAutoCompleteFragment == null){
            placeAutoCompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().
                    findFragmentById(R.id.place_autocomplete_fragment);
        }


        placeAutoCompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                chosenCityListener.onCityChosen(place.getName().toString(),
                        place.getLatLng().latitude, place.getLatLng().longitude);
                addToHistory(place.getName().toString(),
                        place.getLatLng().latitude, place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
            }
        });



        fragments[0] = HistoryFragment.newInstance();
        fragments[1] = FavoritesFragment.newInstance();

        searchWeatherFragmentAdapter = new SearchWeatherFragmentAdapter(getFragmentManager(),
                getActivity(), fragments);

        weatherViewPager.setSwiping(true);
        weatherViewPager.setAdapter(searchWeatherFragmentAdapter);
        tabLayout.setupWithViewPager(weatherViewPager);

        handler = new Handler();

        ButterKnife.findById(locationCard, R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingFrame.setVisibility(View.GONE);
                locationBackground.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.remove(placeAutoCompleteFragment);
            fragmentTransaction.commit();
    }

    public CardView getLocationCard(){ return locationCard; }

    private void addToHistory(String cityName, double latitude, double longitude){
        List<HistoryElement> historyElementList = DBQueries.getTable(getActivity(), "tableHISTORY");


        if(historyElementList != null && historyElementList.size() > 10){
            DBQueries.addToDatabase(new HistoryElement("0", cityName, Double.toString(latitude),
                    Double.toString(longitude), "false"), getActivity(), "tableHISTORY");
            DBQueries.deleteElementFromDatabase(getActivity(),
                    new HistoryElement(historyElementList.get(0).getId(),
                            historyElementList.get(0).getCityName(),
                            Double.toString(historyElementList.get(0).getLatitude()),
                            Double.toString(historyElementList.get(0).getLongitude()),
                            "false"), "tableHISTORY");
        }else{
            DBQueries.addToDatabase(new HistoryElement("0", cityName, Double.toString(latitude),
                    Double.toString(longitude), "false"), getActivity(), "tableHISTORY");
        }

    }

    public void setLocation(double latitude, double longitude){
        ((FavoritesFragment)fragments[1]).setLocation(latitude, longitude);

    }

    private Animation scaleAnimation(final View parent, float bX, float fX, final boolean reverse){
        Animation animation = new ScaleAnimation(
                bX, fX,
                bX, fX,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {valueAnimation(parent, reverse);}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {}
        });
        return animation;
    }

    private void valueAnimation(final View view, boolean reverse){
        String[] colors = {"#99000000", "#00000000"};
        ValueAnimator valueAnimator = new ValueAnimator();
        if(reverse)
            valueAnimator.setIntValues(Color.parseColor(colors[1]), Color.parseColor(colors[0]));
        else
            valueAnimator.setIntValues(Color.parseColor(colors[0]), Color.parseColor(colors[1]));

        valueAnimator.setDuration(1000);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setBackgroundColor((Integer)animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private Animation translateAnimation(float bX, float fX, int bXType, int fXType){
        Animation animation = new TranslateAnimation(
                bXType, bX,
                fXType, fX,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        return animation;
    }

    public void setAnimationSet(){
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(1000);
        animationSet.addAnimation(scaleAnimation(locationBackground, 0.5f, 1f, true));
        animationSet.addAnimation(translateAnimation(1f, 0f,
                Animation.RELATIVE_TO_PARENT, Animation.RELATIVE_TO_SELF));
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnimationSet animationSet = new AnimationSet(true);
                        animationSet.setFillAfter(true);
                        animationSet.setDuration(1000);
                        animationSet.addAnimation(scaleAnimation(locationBackground, 1f, 0.5f, false));
                        animationSet.addAnimation(translateAnimation(0f, 1f,
                                Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_PARENT));
                        locationCard.startAnimation(animationSet);
                    }
                }, 1500);

            }
        });
        locationCard.startAnimation(animationSet);
    }



}
