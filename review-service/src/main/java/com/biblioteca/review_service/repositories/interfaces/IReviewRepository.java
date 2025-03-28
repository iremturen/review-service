package com.biblioteca.review_service.repositories.interfaces;

import java.util.List;

import com.biblioteca.review_service.models.Review;

public interface IReviewRepository {
    List<Review> getAllReviews(Integer bookId);
    List<Review> getUserReviews(Integer userId);
    Integer getReviewCount(Integer bookId);
    void addReview(Review review);
    void removeReview(Integer reviewId);
}
