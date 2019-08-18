package com.example.a10.guideapplication.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Hotel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelServicesAdapter extends RecyclerView.Adapter<HotelServicesAdapter.HotelServicesViewHolder> {

    List<Hotel> hotels;
    HotelServicesAdapter(List<Hotel> hotels){
        this.hotels = hotels;
    }
    @NonNull
    @Override
    public HotelServicesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.hotel_services_fragment, viewGroup, false);
        return new HotelServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelServicesViewHolder holder, int i) {
        if (hotels.get(i).getRoomType()!=null){
            holder.roomTypeTV.setText(hotels.get(i).getRoomType());
        }
        if (hotels.get(i).getPrice()!=null){
            holder.priceTV.setText(String.valueOf(hotels.get(i).getPrice()));
        }
        if (hotels.get(i).isWifi()){
            holder.wifiImageView.setVisibility(View.VISIBLE);
        }else {
            holder.wifiImageView.setVisibility(View.GONE);
        }
        if (hotels.get(i).isBool()){
            holder.swimmingImageView.setVisibility(View.VISIBLE);
        }else {
            holder.swimmingImageView.setVisibility(View.GONE);
        }
        if (hotels.get(i).isMeals()){
            holder.mealsImageView.setVisibility(View.VISIBLE);
        }else {
            holder.mealsImageView.setVisibility(View.GONE);
        }
        if (hotels.get(i).isGym()){
            holder.weightImageView.setVisibility(View.VISIBLE);
        }else {
            holder.weightImageView.setVisibility(View.GONE);
        }
        if (hotels.get(i).isParking()){
            holder.parkingImageView.setVisibility(View.VISIBLE);
        }else {
            holder.parkingImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class HotelServicesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.roomTypeTV)
        TextView roomTypeTV;
        @BindView(R.id.priceTV)
        TextView priceTV;
        @BindView(R.id.wifiImageView)
        ImageView wifiImageView;
        @BindView(R.id.swimmingImageView)
        ImageView swimmingImageView;
        @BindView(R.id.mealsImageView)
        ImageView mealsImageView;
        @BindView(R.id.weightImageView)
        ImageView weightImageView;
        @BindView(R.id.parkingImageView)
        ImageView parkingImageView;
        public HotelServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
