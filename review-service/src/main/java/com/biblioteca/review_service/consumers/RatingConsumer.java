package com.biblioteca.review_service.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biblioteca.review_service.dto.RatingMessage;
import com.biblioteca.review_service.services.interfaces.IRatingService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingConsumer {

    private final IRatingService ratingService;
    private static final Logger logger = LoggerFactory.getLogger(RatingConsumer.class);

    @KafkaListener(topics = "rating-topic", groupId = "rating-group")
    public void consumeRatingEvent(RatingMessage message) {
        try {
            if (message.getActionType() == null) {
                throw new IllegalArgumentException("Action type cannot be null");
            }

            switch (message.getActionType()) {
                case "ADD":
                    ratingService.addRating(message.getRating());
                    logger.info("Added rating: {}", message.getRating());
                    break;

                case "DELETE":
                    ratingService.removeRating(message.getRating().getId());
                    logger.info("Deleted rating with ID: {}", message.getRating().getId());
                    break;

                case "UPDATE":
                    ratingService.updateRating(message.getRating());
                    logger.info("Updated rating: {}", message.getRating());
                    break;

                case "GET_ALL":
                    ratingService.getAllRatings(message.getRating().getBookId());
                    logger.info("Retrieved all ratings for book ID: {}", message.getRating().getBookId());
                    break;

                case "GET_USER":
                    ratingService.getUserRatings(message.getRating().getUserId());
                    logger.info("Retrieved ratings for user ID: {}", message.getRating().getUserId());
                    break;

                case "GET_COUNT":
                    ratingService.getRatingCount(message.getRating().getBookId());
                    logger.info("Retrieved rating count for book ID: {}", message.getRating().getBookId());
                    break;

                case "GET_AVERAGE":
                    ratingService.getAverageRating(message.getRating().getBookId());
                    logger.info("Retrieved average rating for book ID: {}", message.getRating().getBookId());
                    break;

                default:
                    throw new IllegalArgumentException("Invalid action type: " + message.getActionType());
            }
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
