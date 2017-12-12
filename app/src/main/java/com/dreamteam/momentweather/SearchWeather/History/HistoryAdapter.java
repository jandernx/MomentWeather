package com.dreamteam.momentweather.SearchWeather.History;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamteam.momentweather.MainActivity;
import com.dreamteam.momentweather.R;
import com.dreamteam.momentweather.SearchWeather.ChosenCityListener;
import com.dreamteam.momentweather.SearchWeather.Favorites.FavoritesDatabase.DBQueries;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by roman on 01.07.17.
 */

public class HistoryAdapter extends RecyclerView.Adapter {

    private List<HistoryElement> historyElements;
    private Context context;

    public HistoryAdapter(Context context, List<HistoryElement> historyElements){
        this.historyElements = historyElements;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HistoryViewHolder viewHolder;
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        viewHolder = new HistoryViewHolder(view, context);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final HistoryViewHolder historyViewHolder = (HistoryViewHolder)holder;
        Glide.with(context).
                load(historyElements.get(position).getPhotoUri()).
                into(historyViewHolder.imageView);
        historyViewHolder.cityName.setText(historyElements.get(position).getCityName());
        historyViewHolder.temp.setText(historyElements.get(position).getTemp() + "°");
        if(historyElements.get(position).getFavorite() != null &&
                historyElements.get(position).getFavorite().equals("true")){
            historyViewHolder.setImageStar(true);
        }else{
            historyViewHolder.setImageStar(false);
        }
        historyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyViewHolder.onCityChosen(historyElements.get(position).getCityName(),
                        historyElements.get(position).getLatitude(),
                        historyElements.get(position).getLongitude());
            }
        });
        historyViewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(historyElements.get(position).getFavorite() != null &&
                        historyElements.get(position).getFavorite().equals("true")){
                    historyViewHolder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_inactive));
                    DBQueries.deleteElementFromDatabase(context, historyElements.get(position), DBQueries.tableFAVORITES);
                    DBQueries.changeElementFromDatabase(context, historyElements.get(position), DBQueries.tableHISTORY, "false");
                }else {
                    historyViewHolder.star.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_active));
                    DBQueries.changeElementFromDatabase(context, historyElements.get(position), DBQueries.tableHISTORY, "true");
                    DBQueries.addToDatabase(historyElements.get(position), context, DBQueries.tableFAVORITES);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyElements.size();
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView, star;
        public TextView cityName, temp;
        public View itemView;
        private Context context;
        private ChosenCityListener chosenCityListener;


        public HistoryViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            imageView = ButterKnife.findById(itemView, R.id.imageView);
            cityName = ButterKnife.findById(itemView, R.id.city_name);
            temp = ButterKnife.findById(itemView, R.id.temperature);
            star = ButterKnife.findById(itemView, R.id.city_star);
            this.itemView = itemView;
            chosenCityListener = (ChosenCityListener) context;

        }

        public void onCityChosen(String cityName, double latitude, double longitude){
            chosenCityListener.onCityChosen(cityName,
                    latitude, longitude);
        }

        public void setImageStar(boolean favorite){
            if(favorite){
                star.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_active));
            }else{
                star.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_inactive));
            }
        }



    }
}
