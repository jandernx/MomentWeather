package com.dreamteam.momentweather;


import android.animation.Animator;
import android.app.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.*;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamteam.momentweather.SearchWeather.ChosenCityListener;
import com.dreamteam.momentweather.SearchWeather.Favorites.Location.LocationFragment;
import com.dreamteam.momentweather.SearchWeather.SearchWeatherFragment;
import com.dreamteam.momentweather.SpecificWeather.SpecificWeatherFragment;

import net.semantic_error.turritype.TurriType;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements ChosenCityListener,
                                                    LocationFragment.GetLocationListener{

    public Fragment searchWeatherFragment, locationFragment;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        handler = new Handler();
        locationFragment = LocationFragment.newInstance();
        searchWeatherFragment = SearchWeatherFragment.newInstance();
        replaceFragment(searchWeatherFragment, false, "SearchWeatherFragment");
        requestLocation();

    }

    @Override
    public void onBackPressed() {
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String name){
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_left_enter, R.animator.slide_right_exit);
        fragmentTransaction.replace(R.id.fragment_container, fragment, name);
        if(addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void requestLocation(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                100);
    }

    @Override
    public void onCityChosen(String cityName, double latitude, double longitude) {
        SpecificWeatherFragment specificWeatherFragment =
                SpecificWeatherFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("cityName", cityName);
        specificWeatherFragment.setArguments(bundle);
        replaceFragment(specificWeatherFragment, false, "SpecificWeatherFragment");
    }

    public void goBackListener(){
        SearchWeatherFragment searchWeatherFragment =
                SearchWeatherFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        searchWeatherFragment.setArguments(bundle);
        replaceFragment(searchWeatherFragment, false, "SearchWeatherFragment");
    }

    @Override
    public void getLocation(double latitude, double longitude) {
            ((SearchWeatherFragment) searchWeatherFragment).setLocation(latitude, longitude);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(!checkSwitchLocation()){
                    showAlert();
                }else if(checkSwitchLocation()){
                        getFragmentManager().beginTransaction().
                                replace(android.R.id.content, locationFragment, "LocationFragment").
                                commit();
                }
            }else{
                turryAnimation();
                Button repeat = ButterKnife.findById(this, R.id.repeat_btn);
                repeat.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String isBack = getIntent().getStringExtra("back");
        if(isBack != null && isBack.equals("yes") && !checkSwitchLocation()){
            showAlert();
        }else if(isBack != null && isBack.equals("yes") && checkSwitchLocation()){
                getFragmentManager().beginTransaction().
                        replace(android.R.id.content, locationFragment, "LocationFragment").
                        commit();
        }
    }

    public Activity getActivity(){ return  this; }


    private void turryAnimation(){
        TextView byeBye = ButterKnife.findById(this, R.id.byebye);
        byeBye.setVisibility(View.VISIBLE);
        byeBye.setText("");
        byeBye.setTypeface(Typeface.createFromAsset(getAssets(), "bold.otf"));
        Animator turriType = TurriType.write("Enable location permission and requesting permission in settings" +
                " to provide further work").
                into(byeBye).
                setDuration(3500);
        turriType.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ButterKnife.findById(getActivity(), R.id.repeat_btn).setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ButterKnife.findById(getActivity(), R.id.repeat_btn).setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        turriType.start();
    }

    private void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        alertDialog.setTitle("Location").
                setMessage("Please, enable getting location for further work with app").
                setCancelable(false).
                setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        getIntent().putExtra("back", "yes");
                    }
                }).create().show();
    }

    private boolean checkSwitchLocation(){
        int locationMode = 0;
        String locationString;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        }else{
            locationString = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationString);
        }

    }


}
