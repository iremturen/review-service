package com.biblioteca.review_service.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.biblioteca.review_service.dto.ReviewMessage;
import com.biblioteca.review_service.services.interfaces.IReviewService;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class ReviewConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ReviewConsumer.class);
    private final IReviewService reviewService;

    @KafkaListener(topics = "review-topic", groupId = "review-group")
    public void consumeReviewEvent(ReviewMessage message) {
        try {
            if (message.getActionType() == null) {
                throw new IllegalArgumentException("Action type cannot be null");
            }

            switch (message.getActionType()) {
                case "ADD":
                    reviewService.addReview(message.getReview());
                    logger.info("Added review: {}", message.getReview());
                    break;

                case "DELETE":
                    reviewService.removeReview(message.getReview().getId());
                    logger.info("Deleted review with ID: {}", message.getReview().getId());
                    break;

                case "UPDATE":
                    reviewService.updateReview(message.getReview());
                    logger.info("Updated review: {}", message.getReview());
                    break;

                case "GET_ALL":
                    reviewService.getAllReviews(message.getReview().getBookId());
                    logger.info("Retrieved all reviews");
                    break;

                case "COUNT":
                    reviewService.getReviewCount(message.getReview().getBookId());
                    logger.info("Retrieved reviews for book ID: {}", message.getReview().getBookId());
                    break;

                case "GET_USER":
                    reviewService.getUserReviews(message.getReview().getUserId());
                    logger.info("Retrieved reviews for user ID: {}", message.getReview().getUserId());
                    break;

                default:
                    throw new IllegalArgumentException("Invalid action type: " + message.getActionType());
            }
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
