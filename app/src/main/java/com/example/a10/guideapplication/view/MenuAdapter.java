package com.example.a10.guideapplication.view;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    String[] menus;
    int sectionId;
    String type;
    public interface OnItemClickListener {

        void onItemClick(ImageView imageView);
    }
    private final OnItemClickListener listener;

    public MenuAdapter(String type, int sectionId, String[] menus, OnItemClickListener listener){
        this.listener = listener;
        this.menus = menus;
        this.sectionId = sectionId;
        this.type = type;
    }
    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.menu_item, viewGroup, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, int i) {
        String path;
        path = "attach url"+type+"/"+sectionId+"/"+menus[i];
        Glide.with(App.getContext()).load(path).into(holder.menuImageView);
        holder.menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.menuImageView);
            }
        });
        holder.parent.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public int getItemCount() {
        if (menus==null)
            return 0;
        return menus.length;
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
