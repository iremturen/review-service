package com.biblioteca.review_service.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.biblioteca.review_service.models.Rating;

@Component
public class RatingMapper implements RowMapper<Rating> {
    @Override
    @NonNull
    public Rating mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new Rating(
        rs.getInt("id"),
        rs.getInt("bookId"),
        rs.getInt("userId"),
        rs.getInt("rating"),
        rs.getDate("createdAt"),
        rs.getDate("updatedAt")
        );
    }
}
