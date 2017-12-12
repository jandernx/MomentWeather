package com.dreamteam.momentweather.SearchWeather;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

/**
 * Created by roman on 01.07.17.
 */

public class SearchWeatherFragmentAdapter extends FragmentStatePagerAdapter{

    private Fragment[] fragments;
    private String[] titles = {"Recent", "Favorites"};
    private Context context;

    public SearchWeatherFragmentAdapter(FragmentManager fm, Context context, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];

    }
}
