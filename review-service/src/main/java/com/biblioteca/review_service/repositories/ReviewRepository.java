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
        String checkSql = "SELECT COUNT(*) FROM reviews WHERE bookId = :bookId AND userId = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookId", review.getBookId())
                .addValue("userId", review.getUserId());

        Integer count = jdbcTemplateNamed.queryForObject(checkSql, params, Integer.class);

        if (count != null && count > 0) {
            String updateSql = "UPDATE reviews SET review = :review, updatedAt = :updatedAt " +
                    "WHERE bookId = :bookId AND userId = :userId";
            params.addValue("review", review.getReview());
            params.addValue("updatedAt", review.getUpdatedAt());
            jdbcTemplateNamed.update(updateSql, params);
        } else {
            String insertSql = "INSERT INTO reviews (bookId, userId, review, createdAt, updatedAt) " +
                    "VALUES (:bookId, :userId, :review, :createdAt, :updatedAt)";
            params.addValue("review", review.getReview());
            params.addValue("createdAt", review.getCreatedAt());
            params.addValue("updatedAt", review.getUpdatedAt());
            jdbcTemplateNamed.update(insertSql, params);
        }
    }

    @Override
    public void removeReview(Integer reviewId) {
        String sql = "DELETE FROM reviews WHERE id = :reviewId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("reviewId", reviewId);
        jdbcTemplateNamed.update(sql, mapParams);
    }

}
