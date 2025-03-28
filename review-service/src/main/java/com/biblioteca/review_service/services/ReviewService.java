package com.biblioteca.review_service.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.biblioteca.review_service.models.Review;
import com.biblioteca.review_service.repositories.interfaces.IReviewRepository;
import com.biblioteca.review_service.services.interfaces.IReviewService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Validated
public class ReviewService implements IReviewService {

    private IReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews(Integer bookId) {
        return reviewRepository.getAllReviews(bookId);
    }

    @Override
    public List<Review> getUserReviews(Integer userId) {
        return reviewRepository.getUserReviews(userId);
    }

    @Override
    public Integer getReviewCount(Integer bookId) {
        return reviewRepository.getReviewCount(bookId);
    }

    @Override
    public void addReview(Review review) {
        reviewRepository.addReview(review);
    }

    @Override
    public void removeReview(Integer reviewId) {
        reviewRepository.removeReview(reviewId);
    }
}