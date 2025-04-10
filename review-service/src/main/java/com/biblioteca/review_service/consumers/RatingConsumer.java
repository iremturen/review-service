package com.biblioteca.review_service.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biblioteca.review_service.dto.RatingMessage;
import com.biblioteca.review_service.services.interfaces.IRatingService;
import java.util.List;
import com.biblioteca.review_service.models.Rating;
import lombok.AllArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RatingConsumer {

    private final IRatingService ratingService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RatingConsumer.class);
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "rating-topic",
            groupId = "rating-group",
            containerFactory = "ratingKafkaListenerContainerFactory")
    public void consumeRatingEvent(RatingMessage message) {
        try {
            if (message.getActionType() == null) {
                throw new IllegalArgumentException("Action type cannot be null");
            }

            Rating rating = new Rating(
                null,                         
                message.getBookId(),
                message.getUserId(),           
                message.getRating(),
                message.getCreatedAt(),
                message.getUpdatedAt()
            );

            switch (message.getActionType()) {
                case "ADD":
                    ratingService.addRating(rating);
                    logger.info("Added rating: {}", rating);
                    break;

                case "DELETE":
                    ratingService.removeRating(rating.getId());
                    logger.info("Deleted rating with ID: {}", rating.getId());
                    break;

                case "UPDATE":
                    ratingService.updateRating(rating);
                    logger.info("Updated rating: {}", rating);
                    break;

                case "GET_ALL":
                    List<Rating> allRatings = ratingService.getAllRatings(message.getBookId());
                    logger.info("Retrieved all ratings for book ID: {}", message.getBookId());
                
                    Map<String, Object> getAllResponse = new HashMap<>();
                    getAllResponse.put("type", "GET_ALL");
                    getAllResponse.put("bookId", message.getBookId());
                    getAllResponse.put("ratings", allRatings);
                
                    kafkaTemplate.send("rating-result-topic", objectMapper.writeValueAsString(getAllResponse));
                    break;

                case "GET_USER":
                    List<Rating> userRatings = ratingService.getUserRatings(message.getUserId());
                    logger.info("Retrieved ratings for user ID: {}", message.getUserId());
                
                    Map<String, Object> getUserResponse = new HashMap<>();
                    getUserResponse.put("type", "GET_USER");
                    getUserResponse.put("userId", message.getUserId());
                    getUserResponse.put("ratings", userRatings);
                
                    kafkaTemplate.send("rating-result-topic", objectMapper.writeValueAsString(getUserResponse));
                    break;

                case "GET_COUNT":
                    Integer count = ratingService.getRatingCount(message.getBookId());
                    logger.info("Retrieved rating count for book ID: {}", message.getBookId());
                
                    Map<String, Object> getCountResponse = new HashMap<>();
                    getCountResponse.put("type", "GET_COUNT");
                    getCountResponse.put("bookId", message.getBookId());
                    getCountResponse.put("count", count);
                
                    kafkaTemplate.send("rating-result-topic", objectMapper.writeValueAsString(getCountResponse));
                    break;

                case "GET_AVERAGE":
                    Double average = ratingService.getAverageRating(message.getBookId());
                    logger.info("Retrieved average rating for book ID: {}", message.getBookId());
                
                    Map<String, Object> resultMessage = new HashMap<>();
                    resultMessage.put("type", "GET_AVERAGE");
                    resultMessage.put("bookId", message.getBookId());
                    resultMessage.put("averageRating", average);
                
                    String jsonMessage = objectMapper.writeValueAsString(resultMessage);
                    kafkaTemplate.send("rating-result-topic", jsonMessage);
                    break;
                
                default:
                    throw new IllegalArgumentException("Invalid action type: " + message.getActionType());
            }
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
