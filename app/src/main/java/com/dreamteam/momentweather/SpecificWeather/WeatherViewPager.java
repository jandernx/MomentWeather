package com.dreamteam.momentweather.SpecificWeather;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.dreamteam.momentweather.R;

public class WeatherViewPager extends ViewPager {

    private boolean swiping;

    public WeatherViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swiping ? super.onTouchEvent(event) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swiping ? super.onInterceptTouchEvent(event) : false;
    }

    public void setSwiping(boolean swiping){
        this.swiping = swiping;
    }

}
