package com.example.a10.guideapplication.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Images;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditMenuAdapter extends RecyclerView.Adapter<EditMenuAdapter.MenuViewHolder> {

    List<Images> images;

    public EditMenuAdapter(List<Images> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.menu_item, viewGroup, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, int i) {
        byte[] decodedString = Base64.decode(images.get(i).getData(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.menuImageView.setImageBitmap(decodedByte);
        holder.parent.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public int getItemCount() {
        if (images==null)
            return 0;
        return images.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.menuImageView)
        ImageView menuImageView;
        @BindView(R.id.parent)
        ConstraintLayout parent;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
