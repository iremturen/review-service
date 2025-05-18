package com.biblioteca.review_service.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.biblioteca.review_service.dto.ReviewMessage;
import com.biblioteca.review_service.models.Review;
import com.biblioteca.review_service.services.interfaces.IReviewService;

import lombok.AllArgsConstructor;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReviewConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ReviewConsumer.class);
    private final IReviewService reviewService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "review-topic", groupId = "review-group", containerFactory = "ratingKafkaListenerContainerFactory")
    public void consumeReviewEvent(ReviewMessage message) {
        try {
            if (message.getActionType() == null) {
                throw new IllegalArgumentException("Action type cannot be null");
            }

            Review review = new Review(
                    null,
                    message.getBookId(),
                    message.getUserId(),
                    message.getReview(),
                    message.getCreatedAt(),
                    message.getUpdatedAt());

            switch (message.getActionType()) {
                case "ADD":
                    reviewService.addReview(review);
                    logger.info("Added review: {}", review);
                    break;

                case "DELETE":
                    reviewService.removeReview(review.getId());
                    logger.info("Deleted review with ID: {}", review.getId());
                    break;


                case "GET_ALL":
                    Integer bookId = message.getBookId();
                    List<Review> reviews = reviewService.getAllReviews(bookId);
                    logger.info("Retrieved all reviews for book ID: {}", bookId);
                
                    Map<String, Object> allResponse = new HashMap<>();
                    allResponse.put("type", "GET_ALL");
                    allResponse.put("bookId", bookId);
                    allResponse.put("reviews", reviews);
                
                    kafkaTemplate.send("review-result-topic", objectMapper.writeValueAsString(allResponse));
                    break;
                

                case "GET_COUNT":
                    reviewService.getReviewCount(review.getBookId());
                    logger.info("Retrieved reviews for book ID: {}", review.getBookId());
                    break;

                case "GET_USER":
                    reviewService.getUserReviews(review.getUserId());
                    logger.info("Retrieved reviews for user ID: {}", review.getUserId());
                    break;

                default:
                    throw new IllegalArgumentException("Invalid action type: " + message.getActionType());
            }
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
