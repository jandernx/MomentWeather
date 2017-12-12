package com.dreamteam.momentweather.SearchWeather.Favorites.FavoritesDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public DBHelper(Context context) {
        super(context, "MomentWeatherDB", null, 1);
        this.context = context;
    }
    private ContentValues cv;
    final static String column_ID = "_ID";
    final static String tableFAVORITES = "tableFAVORITES";
    final static String tableHISTORY = "tableHISTORY";
    final static String columnCITY_NAME = "cityName";
    final static String columnLATITUDE = "latitude";
    final static String columnLONGITUDE = "longitude";
    final static String columnFAVORITE = "favorite";


    @Override
    public void onCreate(SQLiteDatabase db) {
        cv = new ContentValues();

        db.execSQL("CREATE TABLE " + tableFAVORITES + " ("
            + column_ID + " TEXT NOT NULL, "
                + columnCITY_NAME + " TEXT NOT NULL, "
                + columnLATITUDE + " TEXT NOT NULL, "
                + columnLONGITUDE + " TEXT NOT NULL, "
                + columnFAVORITE + " TEXT NOT NULL"
                    + ");");

        db.execSQL("CREATE TABLE " + tableHISTORY + " ("
                + column_ID + " TEXT NOT NULL, "
                + columnCITY_NAME + " TEXT NOT NULL, "
                + columnLATITUDE + " TEXT NOT NULL, "
                + columnLONGITUDE + " TEXT NOT NULL, "
                + columnFAVORITE + " TEXT NOT NULL"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableFAVORITES);
        onCreate(db);
    }


}
