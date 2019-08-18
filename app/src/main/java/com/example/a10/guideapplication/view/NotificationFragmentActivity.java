package com.example.a10.guideapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Offer;
import com.example.a10.guideapplication.presenter.OffersPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.OffersRepositoryImp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationFragmentActivity extends Fragment implements OffersListener {
    @BindView(R.id.toolbar_back)
    Button back;
    @BindView(R.id.notificationRecyclerView)
    android.support.v7.widget.RecyclerView notificationRecyclerView;
    @BindView(R.id.NoNotificationImage)
    ImageView noNotificationImage;

    OffersListener Interface;
    OffersPresenter presenter;
    NotificationAdapter adapter;
    List<Offer> offersList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        noNotificationImage.setVisibility(View.VISIBLE);

        Interface = this;
        presenter = new OffersPresenter(Interface, new OffersRepositoryImp());

        if (offersList != null && offersList.size() > 0) {
            notificationRecyclerView.setVisibility(View.VISIBLE);
            noNotificationImage.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(App.getContext());
            notificationRecyclerView.setLayoutManager(layoutManager);
            adapter = new NotificationAdapter(offersList);
        }else{
            presenter.getOffers();
        }

        return view;
    }

    @Override
    public void sectionOffers(List<Offer> offers) {

    }

    @Override
    public void allOffers(List<Offer> offers) {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentTime = localCalendar.getTime();
        if(offers != null && offers.size() > 0) {
            for (Offer offer : offers) {
                if(offer != null && offer.getMEndDate() != null && (offer.getMEndDate().after(currentTime)))
                    offersList.add(offer);
            }

            if(offersList != null && offersList.size() > 0) {
                notificationRecyclerView.setVisibility(View.VISIBLE);
                noNotificationImage.setVisibility(View.GONE);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(App.getContext());
                notificationRecyclerView.setLayoutManager(layoutManager);
                adapter = new NotificationAdapter(offersList);
            }
        }
    }

    @Override
    public void offerAdded(Offer offer) {

    }

    @Override
    public void offerUpdated(Boolean b) {

    }

    @Override
    public void offerDeleted(Boolean b) {

    }
}
