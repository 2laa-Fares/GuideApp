package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragmentActivity extends Fragment {

    @BindView(R.id.caffee)
    LinearLayout caffee;
    @BindView(R.id.hotel)
    LinearLayout hotel;
    @BindView(R.id.doctor)
    LinearLayout doctor;
    @BindView(R.id.shop)
    LinearLayout shop;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        caffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), PlacesActivity.class);
                intent.putExtra(getString(R.string.placeType), getString(R.string.coffee));
                startActivity(intent);
            }
        });
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), PlacesActivity.class);
                intent.putExtra(getString(R.string.placeType), getString(R.string.hotel));
                startActivity(intent);
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), PlacesActivity.class);
                intent.putExtra(getString(R.string.placeType), getString(R.string.doctor));
                startActivity(intent);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.getContext(), PlacesActivity.class);
                intent.putExtra(getString(R.string.placeType), getString(R.string.store));
                startActivity(intent);
            }
        });
        return view;
    }
}
