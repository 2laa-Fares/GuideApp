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
import android.support.v4.view.PagerAdapter;
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
import com.example.a10.guideapplication.model.FavouriteApi;
import com.example.a10.guideapplication.model.Section;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class ImageAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    List<FavouriteApi> arraylistFavourite;


    public ImageAdapter(Context context, List<FavouriteApi> arraylistFavourite) {
        this.context = context;
        this.arraylistFavourite = arraylistFavourite;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        return arraylistFavourite.size();
    }


    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        if(position == 0 )
            view.removeAllViews();

        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        FrameLayout myImage = (FrameLayout) myImageLayout.findViewById(R.id.image);
        TextView placeName = (TextView) myImageLayout.findViewById(R.id.placeName);
        RatingBar ratingBar = (RatingBar) myImageLayout.findViewById(R.id.rating);

        ImageView image = new ImageView(App.getContext());

        final Section section = arraylistFavourite.get(position).getSection();

        if(section != null & section.getPlaceName() != null)
            placeName.setText(section.getPlaceName());

        if(section != null & section.getRate() != null) {
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            ratingBar.setRating(section.getRate());
        }

        if(section != null & section.getImage() != null) {
            byte[] data = Base64.decode(section.getImage(), Base64.DEFAULT);
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
            myImage.setBackground(ob);
        }

        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
                intent.putExtra(App.getContext().getString(R.string.placeType), arraylistFavourite.get(position).getType());
                if(section !=  null & arraylistFavourite.get(position).getType().equals("doctors")) {
                    Doctor doctor = new Doctor(section.getID(), section.getPlaceName(), section.getType(), section.getImage(), section.getRate());
                    intent.putExtra(App.getContext().getString(R.string.place), doctor);
                }else intent.putExtra(App.getContext().getString(R.string.place), section);
                App.getContext().startActivity(intent);
            }
        });

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }
}