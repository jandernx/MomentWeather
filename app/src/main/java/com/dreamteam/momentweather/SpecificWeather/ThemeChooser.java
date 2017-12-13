package com.dreamteam.momentweather.SpecificWeather;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class ThemeChooser {
    private String backStartCenterColor, panelColor, statusBarColor, endColor = "#00000000", photoUri;

    private GradientDrawable gradientDrawable;

    private String[] startCenterColors = {
            "#2c2b0d", "#00580b", "#263645",
            "#c5bdcb", "#a48060", "#274a65",
            "#216031", "#0d8691", "#081714",
            "#6ea0d5", "#77a5b9"
    };

    private String[] panelColors = {
            "#99b0a18e", "#99579877", "#99437290",
            "#9993b1e7", "#99e1bc88", "#995d90bf",
            "#99042413", "#99d96050", "#999b9aa2",
            "#994572a0", "#99204d41",
    };

    private String[] statusBarColors = {
            "#9c988f", "#007584", "#02618f",
            "#0e1d34", "#331e18", "#a1bbcc",
            "#88b193", "#ee7c02", "#487781",
            "#6b96c9", "#a3c6e2",
    };

    private String[] photoUris = {
        "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Fsunny.png?alt=media&token=c89ff990-0eb5-46a1-8c30-692785fad119",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Ffewclouds.png?alt=media&token=c59dde18-c327-4f37-b1c1-4b491d0f751a",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Fscatteredclouds.png?alt=media&token=97e89e42-3648-44de-acd5-e858e3daa0fa",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Fbrokenclouds.png?alt=media&token=49d4e0fa-c633-4f30-80a6-e51339a23d77",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Fshowerrain.png?alt=media&token=1533d2db-1628-4428-9c58-87ea3c36aea0",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Fmoderaterain.png?alt=media&token=d4adcc0a-aa06-4375-9136-83ca98abb440",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Flightrain.png?alt=media&token=b7b4f0be-a362-4ad2-a4e2-025219468b57",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Frain.png?alt=media&token=9ba6bf00-1bb0-4f83-83b7-836e59ac85d8",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Fthunderstorm.png?alt=media&token=f1a4696d-884d-4056-bbc8-191930793f7f",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Fsnow.png?alt=media&token=410e191d-edcf-4904-8e32-d2de8341eaf5",
            "https://firebasestorage.googleapis.com/v0/b/letstalk-fb9fc.appspot.com/o/stickers%2Fweather%2Fweather_blur%2Fmist.png?alt=media&token=32b32beb-fcd7-41ac-a7ba-0ee57cdc0c57"
    };

    public ThemeChooser(String status){
        if(status.equals("clear sky")){
            photoUri = photoUris[0];
            backStartCenterColor = startCenterColors[0];
            panelColor = panelColors[0];
            statusBarColor = statusBarColors[0];

        }else if(status.equals("few clouds")){
            photoUri = photoUris[1];
            backStartCenterColor = startCenterColors[1];
            panelColor = panelColors[1];
            statusBarColor = statusBarColors[1];

        }else if(status.equals("scattered clouds")){
            photoUri = photoUris[2];
            backStartCenterColor = startCenterColors[2];
            panelColor = panelColors[2];
            statusBarColor = statusBarColors[2];

        }else if(status.equals("broken clouds")){
            photoUri = photoUris[3];
            backStartCenterColor = startCenterColors[3];
            panelColor = panelColors[3];
            statusBarColor = statusBarColors[3];

        }else if(status.equals("shower rain")){
            photoUri = photoUris[4];
            backStartCenterColor = startCenterColors[4];
            panelColor = panelColors[4];
            statusBarColor = statusBarColors[4];

        }else if(status.equals("moderate rain")){
            photoUri = photoUris[5];
            backStartCenterColor = startCenterColors[5];
            panelColor = panelColors[5];
            statusBarColor = statusBarColors[5];

        }else if(status.equals("light rain")){
            photoUri = photoUris[6];
            backStartCenterColor = startCenterColors[6];
            panelColor = panelColors[6];
            statusBarColor = statusBarColors[6];

        }else if(status.equals("rain")){
            photoUri = photoUris[7];
            backStartCenterColor = startCenterColors[7];
            panelColor = panelColors[7];
            statusBarColor = statusBarColors[7];

        }else if(status.equals("thunderstorm")){
            photoUri = photoUris[8];
            backStartCenterColor = startCenterColors[8];
            panelColor = panelColors[8];
            statusBarColor = statusBarColors[8];

        }else if(status.equals("snow")){
            photoUri = photoUris[9];
            backStartCenterColor = startCenterColors[9];
            panelColor = panelColors[9];
            statusBarColor = statusBarColors[9];

        }else if(status.equals("mist")){
            photoUri = photoUris[10];
            backStartCenterColor = startCenterColors[10];
            panelColor = panelColors[10];
            statusBarColor = statusBarColors[10];

        }else{
            photoUri = photoUris[0];
            backStartCenterColor = startCenterColors[0];
            panelColor = panelColors[0];
            statusBarColor = statusBarColors[0];

        }
    }

    public int getBackStartCenterColor() {
        return Color.parseColor(backStartCenterColor);
    }

    public int getStatusBarColor() {
        return Color.parseColor(statusBarColor);
    }

    public void setPanelColor(Drawable drawable) {
        GradientDrawable gradientDrawable = (GradientDrawable)drawable;
        gradientDrawable.setColor(Color.parseColor(panelColor));
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public GradientDrawable getGradientDrawable(){
        int[] colors = new int[3];
        colors[0] = Color.parseColor(backStartCenterColor);
        colors[1] = Color.parseColor(backStartCenterColor);
        colors[2] = Color.parseColor(endColor);
        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                colors);
        gradientDrawable.setCornerRadius(0f);
        return gradientDrawable;
    }

}
