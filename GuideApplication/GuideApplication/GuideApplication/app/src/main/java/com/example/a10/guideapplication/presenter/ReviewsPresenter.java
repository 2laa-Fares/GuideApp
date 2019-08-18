package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.Review;
import com.example.a10.guideapplication.model.ReviewWithOwner;
import com.example.a10.guideapplication.repository.ReviewsRepository;
import com.example.a10.guideapplication.view.ReviewsInterface;

import java.util.List;

public class ReviewsPresenter {

    ReviewsRepository repository;
    ReviewsInterface reviewsInterface;
    public ReviewsPresenter(ReviewsInterface reviewsInterface, ReviewsRepository repository){
        this.repository = repository;
        this.reviewsInterface = reviewsInterface;
    }

    public void getReviews(int sectionId, int type){
        ReviewsInterface reviewsInterface1 = new ReviewsInterface() {
            @Override
            public void review(Review review) {
            }

            @Override
            public void reviews(List<ReviewWithOwner> reviews) {
                reviewsInterface.reviews(reviews);
            }
        };
        repository.getReview(sectionId, type, reviewsInterface1);
    }

    public void addReview(Review review){
        ReviewsInterface reviewsInterface1 = new ReviewsInterface() {
            @Override
            public void review(Review review) {
                reviewsInterface.review(review);
            }

            @Override
            public void reviews(List<ReviewWithOwner> reviews) {

            }
        };
        repository.addReview(review, reviewsInterface1);
    }
}
