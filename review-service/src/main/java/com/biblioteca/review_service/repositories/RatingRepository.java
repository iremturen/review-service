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
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookId", rating.getBookId());
        params.addValue("userId", rating.getUserId());
        params.addValue("rating", rating.getRating());
        params.addValue("createdAt", rating.getCreatedAt());
        params.addValue("updatedAt", rating.getUpdatedAt());
    
        String selectSql = "SELECT ID FROM RATING WHERE BOOKID = :bookId AND USERID = :userId";
    
        try {
            Integer existingRatingId = jdbcTemplateNamed.queryForObject(selectSql, params, Integer.class);
    
            String updateSql = "UPDATE RATING SET RATING = :rating, UPDATEDAT = :updatedAt WHERE ID = :id";
            params.addValue("id", existingRatingId);
    
            jdbcTemplateNamed.update(updateSql, params);
        } catch (DataAccessException e) {
            String insertSql = "INSERT INTO RATING (BOOKID, USERID, RATING, CREATEDAT, UPDATEDAT) " +
                               "VALUES (:bookId, :userId, :rating, :createdAt, :updatedAt)";
            jdbcTemplateNamed.update(insertSql, params);
        }
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


}
