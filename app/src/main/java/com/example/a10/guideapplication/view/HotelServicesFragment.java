package com.example.a10.guideapplication.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Hotel;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.HotelPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.HotelRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.example.a10.guideapplication.utils.RtlGridLayoutManager;
import com.example.a10.guideapplication.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class HotelServicesFragment extends Fragment implements HotelInterface{

    @BindView(R.id.servicesRecyclerView)
    RecyclerView servicesRecyclerView;
    @BindView(R.id.noServicesTextView)
    TextView noServicesTextView;
    @BindView(R.id.addServiceFAB)
    android.support.design.widget.FloatingActionButton addServiceFAB;
    HotelInterface hotelInterface;
    HotelPresenter presenter;
    HotelServicesAdapter adapter;
    List<Hotel> hotels;
    private ProgressDialog progressBar;
    boolean wifi, pools, gym, parking, meals;
    int branchId;
    boolean edit;
    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hotel_services, container, false);
        ButterKnife.bind(this, view);
        progressBar = new ProgressDialog(App.getContext());
        progressBar.setCancelable(false);
        progressBar.setMessage("جارى التحميل ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (getArguments() != null) {
            branchId = getArguments().getInt("branchId");
            edit = getArguments().getBoolean(getString(R.string.edit));
            if (edit){
                addServiceFAB.setVisibility(View.VISIBLE);
            }else {
                addServiceFAB.setVisibility(View.GONE);
            }
        }
        RecyclerView.LayoutManager manager = new RtlGridLayoutManager(App.getContext(), 2);
        servicesRecyclerView.setLayoutManager(manager);
        if (hotels!=null){
            servicesRecyclerView.setVisibility(View.VISIBLE);
            noServicesTextView.setVisibility(View.GONE);
            HotelServicesAdapter adapter = new HotelServicesAdapter(hotels);
            servicesRecyclerView.setAdapter(adapter);
        }else {
            hotelInterface = this;
            presenter = new HotelPresenter(hotelInterface, new HotelRepositoryImp());
            presenter.getHotels(branchId);
            progressBar.show();
        }
        addServiceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addService();
            }
        });
        return view;
    }

    private void addService() {
        final LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.hotel_service_popup, null);
        float density = getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(layout, (int) density * 300, ViewPager.LayoutParams.WRAP_CONTENT, true);
        final Spinner roomTypeSpinner = layout.findViewById(R.id.roomTypeSpinner);
        final EditText priceET= layout.findViewById(R.id.priceET);
        final ImageView wifiImageView = layout.findViewById(R.id.wifiImageView);
        final ImageView gymImageView = layout.findViewById(R.id.gymImageView);
        final ImageView swimmingImageView = layout.findViewById(R.id.swimmingImageView);
        final ImageView mealsImageView = layout.findViewById(R.id.mealsImageView);
        final ImageView parkingImageView = layout.findViewById(R.id.parkingImageView);
        final Button addServiceButton = layout.findViewById(R.id.addServiceButton);
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isOnline(App.getContext())) {
                    if (priceET.getText().toString().equals("")){
                        Toast.makeText(App.getContext(), "من فضلك ادخل السعر", Toast.LENGTH_LONG).show();
                    }else {
                        Hotel hotel = new Hotel();
                        hotel.setBranchID(branchId);
                        hotel.setRoomType(roomTypeSpinner.getSelectedItem().toString());
                        hotel.setPrice(Double.valueOf(priceET.getText().toString()));
                        if (gym) {
                            hotel.setGym(true);
                        }
                        if (wifi) {
                            hotel.setWifi(true);
                        }
                        if (parking) {
                            hotel.setParking(true);
                        }
                        if (pools) {
                            hotel.setBool(true);
                        }
                        if (meals) {
                            hotel.setMeals(true);
                        }
                        String user = PrefUtils.getKeys(App.getContext(), getString(R.string.user));
                        Gson gson = new Gson();
                        User user1 = gson.fromJson(user, User.class);
                        hotel.setCreateUser(user1.getID());
                        presenter.setHotel(hotel);
                        progressBar.show();
                        pw.dismiss();
                    }

                }
            }
        });
        final String[] rooms = {"غرفة فردية", "غرفة مزدوجة", "غرفة ثلاثية"};
        wifiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wifi){
                    wifi= false;
                    wifiImageView.setImageResource(R.mipmap.wifi_light);
                }else {
                    wifi = true;
                    wifiImageView.setImageResource(R.mipmap.wifi_signal);
                }
            }
        });
        gymImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gym){
                    gym= false;
                    gymImageView.setImageResource(R.mipmap.weights_light);
                }else {
                    gym = true;
                    gymImageView.setImageResource(R.mipmap.weights);
                }
            }
        });
        swimmingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pools){
                    pools= false;
                    swimmingImageView.setImageResource(R.mipmap.swimming_light);
                }else {
                    pools = true;
                    swimmingImageView.setImageResource(R.mipmap.swimming);
                }
            }
        });
        mealsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (meals){
                    meals= false;
                    mealsImageView.setImageResource(R.mipmap.meal_light);
                }else {
                    meals = true;
                    mealsImageView.setImageResource(R.mipmap.meal);
                }
            }
        });
        parkingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parking){
                    parking= false;
                    parkingImageView.setImageResource(R.mipmap.parking_light);
                }else {
                    parking = true;
                    parkingImageView.setImageResource(R.mipmap.parking);
                }
            }
        });
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, rooms);
        roomTypeSpinner.setAdapter(spinnerArrayAdapter);
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
        try {
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void hotel(Hotel hotel) {
        progressBar.dismiss();
        if (hotel!=null){
            Toast.makeText(App.getContext(), "تم اضافة الخدمة بنجاح", Toast.LENGTH_LONG).show();
            if (this.hotels!=null){
                this.hotels.add(hotel);
                adapter.notifyDataSetChanged();
            }else {
                this.hotels = new ArrayList<>();
                hotels.add(hotel);
                adapter = new HotelServicesAdapter(hotels);
                servicesRecyclerView.setAdapter(adapter);
                servicesRecyclerView.setVisibility(View.VISIBLE);
                noServicesTextView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void message(Boolean response) {

    }

    @Override
    public void hotels(List<Hotel> hotels) {
        progressBar.dismiss();
        if (hotels!=null && hotels.size()>0){
            this.hotels = hotels;
            servicesRecyclerView.setVisibility(View.VISIBLE);
            noServicesTextView.setVisibility(View.GONE);
            adapter = new HotelServicesAdapter(hotels);
            servicesRecyclerView.setAdapter(adapter);
        }else {
            servicesRecyclerView.setVisibility(View.GONE);
            noServicesTextView.setVisibility(View.VISIBLE);
            noServicesTextView.setText("لا يوجد خدمات لعرضها");
        }
    }
}
