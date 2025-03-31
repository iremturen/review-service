package com.biblioteca.review_service.dto;


import com.biblioteca.review_service.models.Rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingMessage {
    private String actionType;
    private Rating rating;
  
}
