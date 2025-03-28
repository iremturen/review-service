package com.biblioteca.review_service.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.biblioteca.review_service.models.Review;
import com.biblioteca.review_service.repositories.interfaces.IReviewRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ReviewRepository implements IReviewRepository {

    @Override
    public List<Review> getAllReviews(Integer bookId) {
        return null;
    }

    @Override
    public List<Review> getUserReviews(Integer userId) {
        return null;
    }

    @Override
    public Integer getReviewCount(Integer bookId) {
        return 0;
    }

    @Override
    public void addReview(Review review) {
        return;
    }

    @Override
    public void removeReview(Integer reviewId) {
        return;
    }
}
