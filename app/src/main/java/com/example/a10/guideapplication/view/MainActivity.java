package com.example.a10.guideapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.customViews.NonSwipeableViewPager;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.UserPresenter;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.viewPager)
    NonSwipeableViewPager viewPager;
    @BindView(R.id.navigation)
    android.support.design.widget.BottomNavigationView navigationView;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.setContext(this);

        String isLogin = PrefUtils.getKeys(App.getContext(), getString(R.string.user));
        Gson gson = new Gson();
        user = gson.fromJson(isLogin, User.class);

        init();
    }


    private void init(){
        navigationView.getMenu().getItem(4).setChecked(true);

        if (user.getType() == 2) {
            navigationView.getMenu().getItem(0).setVisible(true);
            if(user.getCategory().equals(getString(R.string.store))) {
                navigationView.getMenu().getItem(0).setIcon(R.mipmap.shop_grey);
                navigationView.getMenu().getItem(0).setTitle("المتاجر");
            }else{
                if(user.getCategory().equals(getString(R.string.hotel))) {
                    navigationView.getMenu().getItem(0).setIcon(R.mipmap.hotels_grey);
                    navigationView.getMenu().getItem(0).setTitle("الفنادق");
                }
                else{
                    if(user.getCategory().equals(getString(R.string.doctor))) {
                        navigationView.getMenu().getItem(0).setIcon(R.mipmap.doctor_grey);
                        navigationView.getMenu().getItem(0).setTitle("الأطباء");
                    }
                    else{
                        if(user.getCategory().equals(getString(R.string.coffee))) {
                            navigationView.getMenu().getItem(0).setIcon(R.mipmap.coffee_grey);
                            navigationView.getMenu().getItem(0).setTitle("المطاعم");
                        }
                    }
                }
            }
        }else if(user.getType() == 1){
            navigationView.getMenu().getItem(0).setVisible(true);
            navigationView.getMenu().getItem(0).setIcon(R.mipmap.team);
            navigationView.getMenu().getItem(0).setTitle("المُلاّك");
        }

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());

        if (user.getType() != 3) {
            OwnerFragment ownerFragment = new OwnerFragment();
            pagerAdapter.addFragments(ownerFragment);
        }
        ProfileFragmentActivity profileFragmentActivity = new ProfileFragmentActivity();
        pagerAdapter.addFragments(profileFragmentActivity);

        NotificationFragmentActivity notificationFragmentActivity = new NotificationFragmentActivity();
        pagerAdapter.addFragments(notificationFragmentActivity);

        FavouriteFragmentActivity favouriteFragmentActivity = new FavouriteFragmentActivity();
        pagerAdapter.addFragments(favouriteFragmentActivity);

        MainFragmentActivity mainFragmentActivity = new MainFragmentActivity();
        pagerAdapter.addFragments(mainFragmentActivity);

        viewPager.setPageTransformer(true, new CubeInDepthTransformation());
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(4);

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (user.getType() == 3)
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(3);
                        return true;
                    case R.id.navigation_favorite:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigation_notifications:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_profile:
                        viewPager.setCurrentItem(0);
                        return true;
                    default:
                        viewPager.setCurrentItem(3);
                }
            else switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(4);
                    return true;
                case R.id.navigation_favorite:
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_owner:
                    viewPager.setCurrentItem(0);
                    return true;
                default:
                    viewPager.setCurrentItem(3);
            }
            return false;

        }
    };
}
