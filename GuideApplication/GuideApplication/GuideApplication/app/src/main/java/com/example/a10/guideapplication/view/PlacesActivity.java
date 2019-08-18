package com.example.a10.guideapplication.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesActivity extends AppCompatActivity {

    private TabAdapter adapter;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.media_image)
    ImageView media_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        App.setContext(this);
        media_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        int[] tabIcons = {
                R.mipmap.stores,
                R.mipmap.doctors,
                R.mipmap.hotels,
                R.mipmap.caffees
        };
        adapter = new TabAdapter(getSupportFragmentManager());

        StoreFragment storeFragment = new StoreFragment();
        adapter.addFragment(storeFragment, "متاجر");

        DoctorFragment doctorFragment = new DoctorFragment();
        adapter.addFragment(doctorFragment, "اطباء");

        HotelFragment hotelFragment = new HotelFragment();
        adapter.addFragment(hotelFragment, "فنادق");

        CoffeesFragment coffeesFragment = new CoffeesFragment();
        adapter.addFragment(coffeesFragment, "مقاهى ومطاعم");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

        String placeType = getIntent().getStringExtra(getString(R.string.placeType));
        if (placeType.equals(getString(R.string.coffee))){
            viewPager.setCurrentItem(3);
        }else if (placeType.equals(getString(R.string.hotel))){
            viewPager.setCurrentItem(2);
        }else if (placeType.equals(getString(R.string.doctor))){
            viewPager.setCurrentItem(1);
        }else if(placeType.equals(getString(R.string.store))){
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.setContext(this);
    }
}
