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
import com.example.a10.guideapplication.model.Images;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchImagesAdapter extends RecyclerView.Adapter<BranchImagesAdapter.BranchImagesViewHolder> {

    List<Images> images;
    public BranchImagesAdapter(List<Images> images){
        this.images = images;
    }
    @NonNull
    @Override
    public BranchImagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.menu_item, viewGroup, false);
        return new BranchImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchImagesViewHolder holder, int i) {
        if (images.get(i).getData()!=null){
            byte[] decodedString = Base64.decode(images.get(i).getData(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.menuImageView.setImageBitmap(decodedByte);
        }else {
            holder.menuImageView.setVisibility(View.GONE);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.menuImageView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            holder.menuImageView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        if (images==null)
            return 0;
        return images.size();
    }

    public class BranchImagesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.menuImageView)
        ImageView menuImageView;
        public BranchImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
