package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.customViews.WrapContentHeightViewPager;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Favourite;
import com.example.a10.guideapplication.model.FavouriteApi;
import com.example.a10.guideapplication.model.Review;
import com.example.a10.guideapplication.model.ReviewWithOwner;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.DoctorPresenter;
import com.example.a10.guideapplication.presenter.FavouritePresenter;
import com.example.a10.guideapplication.presenter.PlacePresenter;
import com.example.a10.guideapplication.presenter.ReviewsPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.DoctorRepositoryImp;
import com.example.a10.guideapplication.repository.repositoryImp.FavouriteRepositoryImp;
import com.example.a10.guideapplication.repository.repositoryImp.PlacesRepositoryImp;
import com.example.a10.guideapplication.repository.repositoryImp.ReviewsRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceProfileActivity extends AppCompatActivity implements FavouriteInterface, ReviewsInterface, PlaceInterface, DoctorInterface{

    @BindView(R.id.collapsing_toolbar)
    Toolbar collapsing_toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsing_toolbar_layout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    WrapContentHeightViewPager viewPager;
    @BindView(R.id.fabRate)
    com.github.clans.fab.FloatingActionButton fabRate;
    @BindView(R.id.fabFavorite)
    com.github.clans.fab.FloatingActionButton fabFavorite;
    @BindView(R.id.placeNameTV)
    TextView placeNameTV;
    @BindView(R.id.ratingBar2)
    RatingBar ratingBar2;
    @BindView(R.id.collapsing_toolbar_image_view)
    ImageView collapsing_toolbar_image_view;
    @BindView(R.id.media_image)
    ImageView media_image;

    private TabAdapter adapter;
    FavouriteInterface listener;
    FavouritePresenter presenter;
    ReviewsInterface reviewsInterface;
    ReviewsPresenter reviewsPresenter;
    PlaceInterface placeInterface;
    PlacePresenter placePresenter;
    DoctorInterface doctorInterface;
    DoctorPresenter doctorPresenter;
    Boolean favorite;
    PopupWindow pw;
    User user;
    String placeType;
    Section section;
    Doctor doctor;
    boolean userCanEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_profile);
        ButterKnife.bind(this);
        App.setContext(this);
        setSupportActionBar(collapsing_toolbar);

        placeType = getIntent().getStringExtra(getString(R.string.placeType));
        if(placeType.equals(getString(R.string.doctor))) {
            doctor = getIntent().getParcelableExtra(getString(R.string.place));
            doctorInterface = this;
            doctorPresenter = new DoctorPresenter(doctorInterface, new DoctorRepositoryImp());
        }
        else {
            section = getIntent().getParcelableExtra(getString(R.string.place));
            placeInterface = this;
            placePresenter = new PlacePresenter(new PlacesRepositoryImp(), placeInterface);
        }

        final Gson gson = new Gson();
        user = gson.fromJson(PrefUtils.getKeys(App.getContext(), getString(R.string.user)), User.class);

        if (user.getType() == 2 && user.getCategory().equals(placeType) && (user.getPlaces() != null || user.getPlaces().equals(""))) {
            String places = user.getPlaces().substring(0, user.getPlaces().length() - 1);
            String[] placesID = places.split(",");
            int[] placesIDs = strArrayToIntArray(placesID);
            if (user.getCategory().equals(getString(R.string.doctor)) && Arrays.asList(placesIDs).contains(doctor.getID())) userCanEdit = true;
            else if(Arrays.asList(placesIDs).contains(section.getID())) userCanEdit = true;
        }

        listener = this;
        presenter = new FavouritePresenter(listener, new FavouriteRepositoryImp());
        reviewsInterface = this;
        reviewsPresenter = new ReviewsPresenter(reviewsInterface, new ReviewsRepositoryImp());
        media_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (favorite==null){
            if (placeType.equals(getString(R.string.doctor))){
                Doctor doctor = getIntent().getParcelableExtra(getString(R.string.place));
                presenter.isFavorite(user.getID(), doctor.getID(), 2);
            }else {
                Section section = getIntent().getParcelableExtra(getString(R.string.place));
                presenter.isFavorite(user.getID(), section.getID(), 1);
            }
        }else if (favorite){
            fabFavorite.setImageResource(R.mipmap.favorite);
        }else {
            fabFavorite.setImageResource(R.mipmap.favorite_grey);
        }
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favorite){
                    Favourite favorite = new Favourite();
                    if (placeType.equals(getString(R.string.doctor))){
                        favorite.setType(2);
                        Doctor doctor = getIntent().getParcelableExtra(getString(R.string.place));
                        favorite.setSectionID(doctor.getID());
                    }else {
                        favorite.setType(1);
                        Section section = getIntent().getParcelableExtra(getString(R.string.place));
                        favorite.setSectionID(section.getID());
                    }
                    favorite.setUserID(2);
                    presenter.setFavourite(favorite);
                }else {
                    if (placeType.equals(getString(R.string.doctor))){
                        Doctor doctor = getIntent().getParcelableExtra(getString(R.string.place));
                        presenter.deleteFavorite(doctor.getID(), 2, 2);
                    }else {
                        Section section = getIntent().getParcelableExtra(getString(R.string.place));
                        presenter.deleteFavorite(section.getID(), 2, 1);
                    }

                }
            }
        });

        PlaceInfoFragment placeInfoFragment = new PlaceInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.placeType), placeType);
        bundle.putParcelable(getString(R.string.place), getIntent().getParcelableExtra(getString(R.string.place)));
        bundle.putBoolean("UserCanEdit",userCanEdit);
        placeInfoFragment.setArguments(bundle);
        BranchesFragment branchesFragment = new BranchesFragment();
        branchesFragment.setArguments(bundle);
        OffersFragment offersFragment = new OffersFragment();
        offersFragment.setArguments(bundle);
        if (placeType.equals(getString(R.string.coffee))){
            int[] tabIcons = {
                    R.mipmap.offers,
                    R.mipmap.menu,
                    R.mipmap.location2,
                    R.mipmap.info
            };

            if (section!=null){
                if (section.getPlaceName()!=null){
                    placeNameTV.setText(section.getPlaceName());
                }
                if (section.getRate()!=null){
                    ratingBar2.setRating(section.getRate().floatValue());
                }else {
                    ratingBar2.setVisibility(View.GONE);
                }
                if (section.getImage()!=null){
                    byte[] decodedString = Base64.decode(section.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                    collapsing_toolbar_image_view.setImageBitmap(decodedByte);
                }
            }
            adapter = new TabAdapter(getSupportFragmentManager());
            MenuFragment menuFragment = new MenuFragment();
            menuFragment.setArguments(bundle);

            adapter.addFragment(offersFragment, "عروض");
            adapter.addFragment(menuFragment, "المنيو");
            adapter.addFragment(branchesFragment, "الفروع");
            adapter.addFragment(placeInfoFragment, "عن المطعم");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(3);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons[3]);

        }else if (placeType.equals(getString(R.string.hotel))){
            if (section!=null){
                if (section.getPlaceName()!=null){
                    placeNameTV.setText(section.getPlaceName());
                }
                if (section.getRate()!=null){
                    ratingBar2.setRating(section.getRate().floatValue());
                }else {
                    ratingBar2.setVisibility(View.GONE);
                }
                if (section.getImage()!=null){
                    byte[] decodedString = Base64.decode(section.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                    collapsing_toolbar_image_view.setImageBitmap(decodedByte);
                }
            }
            int[] tabIcons = {
                    R.mipmap.offers,
                    R.mipmap.location2,
                    R.mipmap.info
            };
            adapter = new TabAdapter(getSupportFragmentManager());
            adapter.addFragment(offersFragment, "عروض");
            adapter.addFragment(branchesFragment, "الفروع");
            adapter.addFragment(placeInfoFragment, "عن الفندق");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(2);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);

        }else if (placeType.equals(getString(R.string.store))){
            if (section!=null){
                if (section.getPlaceName()!=null){
                    placeNameTV.setText(section.getPlaceName());
                }
                if (section.getRate()!=null){
                    ratingBar2.setRating(section.getRate().floatValue());
                }else {
                    ratingBar2.setVisibility(View.GONE);
                }
                if (section.getImage()!=null){
                    byte[] decodedString = Base64.decode(section.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                    collapsing_toolbar_image_view.setImageBitmap(decodedByte);
                }
            }
            int[] tabIcons = {
                    R.mipmap.offers,
                    R.mipmap.location2,
                    R.mipmap.info
            };
            adapter = new TabAdapter(getSupportFragmentManager());
            adapter.addFragment(offersFragment, "عروض");
            adapter.addFragment(branchesFragment, "الفروع");
            adapter.addFragment(placeInfoFragment, "عن المتجر");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(2);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);

        }else if(placeType.equals(getString(R.string.doctor))){
            doctor = getIntent().getParcelableExtra(getString(R.string.place));
            if (doctor!=null){
                if (doctor.getName()!=null){
                    placeNameTV.setText(doctor.getName());
                }
                if (doctor.getRate()!=null){
                    ratingBar2.setRating(doctor.getRate().floatValue());
                }else {
                    ratingBar2.setVisibility(View.GONE);
                }
                if (doctor.getImage()!=null){
                    byte[] decodedString = Base64.decode(doctor.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length);
                    collapsing_toolbar_image_view.setImageBitmap(decodedByte);
                }
            }
            int[] tabIcons = {
                    R.mipmap.offers,
                    R.mipmap.location2,
                    R.mipmap.info
            };
            adapter = new TabAdapter(getSupportFragmentManager());
            adapter.addFragment(offersFragment, "عروض");
            adapter.addFragment(branchesFragment, "العيادات");
            adapter.addFragment(placeInfoFragment, "عن الطبيب");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(2);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);

        }

        fabRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.rate_popup, null);
                final RatingBar ratingBar = layout.findViewById(R.id.ratingBar4);
                final EditText rateET = layout.findViewById(R.id.rateEditText);
                final Button rateButton = layout.findViewById(R.id.rateButton);
                pw = new PopupWindow(layout, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
                rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Review review = new Review();
                        if (placeType.equals(getString(R.string.doctor))){
                            Doctor doctor = getIntent().getParcelableExtra(getString(R.string.place));
                            review.setType(2);
                            review.setSectionID(doctor.getID());
                        }else {
                            Section section = getIntent().getParcelableExtra(getString(R.string.place));
                            review.setType(1);
                            review.setSectionID(section.getID());
                        }
                        review.setRate(5-Double.valueOf(String.valueOf(ratingBar.getRating())));
                        if (rateET.getText()!=null){
                            review.setDetails(rateET.getText().toString());
                        }
                        review.setUserID(2);
                        reviewsPresenter.addReview(review);
                    }
                });
                pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pw.setTouchInterceptor(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            pw.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                pw.setOutsideTouchable(true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        App.setContext(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getType() != 0 && (user.getType() == 1 || userCanEdit == true))
            getMenuInflater().inflate(R.menu.edit_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent = new Intent(PlaceProfileActivity.this, EditPlaceActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString(getString(R.string.placeType), placeType);
                if (section == null) mBundle.putParcelable(getString(R.string.place), doctor);
                else mBundle.putParcelable(getString(R.string.place), section);
                intent.putExtras(mBundle);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_delete:
                final LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.delete_popup, null);
                final Button deleteButton = layout.findViewById(R.id.deleteButton);
                final Button cancleButton = layout.findViewById(R.id.cancleButton);
                final PopupWindow pw = new PopupWindow(layout, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //your deleting code
                        if (section == null) doctorPresenter.deleteDoctor(doctor.getID());
                        else placePresenter.deletePlace(section.getID());

                        pw.dismiss();
                    }
                });
                cancleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pw.dismiss();
                    }
                });
                float density = getResources().getDisplayMetrics().density;
                pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pw.setTouchInterceptor(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            pw.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                pw.setOutsideTouchable(true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void isFavorite(Boolean b) {
        if (b){
            favorite = true;
            fabFavorite.setImageResource(R.mipmap.favorite);
        }else{
            favorite = false;
            fabFavorite.setImageResource(R.mipmap.favorite_grey);
        }
    }

    @Override
    public void isFavDeleted(boolean b) {
        if (b){
            Toast.makeText(this, "تم الحذف من المفضلة", Toast.LENGTH_LONG).show();
            favorite = false;
            fabFavorite.setImageResource(R.mipmap.favorite_grey);
        }
    }

    @Override
    public void review(Review review) {
        pw.dismiss();
        if (review!=null){
            final LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.thankful_popup, null);
            final PopupWindow pw = new PopupWindow(layout, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
            pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pw.setTouchInterceptor(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        pw.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            pw.setOutsideTouchable(true);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
        }else {
            Toast.makeText(this, "لم يتم تسجيل التقييم", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void reviews(List<ReviewWithOwner> reviews) {

    }

    public static int[] strArrayToIntArray(String[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = Integer.parseInt(a[i]);
        }
        return b;
    }

    @Override
    public void favourites(List<FavouriteApi> favouriteApis) {

    }

    @Override
    public void doctor(Doctor doctor) {

    }

    @Override
    public void doctorMessage(Boolean response) {
        if (response == true) {
            Toast.makeText(this, "لقد تم حذف البيانات بنجاح", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PlaceProfileActivity.this, PlacesActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.placeType), placeType);
            intent.putExtras(mBundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "برجاء المحاولة فى وقت آخر", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void message(Boolean response) {
        if (response){
            Toast.makeText(this, "تم الاضافة الى المفضلة", Toast.LENGTH_LONG).show();
            this.favorite = true;
            fabFavorite.setImageResource(R.mipmap.favorite);
        }
    }

    @Override
    public void doctors(List<Doctor> doctors) {

    }

    @Override
    public void places(List<Section> sections) {

    }

    @Override
    public void isPlaceAdded(Section section) {

    }

    @Override
    public void isPlaceUpdated(boolean b) {

    }

    @Override
    public void isPlaceDeleted(boolean b) {
        if (b == true) {
            Toast.makeText(this, "لقد تم حذف البيانات بنجاح", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PlaceProfileActivity.this, PlacesActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.placeType), placeType);
            intent.putExtras(mBundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "برجاء المحاولة فى وقت آخر", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void topRated(List sections) {

    }

    @Override
    public void searchDoctor(List<Doctor> doctor) {

    }

    @Override
    public void searchSection(List<Section> section) {

    }
}
