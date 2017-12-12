package com.dreamteam.momentweather;

import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by roman on 08.08.17.
 */

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
