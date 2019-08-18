package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.utils.RoundedCornerImage;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private static final String TAG = "TAG";
    List<Section> sections;
    public PlaceAdapter(List<Section> sections){
        this.sections = sections;
    }
    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.place_item, viewGroup, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceViewHolder holder, int i) {
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                intent.putExtra(App.getContext().getString(R.string.placeType), sections.get(holder.getAdapterPosition()).getType());
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
        if (sections.get(i).getPlaceName()!=null){
            holder.placeTextView.setText(sections.get(i).getPlaceName());
        }
        if (sections.get(i).getRate()!=null){
            holder.ratingBar.setRating(sections.get(i).getRate().floatValue());
        }else {
            holder.ratingBar.setVisibility(View.GONE);
        }
        if (sections.get(i).getImage()!=null){
            try {
                byte[] decodedString = Base64.decode(sections.get(i).getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                //holder.placeImageView.setImageBitmap(decodedByte);
                Blurry.with(App.getContext()).radius(5).sampling(1).color(0x33000000).from(RoundedCornerImage.getRoundedCornerBitmap(decodedByte)).into(holder.placeImageView);
            }catch (Exception e){
                Log.e(TAG, e.getMessage());
            }

        }

    }

    @Override
    public int getItemCount() {
        if (sections==null)
            return 0;
        return sections.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.parent)
        ConstraintLayout parent;
        @BindView(R.id.imageView)
        ImageView placeImageView;
        @BindView(R.id.textView)
        TextView placeTextView;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
