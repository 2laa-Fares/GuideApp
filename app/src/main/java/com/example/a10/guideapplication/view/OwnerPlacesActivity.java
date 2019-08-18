package com.example.a10.guideapplication.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.OwnerPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.OwnerRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnerPlacesActivity extends AppCompatActivity implements OwnerInterface{
    @BindView(R.id.toolbar_back)
    Button back;
    @BindView(R.id.ownerText)
    TextView ownerText;
    @BindView(R.id.ownerRecyclerView)
    RecyclerView ownerRecyclerView;
    @BindView(R.id.ownerNoImage)
    ImageView ownerNoImage;

    private OwnerInterface Interface;
    private OwnerPresenter presenter;
    private ProgressDialog progressBar;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Section> sections = new ArrayList<>();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_places);
        ButterKnife.bind(this);
        App.setContext(this);

        user = (User) getIntent().getSerializableExtra(getString(R.string.place));

        init();

        if (user != null && user.getCategory().equals(getString(R.string.doctor))){
            if(doctors != null && doctors.size() > 0) {
                ownerNoImage.setVisibility(View.GONE);
                ownerRecyclerView.setVisibility(View.VISIBLE);
                ownerRecyclerView.setAdapter(new OwnerAdapter(this, doctors, null));
            }else{
                Interface = this;
                presenter = new OwnerPresenter(Interface, new OwnerRepositoryImp());
                presenter.getDoctors(user.getPlaces());
                progressBar = new ProgressDialog(this);
                progressBar.setMessage("جارى تحميل البيانات...");
                progressBar.setCanceledOnTouchOutside(true);
                progressBar.show();
            }
        }else {
            if(user != null && sections != null && sections.size() > 0) {
                ownerNoImage.setVisibility(View.GONE);
                ownerRecyclerView.setVisibility(View.VISIBLE);
                ownerRecyclerView.setAdapter(new OwnerAdapter(this, null, sections));
            }else{
                Interface = this;
                presenter = new OwnerPresenter(Interface, new OwnerRepositoryImp());
                presenter.getSections(user.getPlaces(), user.getCategory());
                progressBar = new ProgressDialog(this);
                progressBar.setMessage("جارى تحميل البيانات...");
                progressBar.setCanceledOnTouchOutside(true);
                progressBar.show();
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnerPlacesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        if(user != null && user.getCategory().equals(getString(R.string.doctor))){
            ownerText.setText("الأطباء");
            ownerNoImage.setImageResource(R.drawable.empty_doctor);
        }else{
            if(user != null && user.getCategory().equals(getString(R.string.hotel))){
                ownerText.setText("الفنادق");
                ownerNoImage.setImageResource(R.drawable.empty_hotel);
            }else{
                if(user != null && user.getCategory().equals(getString(R.string.store))){
                    ownerText.setText("المتاجر");
                    ownerNoImage.setImageResource(R.drawable.empty_shop);
                }else{
                    if(user != null && user.getCategory().equals(getString(R.string.coffee))){
                        ownerText.setText("المطاعم والمقاهي");
                        ownerNoImage.setImageResource(R.drawable.empty_coffee);
                    }
                }
            }
        }
    }

    @Override
    public void doctors(List<Doctor> doctors) {
        this.doctors = doctors;
        if(user != null && user.getCategory().equals(getString(R.string.doctor))){
            progressBar.dismiss();
            if(doctors != null && doctors.size()>0){
                ownerNoImage.setVisibility(View.GONE);
                ownerRecyclerView.setVisibility(View.VISIBLE);
                ownerRecyclerView.setAdapter(new OwnerAdapter(this, doctors, null));
            }else{
                ownerNoImage.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void places(List<Section> sections) {
        this.sections = sections;
        if(user != null && !user.getCategory().equals(getString(R.string.doctor))){
            progressBar.dismiss();
            if(sections != null && sections.size() > 0){
                ownerNoImage.setVisibility(View.GONE);
                ownerRecyclerView.setVisibility(View.VISIBLE);
                ownerRecyclerView.setAdapter(new OwnerAdapter(this, null, sections));
            }else{
                ownerNoImage.setVisibility(View.VISIBLE);
            }
        }
    }
}
