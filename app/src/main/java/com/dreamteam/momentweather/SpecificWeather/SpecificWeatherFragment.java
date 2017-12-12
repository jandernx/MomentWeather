package com.dreamteam.momentweather.SpecificWeather;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamteam.momentweather.MainActivity;
import com.dreamteam.momentweather.R;
import com.dreamteam.momentweather.SpecificWeather.CurrentWeather.CurrentWeatherFragment;
import com.dreamteam.momentweather.SpecificWeather.CurrentWeather.CurrentWeatherModel.CurrentWeather;
import com.dreamteam.momentweather.SpecificWeather.DaysWeather.DaysFragment;
import com.dreamteam.momentweather.SpecificWeather.DaysWeather.DaysModel.DaysModel;
import com.dreamteam.momentweather.SpecificWeather.HoursWeather.HoursFragment;
import com.dreamteam.momentweather.SpecificWeather.HoursWeather.HoursModel.HoursModel;
import com.dreamteam.momentweather.SpecificWeather.Retrofit.RetrofitModule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * Created by roman on 24.06.17.
 */

public class SpecificWeatherFragment extends Fragment {

    private Fragment daysFragment, hoursFragment, currentWeatherFragment;
    private Fragment[] fragments = new Fragment[2];
    private DataIsReadyListener hoursListener, daysListener, currentListener;


    @BindView(R.id.tab_layout_slider) TabLayout tabLayout;
    @BindView(R.id.view_pager_slider) WeatherViewPager viewPager;
    @BindView(R.id.layout_slider_wrapper) FrameLayout sliderWrapper;
    @BindView(R.id.fragment_slider) FrameLayout fragmentSlider;
    @BindView(R.id.loading_frame) FrameLayout loadingFrame;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.fragment_specific_weather) RelativeLayout fragmentSpecificWeather;
    @BindView(R.id.layout_slider) RelativeLayout  layoutSlider;
    @BindView(R.id.background_photo) ImageView backgroundPhoto;
    @BindView(R.id.gradient_photo) View gradientPhoto;
    @BindView(R.id.city_name) TextView cityName;
    @BindView(R.id.back_arrow) ImageView buttonBack;

    final String APP_ID = "28e909fe816297eafabdac9118bfa097";
    private String city;
    private double latitude, longitude;
    private boolean expand;
    private int weatherHeight;
    private float weatherY;
    private Handler handler;

    public interface DataIsReadyListener{
        void setData(Object object);
    }

    public static SpecificWeatherFragment newInstance() {

        Bundle args = new Bundle();

        SpecificWeatherFragment fragment = new SpecificWeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specific_weather, container, false);
        ButterKnife.bind(this, view);
        loadingFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        city = getArguments().getString("cityName", "Kiyv");
        latitude = getArguments().getDouble("latitude", 50.45);
        longitude = getArguments().getDouble("longitude", 30.52);
        cityName.setText(city);

        daysFragment = DaysFragment.newInstance();
        hoursFragment = HoursFragment.newInstance();
        fragments[0] = hoursFragment;
        fragments[1] = daysFragment;

        currentWeatherFragment = CurrentWeatherFragment.newInstance();
        replaceFragment(currentWeatherFragment);

        hoursListener = (DataIsReadyListener)hoursFragment;
        daysListener = (DataIsReadyListener)daysFragment;
        currentListener = (DataIsReadyListener)currentWeatherFragment;

        viewPager.setAdapter(new WeatherFragmentPagerAdapter(getFragmentManager(), getActivity(), fragments));
        final ImageView expandButton = ButterKnife.findById(fragmentSlider, R.id.button_expand_weather);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expand) {
                    weatherHeight = fragmentSlider.getHeight();
                    weatherY = fragmentSlider.getY();
                    expand = true;
                    expandButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                    sliderWrapper.animate().translationY(
                            -Resources.getSystem().getDisplayMetrics().heightPixels * 4 / 11).setDuration(200).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            sliderWrapper.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    Resources.getSystem().getDisplayMetrics().heightPixels * 6 / 7));
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    });
                }else{
                    expand = false;
                    expandButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    sliderWrapper.animate().translationY(weatherY).setDuration(200).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            sliderWrapper.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    weatherHeight));
                        }
                    });
                }
            }
        });
        tabLayout.setupWithViewPager(viewPager);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new GetCurrentWeatherTask().execute();
            }
        }, 600);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity)getActivity()).goBackListener();
                    }
                }, 400);
                animateRotateReverse(buttonBack);
            }
        });


        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        animateRotateReverse(buttonBack);
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.layout_weather_wrapper, fragment);
        fragmentTransaction.commit();
    }

    class GetCurrentWeatherTask extends AsyncTask<Void, Void, CurrentWeather>{

        @Override
        protected CurrentWeather doInBackground(Void... params) {
            Response<CurrentWeather> response;
            try {
                response = RetrofitModule.buildApi().getCurrentWeatherData(latitude, longitude, "metric", APP_ID).execute();
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(CurrentWeather currentWeather) {
            super.onPostExecute(currentWeather);
            try {
                currentListener.setData(currentWeather);
                ThemeChooser themeChooser = new ThemeChooser(currentWeather.getWeather().get(0).getDescription());
                fragmentSpecificWeather.setBackgroundColor(themeChooser.getBackStartCenterColor());
                gradientPhoto.setBackground(themeChooser.getGradientDrawable());
                themeChooser.setPanelColor(layoutSlider.getBackground());
                if(Build.VERSION.SDK_INT >= 21) {
                    getActivity().getWindow().setStatusBarColor(themeChooser.getStatusBarColor());
                }
                Glide.with(getActivity()).
                        load(themeChooser.getPhotoUri()).
                        into(backgroundPhoto);

                new GetHoursDataTask().execute();
            }catch(NullPointerException e){
                Toast.makeText(getActivity(), "Troubles with getting data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class GetHoursDataTask extends AsyncTask<Void, Void, HoursModel>{

        @Override
        protected HoursModel doInBackground(Void... params) {
            Response<HoursModel> response;
            try {
                response = RetrofitModule.buildApi().getHoursData(latitude, longitude, "metric", APP_ID).execute();
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(HoursModel hoursModel) {
            super.onPostExecute(hoursModel);
            try {
                hoursListener.setData(hoursModel);
                new GetDaysDataTask().execute();
            }catch (NullPointerException e){
                Toast.makeText(getActivity(), "Troubles with getting data", Toast.LENGTH_SHORT).show();
            }

        }
    }

    class GetDaysDataTask extends AsyncTask<Void, Void, DaysModel>{

        @Override
        protected DaysModel doInBackground(Void... params) {
            Response<DaysModel> response;
            try {
                response = RetrofitModule.buildApi().getDaysData(latitude, longitude, "metric", 7, APP_ID).execute();
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(DaysModel daysModel) {
            super.onPostExecute(daysModel);
            try {
                loadingFrame.setVisibility(View.GONE);
                daysListener.setData(daysModel);
                animateRotate(buttonBack);
            }catch (NullPointerException e){
                Toast.makeText(getActivity(), "Troubles with getting data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void animateRotate(final View button){
        button.animate().rotation(180).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    private void animateRotateReverse(final View button){
        button.animate().rotation(0).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }
}
