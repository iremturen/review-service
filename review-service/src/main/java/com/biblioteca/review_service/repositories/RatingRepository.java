package com.biblioteca.review_service.repositories;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;

import com.biblioteca.review_service.mappers.RatingMapper;
import com.biblioteca.review_service.models.Rating;
import com.biblioteca.review_service.repositories.interfaces.IRatingRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class RatingRepository implements IRatingRepository{

    private NamedParameterJdbcTemplate jdbcTemplateNamed;
    private RatingMapper ratingMapper;

    @Override
    public Double getAverageRating(Integer bookId) {
        String sql = "SELECT COALESCE(ROUND(AVG(RATING), 1), 0.0) AS RATING FROM RATING WHERE BOOKID= :bookId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("bookId", bookId);
        try {
            return jdbcTemplateNamed.queryForObject(sql, mapParams, Double.class);
        } catch (DataAccessException e) {
                    return 0.0;
        }
    }

    @Override
    public List<Rating> getAllRatings(Integer bookId) {
        String sql = "SELECT * FROM RATING WHERE BOOKID= :bookId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("bookId", bookId);
        return jdbcTemplateNamed.query(sql, mapParams, ratingMapper);
    }

    @Override
    public List<Rating> getUserRatings(Integer userId) {
        String sql = "SELECT * FROM RATING WHERE USERID= :userId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("userId", userId);
        return jdbcTemplateNamed.query(sql, mapParams, ratingMapper);
    }

    @Override
    public void addRating(Rating rating) {
        String sql = "INSERT INTO RATING (BOOKID, USERID, RATING, CREATEDAT, UPDATEDAT) VALUES (:bookId, :userId, :rating, :createdAt, :updatedAt)";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("bookId", rating.getBookId());
        mapParams.addValue("userId", rating.getUserId());
        mapParams.addValue("rating", rating.getRating());
        mapParams.addValue("createdAt", rating.getCreatedAt());
        mapParams.addValue("updatedAt", rating.getUpdatedAt());
        jdbcTemplateNamed.update(sql, mapParams);
    }

    @Override
    public void removeRating(Integer ratingId) {
        String sql = "DELETE FROM RATING WHERE ID= :ratingId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("ratingId", ratingId);
        jdbcTemplateNamed.update(sql, mapParams);
    }

    @Override
    public Integer getRatingCount(Integer bookId) {
        String sql = "SELECT COUNT(*) FROM RATING WHERE BOOKID= :bookId";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("bookId", bookId);
        return jdbcTemplateNamed.queryForObject(sql, mapParams, Integer.class);
    }

    @Override
    public void updateRating(Rating rating) {
        String sql = "UPDATE RATING SET RATING = :rating, UPDATEDAT = :updatedAt WHERE ID = :id";
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("rating", rating.getRating());
        mapParams.addValue("updatedAt", rating.getUpdatedAt());
        mapParams.addValue("id", rating.getId());
        jdbcTemplateNamed.update(sql, mapParams);
    }

}
