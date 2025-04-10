package com.biblioteca.review_service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class RatingMessage {
    private String actionType;
    private Integer bookId;
    private Integer userId;
    private Integer rating;
    private Date createdAt;
    private Date updatedAt;
}
