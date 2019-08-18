package com.example.a10.guideapplication.view;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Branch;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.BranchPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.BranchRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchesFragment extends Fragment implements BranchInterface{

    @BindView(R.id.branchesRecyclerView)
    RecyclerView branchesRecyclerView;
    @BindView(R.id.noBranchesTextView)
    TextView noBranchesTextView;
    BranchInterface branchListener;
    BranchPresenter presenter;
    String placeImage;
    String placeType;
    Section section;
    Doctor doctor;
    boolean userCanEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branches_fragment, container, false);
        ButterKnife.bind(this, view);

        branchListener = this;
        presenter = new BranchPresenter(branchListener, new BranchRepositoryImp());

        userCanEdit = getArguments().getBoolean("UserCanEdit");

        placeType = getArguments().getString(getString(R.string.placeType));
        if (placeType.equals(getString(R.string.doctor))){
            doctor = getArguments().getParcelable(getString(R.string.place));
            placeImage = doctor.getImage();
            presenter.getBranches(doctor.getID(), 2);
        }else {
            section = getArguments().getParcelable(getString(R.string.place));
            placeImage = section.getImage();
            presenter.getBranches(section.getID(), 1);
        }
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.getContext());
        branchesRecyclerView.setLayoutManager(manager);
        return view;
    }

    @Override
    public void branches(List<Branch> branches) {
        if (branches!=null){
            BranchesAdapter adapter;
            if(!userCanEdit)
                adapter = new BranchesAdapter(branches, placeImage, placeType, null);
            else
                adapter = new BranchesAdapter(branches, placeImage, placeType, presenter);

            branchesRecyclerView.setAdapter(adapter);
            branchesRecyclerView.setVisibility(View.VISIBLE);
            noBranchesTextView.setVisibility(View.GONE);
        }else {
            branchesRecyclerView.setVisibility(View.GONE);
            noBranchesTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void branch(Branch branch) {

    }

    @Override
    public void message(Boolean response) {
        if(response == true) {
            Toast.makeText(App.getContext(), "لقد تم حذف البيانات بنجاح", Toast.LENGTH_LONG).show();
            /*Intent intent = new Intent(App.getContext(), PlaceProfileActivity.class);
            intent.putExtra(App.getContext().getString(R.string.placeType), placeType);
            if(section == null) intent.putExtra(App.getContext().getString(R.string.place), doctor);
            else intent.putExtra(App.getContext().getString(R.string.place), section);
            startActivity(intent);
            ((PlaceProfileActivity)getActivity()).finish();*/
        }else{
            Toast.makeText(App.getContext(), "برجاء المحاولة فى وقت آخر", Toast.LENGTH_LONG).show();
        }
    }


}
