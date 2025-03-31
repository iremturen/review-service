package com.biblioteca.review_service.dto;

import com.biblioteca.review_service.models.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewMessage {
    private String actionType;
    private Review review;
}
