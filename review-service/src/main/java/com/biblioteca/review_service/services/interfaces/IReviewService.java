package com.biblioteca.review_service.services.interfaces;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.biblioteca.review_service.models.Review;

@Validated
public interface IReviewService {
    List<Review> getAllReviews(Integer bookId);
    List<Review> getUserReviews(Integer userId);
    Integer getReviewCount(Integer bookId);
    void addReview(Review review);
    void removeReview(Integer reviewId);
    void updateReview(Review review);
}
