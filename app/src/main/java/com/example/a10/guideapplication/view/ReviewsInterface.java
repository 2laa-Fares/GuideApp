package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.Review;
import com.example.a10.guideapplication.model.ReviewWithOwner;

import java.util.List;

public interface ReviewsInterface {
    void review(Review review);
    void reviews(List<ReviewWithOwner> reviews);
}
