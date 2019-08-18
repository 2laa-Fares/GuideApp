package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.utils.RoundedCornerImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class SearchPlaceAdapter extends RecyclerView.Adapter<SearchPlaceAdapter.SearchPlaceViewHolder> {

    List<Section> sections;
    public SearchPlaceAdapter(List<Section> sections){
        this.sections = sections;
    }
    @NonNull
    @Override
    public SearchPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.search_place_item, viewGroup, false);
        return new SearchPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchPlaceViewHolder holder, int i) {
        if (sections.get(i).getPlaceName()!=null){
            holder.placeNameTextView.setText(sections.get(i).getPlaceName());
        }
        if (sections.get(i).getRate()!=null){
            holder.ratingBar.setRating(sections.get(i).getRate().floatValue());
        }else {
            holder.ratingBar.setVisibility(View.GONE);
        }
        if (sections.get(i).getImage()!=null){
            byte[] decodedString = Base64.decode(sections.get(i).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
            Blurry.with(App.getContext()).from(RoundedCornerImage.getRoundedCornerBitmap(decodedByte)).into(holder.placeImageView);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                intent.putExtra(App.getContext().getString(R.string.placeType), sections.get(holder.getAdapterPosition()).getType());
                intent.putExtra(App.getContext().getString(R.string.place), sections.get(holder.getAdapterPosition()));
                App.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (sections==null)
            return 0;
        return sections.size();
    }

    class SearchPlaceViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.placeImageView)
        ImageView placeImageView;
        @BindView(R.id.textView4)
        TextView placeNameTextView;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.container)
        ConstraintLayout container;
        SearchPlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
