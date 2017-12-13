package com.dreamteam.momentweather.SpecificWeather;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

public class WeatherFragmentPagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter{

    final int PAGE_COUNT = 2;
    final String[] tabTitles = {"Hours", "Days"};
    private Context context;
    private Fragment[] fragments;

    public WeatherFragmentPagerAdapter(FragmentManager fm, Context context, Fragment[] fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
