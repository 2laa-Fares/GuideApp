package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;

import java.io.ByteArrayOutputStream;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    List<Section> sections;
    List<Doctor> doctors;
    String type;
    public DoctorsAdapter(List sections, String type){
        if (type.equals(App.getContext().getString(R.string.store))){
            this.sections = sections;
        }else if(type.equals(App.getContext().getString(R.string.doctor))){
            this.doctors = sections;
        }
        this.type = type;
    }
    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.doctor_item, viewGroup, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorViewHolder holder, int i) {
        if (type.equals(App.getContext().getString(R.string.doctor))){
            if (doctors.get(i).getName()!=null){
                holder.doctorTextView.setText(doctors.get(i).getName());
            }
            if (doctors.get(i).getRate()!=null){
                holder.ratingBar.setRating(doctors.get(i).getRate().floatValue());
            }else {
                holder.ratingBar.setVisibility(View.GONE);
            }
            if (doctors.get(i).getSpecialization()!=null){
                holder.specializationTextView.setText(doctors.get(i).getSpecialization());
            }
            if (doctors.get(i).getImage()!=null){
                byte[] decodedString = Base64.decode(doctors.get(i).getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                holder.doctorImageView.setImageBitmap(decodedByte);
            }
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                    intent.putExtra(App.getContext().getString(R.string.placeType), App.getContext().getString(R.string.doctor));
                    if (doctors.get(holder.getAdapterPosition()).getImage()!=null){
                        byte[] decodedString = Base64.decode(doctors.get(holder.getAdapterPosition()).getImage(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        int Height = bitmap.getHeight();
                        int Width = bitmap.getWidth();
                        if (Height>300 || Width>300){
                            int newHeight = 300;
                            int newWidth = 300;
                            float scaleWidth = ((float) newWidth) / Width;
                            float scaleHeight = ((float) newHeight) / Height;
                            Matrix matrix = new Matrix();
                            matrix.postScale(scaleWidth, scaleHeight);
                            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,Width, Height, matrix, true);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream .toByteArray();
                            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            doctors.get(holder.getAdapterPosition()).setImage(encoded);
                        }
                    }
                    intent.putExtra(App.getContext().getString(R.string.place), doctors.get(holder.getAdapterPosition()));
                    App.getContext().startActivity(intent);
                }
            });
        }else {
            if (sections.get(i).getPlaceName()!=null){
                holder.doctorTextView.setText(sections.get(i).getPlaceName());
            }
            if (sections.get(i).getRate()!=null){
                holder.ratingBar.setRating(sections.get(i).getRate().floatValue());
            }else {
                holder.ratingBar.setVisibility(View.GONE);
            }
            if (sections.get(i).getImage()!=null){
                byte[] decodedString = Base64.decode(sections.get(i).getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                holder.doctorImageView.setImageBitmap(decodedByte);
            }
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                    intent.putExtra(App.getContext().getString(R.string.placeType), App.getContext().getString(R.string.store));
                    if (sections.get(holder.getAdapterPosition()).getImage()!=null){
                        byte[] decodedString = Base64.decode(sections.get(holder.getAdapterPosition()).getImage(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        int Height = bitmap.getHeight();
                        int Width = bitmap.getWidth();
                        if (Height>300 || Width>300){
                            int newHeight = 300;
                            int newWidth = 300;
                            float scaleWidth = ((float) newWidth) / Width;
                            float scaleHeight = ((float) newHeight) / Height;
                            Matrix matrix = new Matrix();
                            matrix.postScale(scaleWidth, scaleHeight);
                            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,Width, Height, matrix, true);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream .toByteArray();
                            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            sections.get(holder.getAdapterPosition()).setImage(encoded);
                        }
                    }
                    intent.putExtra(App.getContext().getString(R.string.place), sections.get(holder.getAdapterPosition()));
                    App.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (type.equals(App.getContext().getString(R.string.store))){
            if (sections==null)
                return 0;
            return sections.size();
        }else {
            if (doctors==null)
                return 0;
            return doctors.size();
        }
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView)
        ImageView doctorImageView;
        @BindView(R.id.textView)
        TextView doctorTextView;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.textView15)
        TextView specializationTextView;
        @BindView(R.id.parent)
        ConstraintLayout parent;
        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
