package com.example.a10.guideapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Hotel;
import com.example.a10.guideapplication.presenter.HotelPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.HotelRepositoryImp;
import com.example.a10.guideapplication.utils.RtlGridLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelServicesFragment extends Fragment implements HotelInterface{

    @BindView(R.id.menuRecyclerView)
    RecyclerView menuRecyclerView;
    @BindView(R.id.noMenuTextView)
    TextView noMenuTextView;
    HotelInterface hotelInterface;
    HotelPresenter presenter;
    List<Hotel> hotels;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        ButterKnife.bind(this, view);
        int branchId = getArguments().getInt("branchId");
        RecyclerView.LayoutManager manager = new RtlGridLayoutManager(App.getContext(), 2);
        menuRecyclerView.setLayoutManager(manager);
        if (hotels!=null){
            menuRecyclerView.setVisibility(View.VISIBLE);
            noMenuTextView.setVisibility(View.GONE);
            HotelServicesAdapter adapter = new HotelServicesAdapter(hotels);
            menuRecyclerView.setAdapter(adapter);
        }else {
            hotelInterface = this;
            presenter = new HotelPresenter(hotelInterface, new HotelRepositoryImp());
            presenter.getHotels(branchId);
        }
        return view;
    }

    @Override
    public void hotel(Hotel hotel) {

    }

    @Override
    public void message(Boolean response) {

    }

    @Override
    public void hotels(List<Hotel> hotels) {
        if (hotels!=null && hotels.size()>0){
            this.hotels = hotels;
            menuRecyclerView.setVisibility(View.VISIBLE);
            noMenuTextView.setVisibility(View.GONE);
            HotelServicesAdapter adapter = new HotelServicesAdapter(hotels);
            menuRecyclerView.setAdapter(adapter);
        }else {
            menuRecyclerView.setVisibility(View.GONE);
            noMenuTextView.setVisibility(View.VISIBLE);
            noMenuTextView.setText("لا يوجد خدمات لعرضها");
        }
    }
}
