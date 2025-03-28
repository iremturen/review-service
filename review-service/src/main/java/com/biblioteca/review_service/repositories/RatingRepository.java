package com.biblioteca.review_service.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.biblioteca.review_service.models.Rating;
import com.biblioteca.review_service.repositories.interfaces.IRatingRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class RatingRepository implements IRatingRepository{

    @Override
    public Double getAverageRating(Integer bookId) {
        return 0.0;
    }

    @Override
    public List<Rating> getAllRatings(Integer bookId) {
        return null;
    }

    @Override
    public Integer getRatingCount(Integer bookId, Integer userId) {
        return 0;
    }

    @Override
    public List<Rating> getUserRatings(Integer userId) {
        return null;
    }

    @Override
    public void addRating(Rating rating) {
        return;
    }

    @Override
    public void removeRating(Integer ratingId) {
        return;
    }
}
