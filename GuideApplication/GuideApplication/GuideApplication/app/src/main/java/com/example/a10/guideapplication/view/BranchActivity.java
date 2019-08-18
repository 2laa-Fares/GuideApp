package com.example.a10.guideapplication.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.customViews.WrapContentHeightViewPager;
import com.example.a10.guideapplication.model.Branch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchActivity extends AppCompatActivity {

    @BindView(R.id.collapsing_toolbar)
    Toolbar collapsing_toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsing_toolbar_layout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    WrapContentHeightViewPager viewPager;
    @BindView(R.id.collapsing_toolbar_image_view)
    ImageView placeImageView;
    @BindView(R.id.placeNameTV)
    TextView placeNameTV;
    @BindView(R.id.media_image)
    ImageView media_image;
    private TabAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        ButterKnife.bind(this);
        App.setContext(this);
        setSupportActionBar(collapsing_toolbar);
        media_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Branch branch = getIntent().getParcelableExtra(getString(R.string.branch));
        String placeImage = getIntent().getStringExtra(getString(R.string.placeImage));
        if (placeImage!=null){
            byte[] decodedString = Base64.decode(placeImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
            placeImageView.setImageBitmap(decodedByte);
        }
        if (branch.getName()!=null){
            placeNameTV.setText(branch.getName());
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.branch), branch);
        String placeType = getIntent().getStringExtra(getString(R.string.placeType));
        adapter = new TabAdapter(getSupportFragmentManager());
        BranchPhotosFragment branchPhotosFragment = new BranchPhotosFragment();
        branchPhotosFragment.setArguments(bundle);
        BranchAddressFragment branchAddressFragment = new BranchAddressFragment();
        branchAddressFragment.setArguments(bundle);
        if(placeType.equals(getString(R.string.hotel))){
            int[] tabIcons = {
                    R.mipmap.services,
                    R.mipmap.camera,
                    R.mipmap.navigation
            };
            HotelServicesFragment hotelServicesFragment = new HotelServicesFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("branchId", branch.getID());
            hotelServicesFragment.setArguments(bundle1);
            adapter.addFragment(hotelServicesFragment, "الخدمات");
            adapter.addFragment(branchPhotosFragment, "الصور");
            adapter.addFragment(branchAddressFragment, "العنوان");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(2);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);

        }else {
            int[] tabIcons = {
                    R.mipmap.camera,
                    R.mipmap.navigation,
            };
            adapter.addFragment(branchPhotosFragment, "الصور");
            adapter.addFragment(branchAddressFragment, "العنوان");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(2);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }
}
