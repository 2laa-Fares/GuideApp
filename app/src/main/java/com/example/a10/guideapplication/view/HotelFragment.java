package com.example.a10.guideapplication.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.presenter.PlacePresenter;
import com.example.a10.guideapplication.repository.repositoryImp.PlacesRepositoryImp;
import com.example.a10.guideapplication.utils.RtlGridLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelFragment extends Fragment implements PlaceInterface{

    @BindView(R.id.searchEditText)
    SearchView searchEditText;
    @BindView(R.id.mostOfferedRecyclerView)
    RecyclerView mostOfferedRecyclerView;
    @BindView(R.id.newPlacesRecyclerView)
    RecyclerView newPlacesRecyclerView;
    @BindView(R.id.topRatedNotFoundTV)
    TextView topRatedNotFoundTV;
    @BindView(R.id.placesNotFoundTV)
    TextView placesNotFoundTV;
    @BindView(R.id.placesContainer)
    ConstraintLayout placesContainer;
    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    PlaceInterface placeInterface;
    PlacePresenter presenter;
    List<Section> hotels;
    List<Section> topRatedHotels;
    private ProgressDialog progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.coffees_fragment, container, false);
        ButterKnife.bind(this, view);
        searchEditText.setQueryHint("ابحث عن فندق");
        placeInterface = this;
        presenter = new PlacePresenter(new PlacesRepositoryImp(), placeInterface);
        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                presenter.search(getString(R.string.hotel), s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchEditText.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                placesContainer.setVisibility(View.VISIBLE);
                searchRecyclerView.setVisibility(View.GONE);
                return true;
            }
        });
        RecyclerView.LayoutManager manager = new RtlGridLayoutManager(App.getContext(), 2);
        mostOfferedRecyclerView.setLayoutManager(manager);
        RecyclerView.LayoutManager manager2 = new RtlGridLayoutManager(App.getContext(), 2);
        newPlacesRecyclerView.setLayoutManager(manager2);
        if (hotels!=null){
            PlaceAdapter adapter = new PlaceAdapter(hotels);
            newPlacesRecyclerView.setAdapter(adapter);
            newPlacesRecyclerView.setVisibility(View.VISIBLE);
            placesNotFoundTV.setVisibility(View.GONE);

        }else {
            progressBar = new ProgressDialog(App.getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("جارى التحميل ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();
            presenter.getPlaces(getString(R.string.hotel));
        }
        if (topRatedHotels != null) {
            PlaceAdapter adapter2 = new PlaceAdapter(topRatedHotels);
            mostOfferedRecyclerView.setAdapter(adapter2);
            mostOfferedRecyclerView.setVisibility(View.VISIBLE);
            topRatedNotFoundTV.setVisibility(View.GONE);
        }else {
            presenter.topRated(getString(R.string.hotel));
        }
        return view;
    }

    @Override
    public void places(List<Section> sections) {
        progressBar.dismiss();
        if (sections != null && sections.size() > 0) {
            this.hotels = sections;
            PlaceAdapter adapter = new PlaceAdapter(sections);
            newPlacesRecyclerView.setAdapter(adapter);
            newPlacesRecyclerView.setVisibility(View.VISIBLE);
            placesNotFoundTV.setVisibility(View.GONE);

        } else {
            newPlacesRecyclerView.setVisibility(View.INVISIBLE);
            placesNotFoundTV.setVisibility(View.VISIBLE);
            placesNotFoundTV.setText("لا يوجد فنادق لعرضها");
        }
    }

    @Override
    public void isPlaceAdded(Section section) {

    }

    @Override
    public void isPlaceUpdated(boolean b) {

    }

    @Override
    public void isPlaceDeleted(boolean b) {

    }

    @Override
    public void topRated(List sections) {
        if (sections != null && sections.size() > 0) {
            this.topRatedHotels = sections;
            PlaceAdapter adapter = new PlaceAdapter(sections);
            mostOfferedRecyclerView.setAdapter(adapter);
            mostOfferedRecyclerView.setVisibility(View.VISIBLE);
            topRatedNotFoundTV.setVisibility(View.GONE);
        } else {
            mostOfferedRecyclerView.setVisibility(View.INVISIBLE);
            topRatedNotFoundTV.setVisibility(View.VISIBLE);
            topRatedNotFoundTV.setText("لا يوجد فنادق لعرضها");
        }
    }

    @Override
    public void searchDoctor(List<Doctor> doctor) {

    }

    @Override
    public void searchSection(List<Section> section) {
        if (section!=null && section.size()>0){
            placesContainer.setVisibility(View.GONE);
            searchRecyclerView.setVisibility(View.VISIBLE);
            SearchPlaceAdapter adapter = new SearchPlaceAdapter(section);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(App.getContext());
            searchRecyclerView.setLayoutManager(manager);
            searchRecyclerView.setAdapter(adapter);

        }else {
            placesContainer.setVisibility(View.VISIBLE);
            searchRecyclerView.setVisibility(View.GONE);
            Toast.makeText(App.getContext(), "لا يوجد", Toast.LENGTH_LONG).show();
        }
    }
}
