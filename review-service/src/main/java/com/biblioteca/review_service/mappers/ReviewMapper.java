package com.biblioteca.review_service.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.biblioteca.review_service.models.Review;

@Component
public class ReviewMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new Review(
            rs.getInt("id"),
            rs.getInt("bookId"),
            rs.getInt("userId"),
            rs.getString("review"),
            rs.getTimestamp("createdAt"),
            rs.getTimestamp("updatedAt")
        );
    }
}