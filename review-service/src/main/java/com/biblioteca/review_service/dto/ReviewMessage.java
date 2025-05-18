package com.biblioteca.review_service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ReviewMessage {
    private String actionType;
    private Integer bookId;
    private Integer userId;
    private String review;
    private Date createdAt;
    private Date updatedAt;
}
