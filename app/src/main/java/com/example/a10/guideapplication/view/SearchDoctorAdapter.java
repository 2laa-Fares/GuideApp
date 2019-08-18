package com.example.a10.guideapplication.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Placeholder;
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
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchDoctorAdapter extends RecyclerView.Adapter<SearchDoctorAdapter.SearchDoctorViewHolder> {

    List<Doctor> doctors;
    List<Section> sections;
    String type;
    public SearchDoctorAdapter(List list, String type){
        this.type = type;
        if (type.equals(App.getContext().getString(R.string.doctor))){
            this.doctors = list;
        }else {
            this.sections = list;
        }
    }
    @NonNull
    @Override
    public SearchDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.search_doctor_item, viewGroup, false);
        return new SearchDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchDoctorViewHolder holder, int i) {
        if (type.equals(App.getContext().getString(R.string.doctor))){
            if (doctors.get(i).getName()!=null){
                holder.placeNameTextView.setText(doctors.get(i).getName());
            }
            if (doctors.get(i).getSpecialization()!=null){
                holder.specialization.setText(doctors.get(i).getSpecialization());
            }
            if (doctors.get(i).getRate()!=null) {
                holder.ratingBar.setRating(doctors.get(i).getRate().floatValue());
            }else {
                holder.ratingBar.setVisibility(View.GONE);
            }
            if (doctors.get(i).getImage()!=null){
                byte[] decodedString = Base64.decode(doctors.get(i).getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                holder.placeImageView.setImageBitmap(decodedByte);
            }
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                    intent.putExtra(App.getContext().getString(R.string.placeType), App.getContext().getString(R.string.doctor));
                    intent.putExtra(App.getContext().getString(R.string.place), doctors.get(holder.getAdapterPosition()));
                    App.getContext().startActivity(intent);
                }
            });
        }else {
            if (sections.get(i).getPlaceName()!=null){
                holder.placeNameTextView.setText(sections.get(i).getPlaceName());
            }
            holder.specialization.setVisibility(View.GONE);
            if (sections.get(i).getRate()!=null) {
                holder.ratingBar.setRating(sections.get(i).getRate().floatValue());
            }else {
                holder.ratingBar.setVisibility(View.GONE);
            }
            if (sections.get(i).getImage()!=null){
                byte[] decodedString = Base64.decode(sections.get(i).getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                holder.placeImageView.setImageBitmap(decodedByte);
            }
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                    intent.putExtra(App.getContext().getString(R.string.placeType), App.getContext().getString(R.string.store));
                    intent.putExtra(App.getContext().getString(R.string.place), sections.get(holder.getAdapterPosition()));
                    App.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(type.equals(App.getContext().getString(R.string.doctor))){
            if (doctors == null)
                return 0;
            return doctors.size();
        }else {
            if (sections == null)
                return 0;
            return sections.size();
        }
    }

    public class SearchDoctorViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.placeImageView)
        CircleImageView placeImageView;
        @BindView(R.id.textView4)
        TextView placeNameTextView;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.textView5)
        TextView specialization;
        @BindView(R.id.container)
        ConstraintLayout container;

        public SearchDoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
