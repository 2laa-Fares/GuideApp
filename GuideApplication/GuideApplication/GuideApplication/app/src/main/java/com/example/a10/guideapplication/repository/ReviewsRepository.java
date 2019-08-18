package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.Review;
import com.example.a10.guideapplication.view.ReviewsInterface;

public interface ReviewsRepository {

    public  void addReview(Review review, ReviewsInterface reviewsInterface);
    public void getReview(int sectionId, int type, ReviewsInterface reviewsInterface);
}
