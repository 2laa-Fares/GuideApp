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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    List<Offer> offers;
    public OffersAdapter(List<Offer> offers){
        this.offers = offers;
    }
    @NonNull
    @Override
    public OffersAdapter.OffersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.offers_item, viewGroup, false);
        return new OffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.OffersViewHolder holder, int i) {
        if (offers.get(i).getMainImage()!=null){
            byte[] decodedString = Base64.decode(offers.get(i).getMainImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
            bitmap = RoundedCornerImage.getRoundedCornerBitmap(bitmap);
            holder.offerImage.setImageBitmap(bitmap);
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
        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
