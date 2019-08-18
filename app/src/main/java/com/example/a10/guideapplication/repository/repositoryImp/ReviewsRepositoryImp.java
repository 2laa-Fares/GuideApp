package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.model.Review;
import com.example.a10.guideapplication.model.ReviewWithOwner;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.ReviewsRepository;
import com.example.a10.guideapplication.view.ReviewsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsRepositoryImp implements ReviewsRepository {
    ApiService apiService;
    public ReviewsRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }
    @Override
    public void addReview(final Review review, final ReviewsInterface reviewsInterface) {
        Call<Result<Review>> addReview = apiService.addReview(review);
        addReview.enqueue(new Callback<Result<Review>>() {
            @Override
            public void onResponse(Call<Result<Review>> call, Response<Result<Review>> response) {
                if (response.body() != null){
                    if (response.body().getData()!=null){
                        reviewsInterface.review(response.body().getData());
                    }else {
                        reviewsInterface.review(null);
                    }
                }else {
                    reviewsInterface.review(null);
                }
            }
            @Override
            public void onFailure(Call<Result<Review>> call, Throwable t) {
                reviewsInterface.review(null);
            }
        });
    }

    @Override
    public void getReview(int sectionId, int type, final ReviewsInterface reviewsInterface) {
        Call<Result<List<ReviewWithOwner>>> getReviews = apiService.getReviews(sectionId, type);
        getReviews.enqueue(new Callback<Result<List<ReviewWithOwner>>>() {
            @Override
            public void onResponse(Call<Result<List<ReviewWithOwner>>> call, Response<Result<List<ReviewWithOwner>>> response) {
                if (response.body()!=null){
                    if (response.body().getData()!=null){
                        reviewsInterface.reviews(response.body().getData());
                    }else {
                        reviewsInterface.reviews(null);
                    }
                }else {
                    reviewsInterface.reviews(null);
                }
            }

            @Override
            public void onFailure(Call<Result<List<ReviewWithOwner>>> call, Throwable t) {
                reviewsInterface.reviews(null);
            }
        });
    }
}
