package com.biblioteca.review_service.repositories.interfaces;

import java.util.List;

import com.biblioteca.review_service.models.Rating;

public interface IRatingRepository {
    Double getAverageRating(Integer bookId);
    List<Rating> getAllRatings(Integer bookId);
    List<Rating> getUserRatings(Integer userId);
    Integer getRatingCount(Integer bookId, Integer userId);
    void addRating(Rating rating);
    void removeRating(Integer ratingId);
}
