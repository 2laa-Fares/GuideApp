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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.ReviewWithOwner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>{

    List<ReviewWithOwner> reviews;
    public ReviewsAdapter(List<ReviewWithOwner> reviews){
        this.reviews = reviews;
    }
    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.review_item, viewGroup, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int i) {
        if (reviews.get(i).getUserName()!=null){
            holder.userNameTextView.setText(reviews.get(i).getUserName());
        }
        if (reviews.get(i).getImage()!=null){
            byte[] decodedString = Base64.decode(reviews.get(i).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
            holder.userImageView.setImageBitmap(decodedByte);
        }
        if (reviews.get(i).getRate()!=null){
            holder.ratingBar.setRating(reviews.get(i).getRate().floatValue());
            holder.rateTextView.setText(String. valueOf(reviews.get(i).getRate()).concat(" from 5"));
        }else {
            holder.ratingBar.setVisibility(View.GONE);
        }
        if (reviews.get(i).getDetails()!=null){
            holder.ratingDescriptionTextView.setText(reviews.get(i).getDetails());
        }
        if (reviews.get(i).getDate()!=null){
            holder.dateTextView.setText(reviews.get(i).getDate());
        }
    }

    @Override
    public int getItemCount() {
        if (reviews==null)
            return 0;
        return reviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.userImageView)
        ImageView userImageView;
        @BindView(R.id.userNameTextView)
        TextView userNameTextView;
        @BindView(R.id.dateTextView)
        TextView dateTextView;
        @BindView(R.id.ratingBar3)
        RatingBar ratingBar;
        @BindView(R.id.rateTextView)
        TextView rateTextView;
        @BindView(R.id.ratingDescriptionTextView)
        TextView ratingDescriptionTextView;
        ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
