package com.biblioteca.review_service.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.biblioteca.review_service.models.Rating;
import com.biblioteca.review_service.repositories.interfaces.IRatingRepository;
import com.biblioteca.review_service.services.interfaces.IRatingService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Validated
public class RatingService implements IRatingService{

    private IRatingRepository ratingRepository;

    @Override
    public Double getAverageRating(Integer bookId) {
        return ratingRepository.getAverageRating(bookId);
    }

    @Override
    public List<Rating> getAllRatings(Integer bookId) {
        return ratingRepository.getAllRatings(bookId);
    }

    @Override
    public Integer getRatingCount(Integer bookId) {
        return ratingRepository.getRatingCount(bookId);
    }

    @Override
    public List<Rating> getUserRatings(Integer userId) {
        return ratingRepository.getUserRatings(userId);
    }

    @Override
    public void addRating(Rating rating) {
        ratingRepository.addRating(rating);
    }

    @Override
    public void removeRating(Integer ratingId) {
        ratingRepository.removeRating(ratingId);
    }

    @Override
    public void updateRating(Rating rating) {
        ratingRepository.updateRating(rating);
    }
}
