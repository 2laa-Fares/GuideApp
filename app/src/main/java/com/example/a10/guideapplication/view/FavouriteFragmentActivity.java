package com.example.a10.guideapplication.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Favourite;
import com.example.a10.guideapplication.model.FavouriteApi;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.FavouritePresenter;
import com.example.a10.guideapplication.repository.repositoryImp.FavouriteRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class FavouriteFragmentActivity extends Fragment implements FavouriteInterface {
    @BindView(R.id.toolbar_back)
    Button back;
    @BindView(R.id.hotel)
    TextView hotelTextView;
    @BindView(R.id.hotelPager)
    ViewPager hotelPager;
    @BindView(R.id.hotelIndicator)
    CircleIndicator hotelIndicator;
    @BindView(R.id.shop)
    TextView shopTextView;
    @BindView(R.id.shopPager)
    ViewPager shopPager;
    @BindView(R.id.shopIndicator)
    CircleIndicator shopIndicator;
    @BindView(R.id.doctor)
    TextView doctorTextView;
    @BindView(R.id.doctorPager)
    ViewPager doctorPager;
    @BindView(R.id.doctorIndicator)
    CircleIndicator doctorIndicator;
    @BindView(R.id.restaurant)
    TextView restaurantTextView;
    @BindView(R.id.restaurantPager)
    ViewPager restaurantPager;
    @BindView(R.id.restaurantIndicator)
    CircleIndicator restaurantIndicator;
    @BindView(R.id.NoFavouriteImage)
    ImageView NoFavouriteImage;

    FavouriteInterface Interface;
    FavouritePresenter presenter;
    List<FavouriteApi> hotelFavourite = new ArrayList<>();
    List<FavouriteApi> doctorFavourite = new ArrayList<>();
    List<FavouriteApi> shopFavourite = new ArrayList<>();
    List<FavouriteApi> restaurantFavourite = new ArrayList<>();
    private ProgressDialog progressBar;
    Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, view);
        context = App.getContext();
        Interface = this;
        presenter = new FavouritePresenter(Interface, new FavouriteRepositoryImp());

        final Gson gson = new Gson();
        User user = gson.fromJson(PrefUtils.getKeys(App.getContext(), getString(R.string.user)), User.class);

        if((hotelFavourite != null && hotelFavourite.size() > 0) || (doctorFavourite != null && doctorFavourite.size() > 0) || (shopFavourite != null && shopFavourite.size() > 0) || (restaurantFavourite != null && restaurantFavourite.size() > 0))
            init();
        else {
            presenter.getFavourites(user.getID());
            progressBar = new ProgressDialog(context);
            progressBar.setMessage("جارى تحميل البيانات...");
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        return view;
    }


    private void init(){
        if((hotelFavourite != null && hotelFavourite.size() > 0) || (doctorFavourite != null && doctorFavourite.size() > 0) || (shopFavourite != null && shopFavourite.size() > 0) || (restaurantFavourite != null && restaurantFavourite.size() > 0))
            NoFavouriteImage.setVisibility(View.GONE);

        if(hotelFavourite != null && hotelFavourite.size() > 0){
            hotelPager.removeAllViews();
            hotelPager.setAdapter(new ImageAdapter(App.getContext(),hotelFavourite));
            hotelIndicator.setViewPager(hotelPager);
            hotelTextView.setVisibility(View.VISIBLE);
            hotelPager.setVisibility(View.VISIBLE);
            hotelIndicator.setVisibility(View.VISIBLE);
        }

        if(doctorFavourite != null && doctorFavourite.size() > 0){
            doctorPager.removeAllViews();
            doctorPager.setAdapter(new ImageAdapter(App.getContext(),doctorFavourite));
            doctorIndicator.setViewPager(doctorPager);
            doctorTextView.setVisibility(View.VISIBLE);
            doctorPager.setVisibility(View.VISIBLE);
            doctorIndicator.setVisibility(View.VISIBLE);
        }

        if(shopFavourite != null && shopFavourite.size() > 0){
            shopPager.removeAllViews();
            shopPager.setAdapter(new ImageAdapter(App.getContext(),shopFavourite));
            shopIndicator.setViewPager(shopPager);
            shopTextView.setVisibility(View.VISIBLE);
            shopPager.setVisibility(View.VISIBLE);
            shopIndicator.setVisibility(View.VISIBLE);
        }

        if(restaurantFavourite != null && restaurantFavourite.size() > 0){
            restaurantPager.removeAllViews();
            restaurantPager.setAdapter(new ImageAdapter(App.getContext(),restaurantFavourite));
            restaurantIndicator.setViewPager(restaurantPager);
            restaurantTextView.setVisibility(View.VISIBLE);
            restaurantPager.setVisibility(View.VISIBLE);
            restaurantIndicator.setVisibility(View.VISIBLE);
        }
        //new RetrieveFeedTask().execute();

    }

    @Override
    public void favourites(List<FavouriteApi> favouriteApis) {
        progressBar.dismiss();
        if(favouriteApis != null && favouriteApis.size() > 0) {
            for (FavouriteApi favouriteApi : favouriteApis) {
                if (favouriteApi.getType() != null) {
                    if (favouriteApi.getType().equals("hotels")) {
                        hotelFavourite.add(favouriteApi);
                    } else {
                        if (favouriteApi.getType().equals("doctors")) {
                            doctorFavourite.add(favouriteApi);
                        } else {
                            if (favouriteApi.getType().equals("stores")) {
                                shopFavourite.add(favouriteApi);
                            } else {
                                if (favouriteApi.getType().equals("coffees")) {
                                    restaurantFavourite.add(favouriteApi);
                                }
                            }
                        }
                    }
                }
            }
            init();
        }
    }

    @Override
    public void message(Boolean response) {

    }

    @Override
    public void isFavorite(Boolean b) {

    }

    @Override
    public void isFavDeleted(boolean b) {

    }

}
