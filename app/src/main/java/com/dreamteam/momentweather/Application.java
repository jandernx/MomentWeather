package com.dreamteam.momentweather;

import android.content.Context;
import android.support.multidex.MultiDex;

public class Application extends android.app.Application {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

}
