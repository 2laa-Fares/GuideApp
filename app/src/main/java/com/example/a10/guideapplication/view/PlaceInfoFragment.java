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
import android.widget.TextView;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Review;
import com.example.a10.guideapplication.model.ReviewWithOwner;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.presenter.ReviewsPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.ReviewsRepositoryImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceInfoFragment extends Fragment implements ReviewsInterface{

    @BindView(R.id.reviewsRecyclerView)
    RecyclerView reviewsRecyclerView;
    @BindView(R.id.textView9)
    TextView placeName;
    @BindView(R.id.descriptionTV)
    TextView descriptionTV;
    @BindView(R.id.noReviewsTextView)
    TextView noReviewsTextView;
    ReviewsInterface reviewsInterface;
    ReviewsPresenter presenter;
    List<ReviewWithOwner> reviews;
    int sectionId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_info_fragment, container, false);
        ButterKnife.bind(this, view);
        reviewsInterface = this;
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.getContext());
        reviewsRecyclerView.setLayoutManager(manager);
        presenter = new ReviewsPresenter(reviewsInterface, new ReviewsRepositoryImp());
        String placeType = getArguments().getString(getString(R.string.placeType));
        if (placeType.equals(getString(R.string.coffee))){
            Section section = getArguments().getParcelable(getString(R.string.place));
            if (section!=null){
                if (section.getDescription()!=null){
                    placeName.setVisibility(View.VISIBLE);
                    descriptionTV.setVisibility(View.VISIBLE);
                    placeName.setText("عن المطعم");
                    descriptionTV.setText(section.getDescription());
                }else {
                    placeName.setVisibility(View.GONE);
                    descriptionTV.setVisibility(View.GONE);
                }
                sectionId = section.getID();
            }
        }else if (placeType.equals(getString(R.string.hotel))){
            Section section = getArguments().getParcelable(getString(R.string.place));
            if (section!=null){
                if (section.getDescription()!=null){
                    placeName.setVisibility(View.VISIBLE);
                    descriptionTV.setVisibility(View.VISIBLE);
                    placeName.setText("عن الفندق");
                    descriptionTV.setText(section.getDescription());
                }else {
                    placeName.setVisibility(View.GONE);
                    descriptionTV.setVisibility(View.GONE);
                }
                sectionId = section.getID();
            }
        }else if (placeType.equals(getString(R.string.doctor))){
            Doctor doctor = getArguments().getParcelable(getString(R.string.place));
            if (doctor!=null){
                if (doctor.getSpecialization()!=null){
                    placeName.setVisibility(View.VISIBLE);
                    descriptionTV.setVisibility(View.VISIBLE);
                    placeName.setText("تخصص الطبيب");
                    descriptionTV.setText(doctor.getSpecialization());
                }else {
                    placeName.setVisibility(View.GONE);
                    descriptionTV.setVisibility(View.GONE);
                }
                sectionId = doctor.getID();
            }
        }else if (placeType.equals(getString(R.string.store))){
            Section section = getArguments().getParcelable(getString(R.string.place));
            if (section!=null){
                if (section.getDescription()!=null){
                    placeName.setVisibility(View.VISIBLE);
                    descriptionTV.setVisibility(View.VISIBLE);
                    placeName.setText("عن المتجر");
                    descriptionTV.setText(section.getDescription());
                }else {
                    placeName.setVisibility(View.GONE);
                    descriptionTV.setVisibility(View.GONE);
                }
                sectionId = section.getID();
            }
        }
        if (reviews!=null){
            ReviewsAdapter adapter = new ReviewsAdapter(reviews);
            reviewsRecyclerView.setAdapter(adapter);
            reviewsRecyclerView.setVisibility(View.VISIBLE);
            noReviewsTextView.setVisibility(View.GONE);
        }else if (placeType.equals(getString(R.string.doctor))){
            presenter.getReviews(sectionId, 2);
        }else {
            presenter.getReviews(sectionId, 1);
        }
        return view;
    }

    @Override
    public void review(Review review) {

    }

    @Override
    public void reviews(List<ReviewWithOwner> reviews) {
        this.reviews = reviews;
        if (reviews!=null){
            ReviewsAdapter adapter = new ReviewsAdapter(reviews);
            reviewsRecyclerView.setAdapter(adapter);
            reviewsRecyclerView.setVisibility(View.VISIBLE);
            noReviewsTextView.setVisibility(View.GONE);
        }else {
            reviewsRecyclerView.setVisibility(View.GONE);
            noReviewsTextView.setVisibility(View.VISIBLE);
        }
    }
}
