package com.biblioteca.review_service.services.interfaces;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.biblioteca.review_service.models.Rating;

@Validated
public interface    IRatingService {
    Double getAverageRating(Integer bookId);
    List<Rating> getAllRatings(Integer bookId);
    List<Rating> getUserRatings(Integer userId);
    Integer getRatingCount(Integer bookId, Integer userId);
    void addRating(Rating rating);
    void removeRating(Integer ratingId);
}
