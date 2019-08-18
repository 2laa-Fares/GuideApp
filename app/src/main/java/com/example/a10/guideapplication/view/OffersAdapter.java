package com.example.a10.guideapplication.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Offer;
import com.example.a10.guideapplication.utils.RoundedCornerImage;
import com.example.a10.guideapplication.utils.RtlGridLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    List<Offer> offers;
    public interface OnItemClickListener {

        void onItemClick(ImageView imageView);
    }
    private final OnItemClickListener listener;

    public OffersAdapter(List<Offer> offers, OnItemClickListener listener){
        this.offers =  offers;
        this.listener = listener;
    }
    @NonNull
    @Override
    public OffersAdapter.OffersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.offers_item, viewGroup, false);
        return new OffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.OffersViewHolder holder, int i) {

        if (offers.get(i).getmDate()!=null && offers.get(i).getmEndDate()!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            int start = 0, end = 0;
            try {
                Date date = sdf.parse(offers.get(i).getmDate());
                if (new Date().after(date)) {
                    start = 1;
                }

                date = sdf.parse(offers.get(i).getmEndDate());

                if (new Date().before(date)){
                    end = 1;
                }

            } catch (ParseException e) {
                e.printStackTrace();
                holder.offerImage.setLayoutParams(new ViewGroup.LayoutParams(0,0));
                holder.offerImage.setVisibility(View.GONE);
                holder.offersRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                holder.offersRecyclerView.setVisibility(View.GONE);
            }
            if (start==1&&end==1){
                if (offers.get(i).getMainImage()!=null){
                    byte[] decodedString = Base64.decode(offers.get(i).getMainImage(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                    bitmap = RoundedCornerImage.getRoundedCornerBitmap(bitmap);
                    holder.offerImage.setImageBitmap(bitmap);
                }
                if (offers.get(i).getImages()!=null){
                    holder.offersRecyclerView.setVisibility(View.VISIBLE);
                    String[] images = offers.get(i).getImages().split(",");
                    RecyclerView.LayoutManager manager = new RtlGridLayoutManager(App.getContext(), 3);
                    holder.offersRecyclerView.setLayoutManager(manager);
                    MenuAdapter adapter = new MenuAdapter("Offer", offers.get(i).getID(), images, new MenuAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ImageView imageView) {
                            listener.onItemClick(imageView);
                        }
                    });
                    holder.offersRecyclerView.setAdapter(adapter);
                }else {
                    holder.offersRecyclerView.setVisibility(View.GONE);
                    holder.offersRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(0 ,0));
                }
            }else {
                holder.offerImage.setLayoutParams(new ViewGroup.LayoutParams(0,0));
                holder.offerImage.setVisibility(View.GONE);
                holder.offersRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                holder.offersRecyclerView.setVisibility(View.GONE);
            }
        }else {
            holder.offerImage.setLayoutParams(new ViewGroup.LayoutParams(0,0));
            holder.offerImage.setVisibility(View.GONE);
            holder.offersRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            holder.offersRecyclerView.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        if (offers==null)
            return 0;
        return offers.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.offerImage)
        ImageView offerImage;
        @BindView(R.id.offersRecyclerView)
        RecyclerView offersRecyclerView;
        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
