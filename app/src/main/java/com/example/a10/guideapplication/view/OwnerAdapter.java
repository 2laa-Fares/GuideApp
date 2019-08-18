package com.example.a10.guideapplication.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder>  {

    Context context;
    List<Doctor> doctors = new ArrayList<>();
    List<Section> sections = new ArrayList<>();

    public OwnerAdapter(Context context, List<Doctor> doctors, List<Section> sections) {
        this.context = context;
        this.doctors = doctors;
        this.sections = sections;

    }

    @NonNull
    @Override
    public OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.slide, viewGroup, false);
        return new OwnerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final OwnerViewHolder holder, final int position) {

        ImageView image = new ImageView(App.getContext());

        if (doctors != null && doctors.get(position).getName() != null)
            holder.placeName.setText(doctors.get(position).getName());
        else if (sections != null && sections.get(position).getPlaceName() != null)
            holder.placeName.setText(sections.get(position).getPlaceName());


        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        if (doctors != null && doctors.get(position).getRate() != 0)
            holder.ratingBar.setRating(doctors.get(position).getRate());
        else if (sections != null && sections.get(position).getRate() != 0)
            holder.ratingBar.setRating(sections.get(position).getRate());

        byte[] data = null;
        if (doctors != null && doctors.get(position).getImage() != null)
            data = Base64.decode(doctors.get(position).getImage(), Base64.DEFAULT);
        else if (sections != null && sections.get(position).getImage() != null)
            data = Base64.decode(sections.get(position).getImage(), Base64.DEFAULT);

        if(data != null) {
            Bitmap imagesBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            boolean tabletSize = App.getContext().getResources().getBoolean(R.bool.isTablet);
            if (tabletSize) {
                Blurry.with(App.getContext()).radius(5).sampling(1).color(0x33000000).from(Bitmap.createScaledBitmap(imagesBitmap, width / 2, 200, false)).into(image);
            } else {
                Blurry.with(App.getContext()).radius(5).sampling(1).color(0x33000000).from(Bitmap.createScaledBitmap(imagesBitmap, width / 2, 150, false)).into(image);
            }
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            BitmapDrawable ob = new BitmapDrawable(App.getContext().getResources(), Bitmap.createScaledBitmap(bitmap, width / 2, 200, false));
            holder.myImage.setBackground(ob);
        }

        holder.myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sections != null){
                    Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                    intent.putExtra(App.getContext().getString(R.string.placeType), sections.get(holder.getAdapterPosition()).getType());
                    intent.putExtra(App.getContext().getString(R.string.place), sections.get(holder.getAdapterPosition()));
                    App.getContext().startActivity(intent);
                }else{
                    if(doctors != null){
                        Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                        intent.putExtra(App.getContext().getString(R.string.placeType), App.getContext().getString(R.string.doctor));
                        intent.putExtra(App.getContext().getString(R.string.place), doctors.get(holder.getAdapterPosition()));
                        App.getContext().startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (doctors != null && doctors.size() > 0)
            return doctors.size();
        else if (sections != null && sections.size() > 0) return sections.size();
        else return 0;
    }

    public class OwnerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image)
        FrameLayout myImage;
        @BindView(R.id.placeName)
        TextView placeName;
        @BindView(R.id.rating)
        RatingBar ratingBar;
        public OwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}