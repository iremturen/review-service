package com.biblioteca.review_service.repositories;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.biblioteca.review_service.mappers.ReviewMapper;
import com.biblioteca.review_service.models.Review;
import com.biblioteca.review_service.repositories.interfaces.IReviewRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ReviewRepository implements IReviewRepository {

    private NamedParameterJdbcTemplate jdbcTemplateNamed;
    private ReviewMapper reviewMapper;

    @Override
    public List<Review> getAllReviews(Integer bookId) {
        String sql = "SELECT * FROM reviews WHERE bookId = :bookId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("bookId", bookId);
        return jdbcTemplateNamed.query(sql, mapParams, reviewMapper);
    }

    @Override
    public List<Review> getUserReviews(Integer userId) {
        String sql = "SELECT * FROM reviews WHERE userid = :userId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("userId", userId);
        return jdbcTemplateNamed.query(sql, mapParams, reviewMapper);
    }

    @Override
    public Integer getReviewCount(Integer bookId) {
        String sql = "SELECT COUNT(*) FROM reviews WHERE bookId = :bookId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("bookId", bookId);
        return jdbcTemplateNamed.queryForObject(sql, mapParams, Integer.class);
    }

    @Override
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (bookId, userId, review, createdAt, updatedAt) VALUES (:bookId, :userId, :review, :createdAt, :updatedAt)";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("bookId", review.getBookId());
        mapParams.addValue("userId", review.getUserId());
        mapParams.addValue("review", review.getReview());
        mapParams.addValue("createdAt", review.getCreatedAt());
        mapParams.addValue("updatedAt", review.getUpdatedAt());
        jdbcTemplateNamed.update(sql, mapParams);
    }

    @Override
    public void removeReview(Integer reviewId) {
        String sql = "DELETE FROM reviews WHERE id = :reviewId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("reviewId", reviewId);
        jdbcTemplateNamed.update(sql, mapParams);
    }

    @Override
    public void updateReview(Review review) {
        String sql = "UPDATE reviews SET review = :review, updatedAt = :updatedAt WHERE id = :reviewId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("review", review.getReview());
        mapParams.addValue("updatedAt", review.getUpdatedAt());
        mapParams.addValue("reviewId", review.getId());
        jdbcTemplateNamed.update(sql, mapParams);
    }
}
