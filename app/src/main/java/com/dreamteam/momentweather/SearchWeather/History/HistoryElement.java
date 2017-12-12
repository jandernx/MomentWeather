package com.dreamteam.momentweather.SearchWeather.History;

/**
 * Created by roman on 01.07.17.
 */

public class HistoryElement {

    private String photoUri, cityName, id;
    private double latitude, longitude;
    private int temp;
    private String favorite;

    public HistoryElement(String photoUri, String cityName, int temp){
        this.photoUri = photoUri;
        this.cityName = cityName;
        this.temp = temp;
    }

    public HistoryElement(String id, String cityName, String latitude, String longitude,
                          String favorite){
        this.id = id;
        this.cityName = cityName;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.favorite = favorite;
    }

    public HistoryElement(String photoUri, String cityName, int temp,
                          double latitude, double longitude, String id, String favorite){
        this.photoUri = photoUri;
        this.cityName = cityName;
        this.temp = temp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.favorite = favorite;
    }


    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
