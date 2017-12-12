package com.dreamteam.momentweather.SearchWeather.Favorites.FavoritesDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dreamteam.momentweather.SearchWeather.History.HistoryElement;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


public class DBQueries{

    final static String column_ID = "_ID";
    final public  static String tableHISTORY = "tableHISTORY";
    final public static String tableFAVORITES = "tableFAVORITES";
    final static String columnCITY_NAME = "cityName";
    final static String columnLATITUDE = "latitude";
    final static String columnLONGITUDE = "longitude";
    final static String columnFAVORITE = "favorite";


    private Context context;
    private static DBHelper dbh;
    private static SQLiteDatabase db;

    private static int favoritesCnt = 0;



    public static List<HistoryElement> getTable(Context context, String tableName){
        dbh = new DBHelper(context);
        List<HistoryElement> favoritesList = new ArrayList<>();
        String querySQL = "SELECT * FROM " + tableName;
        db = dbh.getWritableDatabase();

        Cursor cursor = db.rawQuery(querySQL, null);
        if(!cursor.moveToFirst()) {
            return new ArrayList<>();
        }else{favoritesList.add(fillModel(cursor));}

        if(!cursor.moveToNext()) {
            return favoritesList;
        }
        do{
            favoritesList.add(fillModel(cursor));
        }while (cursor.moveToNext());
        cursor.close();
        db.close();
        return favoritesList;
    }

    public static HistoryElement fillModel(Cursor cursor){
        String cityName = cursor.getString(cursor.getColumnIndex(columnCITY_NAME));
        String latitude = cursor.getString(cursor.getColumnIndex(columnLATITUDE));
        String longitude = cursor.getString(cursor.getColumnIndex(columnLONGITUDE));
        String favorite = cursor.getString(cursor.getColumnIndex(columnFAVORITE));
        String id = cursor.getString(cursor.getColumnIndex(column_ID));
        favoritesCnt++;
        return new HistoryElement(id, cityName, latitude, longitude, favorite);
    }
    public static void addToDatabase(HistoryElement favoriteElement, Context context, String tableName){
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String cityName = favoriteElement.getCityName();
        String latitude = Double.toString(favoriteElement.getLatitude());
        String longitude = Double.toString(favoriteElement.getLongitude());
        String favorite = favoriteElement.getFavorite();
        String id;
        if(!favoriteElement.getId().equals("0")) id = favoriteElement.getId();
        else id = new BigInteger(50, new SecureRandom()).toString();
        cv.put(column_ID, id);
        cv.put(columnCITY_NAME, cityName);
        cv.put(columnLATITUDE, latitude);
        cv.put(columnLONGITUDE, longitude);
        cv.put(columnFAVORITE, favorite);
        db.insert(tableName, null, cv);
        db.close();
    }

    public static void deleteElementFromDatabase(Context context, HistoryElement favoriteElement, String tableName){
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
        db.delete(tableName, column_ID + " = ?", new String[]{favoriteElement.getId()});
        db.close();
    }

    public static void changeElementFromDatabase(Context context, HistoryElement favoriteElement,
                                                 String tableName, String favorite){
        deleteElementFromDatabase(context, favoriteElement, tableName);
        favoriteElement.setFavorite(favorite);
        addToDatabase(favoriteElement, context, tableName);
        db.close();
    }

}
