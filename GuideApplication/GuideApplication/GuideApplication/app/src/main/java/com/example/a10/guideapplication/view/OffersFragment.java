package com.example.a10.guideapplication.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Offer;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.presenter.OffersPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.OffersRepositoryImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersFragment extends Fragment implements OffersListener{

    @BindView(R.id.menuRecyclerView)
    RecyclerView offersRecyclerView;
    @BindView(R.id.noMenuTextView)
    TextView noMenuTextView;
    OffersListener listener;
    OffersPresenter presenter;
    List<Offer> offers;
    private ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        ButterKnife.bind(this, view);
        listener = this;
        presenter = new OffersPresenter(listener, new OffersRepositoryImp());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.getContext());
        offersRecyclerView.setLayoutManager(manager);
        if (offers!=null){
            offersRecyclerView.setVisibility(View.VISIBLE);
            noMenuTextView.setVisibility(View.GONE);
            OffersAdapter adapter = new OffersAdapter(offers);
            offersRecyclerView.setAdapter(adapter);
        }else {
            progressBar = new ProgressDialog(App.getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("جارى التحميل ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();
            String placeType = getArguments().getString(getString(R.string.placeType));
            if (placeType.equals(getString(R.string.doctor))){
                Doctor doctor = getArguments().getParcelable(getString(R.string.place));
                presenter.getSectionOffers(doctor.getID());
            }else {
                Section section = getArguments().getParcelable(getString(R.string.place));
                presenter.getSectionOffers(section.getID());
            }
        }
        return view;
    }

    @Override
    public void sectionOffers(List<Offer> offers) {
        progressBar.dismiss();
        this.offers = offers;
        if (offers!=null && offers.size()!=0){
            offersRecyclerView.setVisibility(View.VISIBLE);
            noMenuTextView.setVisibility(View.GONE);
            OffersAdapter adapter = new OffersAdapter(offers);
            offersRecyclerView.setAdapter(adapter);
        }else {
            offersRecyclerView.setVisibility(View.GONE);
            noMenuTextView.setVisibility(View.VISIBLE);
            noMenuTextView.setText("لا يوجد عروض");
        }
    }

    @Override
    public void allOffers(List<Offer> offers) {

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
