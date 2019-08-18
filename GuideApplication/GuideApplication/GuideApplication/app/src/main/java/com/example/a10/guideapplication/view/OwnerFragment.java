package com.example.a10.guideapplication.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.OwnerPresenter;
import com.example.a10.guideapplication.presenter.UserPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.OwnerRepositoryImp;
import com.example.a10.guideapplication.repository.repositoryImp.UserRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnerFragment extends Fragment implements OwnerInterface, UserInterface {
    @BindView(R.id.toolbar_back)
    Button back;
    @BindView(R.id.ownerText)
    TextView ownerText;
    @BindView(R.id.ownerRecyclerView)
    RecyclerView ownerRecyclerView;
    @BindView(R.id.ownerNoImage)
    ImageView ownerNoImage;
    @BindView(R.id.addPlace)
    FloatingActionButton addPlace;

    private OwnerInterface Interface;
    private OwnerPresenter presenter;
    private UserInterface userInterface;
    private UserPresenter userPresenter;
    private ProgressDialog progressBar;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Section> sections = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Context context;
    private User user;
    private String

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, view);
        context = App.getContext();

        progressBar = new ProgressDialog(context);
        progressBar.setMessage("جارى تحميل البيانات...");
        progressBar.setCanceledOnTouchOutside(true);
        progressBar.show();

        final Gson gson = new Gson();
        user = gson.fromJson(PrefUtils.getKeys(App.getContext(), getString(R.string.user)), User.class);

        init();

        if(user != null && user.getType() == 1){
            if(users == null){
                userInterface = this;
                userPresenter = new UserPresenter(userInterface, new UserRepositoryImp());
                userPresenter.getOwners();
            }else{
                ownerNoImage.setVisibility(View.GONE);
                ownerRecyclerView.setVisibility(View.VISIBLE);
                ownerRecyclerView.setAdapter(new OwnersAdapter(users));
            }
        }else {
            if (user != null && user.getCategory().equals(getString(R.string.doctor))){
                if(doctors == null) {
                    Interface = this;
                    presenter = new OwnerPresenter(Interface, new OwnerRepositoryImp());
                    presenter.getDoctors(user.getPlaces());
                }else{
                    ownerNoImage.setVisibility(View.GONE);
                    ownerRecyclerView.setVisibility(View.VISIBLE);
                    ownerRecyclerView.setAdapter(new OwnerAdapter(context, doctors, null));
                }
            }else {
                if(sections == null) {
                    Interface = this;
                    presenter = new OwnerPresenter(Interface, new OwnerRepositoryImp());
                    presenter.getSections(user.getPlaces(), user.getCategory());
                }else{
                    ownerNoImage.setVisibility(View.GONE);
                    ownerRecyclerView.setVisibility(View.VISIBLE);
                    ownerRecyclerView.setAdapter(new OwnerAdapter(context, null, sections));
                }
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null & user.getType() == 2) {
                    Intent intent = new Intent(context, EditPlaceActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(getString(R.string.placeType), user.getCategory());
                    mBundle.putParcelable("place", null);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(context, RegistrationActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void init(){
        if(user != null && user.getType() == 1){
            ownerText.setText("المستخدمين المسموح لهم بالتعديل");
        }
        else if(user != null && user.getCategory().equals(getString(R.string.doctor))){
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
            if(doctors != null){
                ownerNoImage.setVisibility(View.GONE);
                ownerRecyclerView.setVisibility(View.VISIBLE);
                ownerRecyclerView.setAdapter(new OwnerAdapter(context, doctors, null));
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
            if(sections != null){
                ownerNoImage.setVisibility(View.GONE);
                ownerRecyclerView.setVisibility(View.VISIBLE);
                ownerRecyclerView.setAdapter(new OwnerAdapter(context, null, sections));
            }else{
                ownerNoImage.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void user(User user) {

    }

    @Override
    public void users(List<User> users) {
        this.users = users;
        if(users!= null){
            ownerNoImage.setVisibility(View.GONE);
            ownerRecyclerView.setVisibility(View.VISIBLE);
            ownerRecyclerView.setAdapter(new OwnersAdapter(users));
        }else{
            ownerNoImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void message(Boolean response) {

    }
}
