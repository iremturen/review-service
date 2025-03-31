package com.biblioteca.review_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "review-topic", groupId = "review-group")
    public void consumeReviewMessage(String message) {
        System.out.println("Received review message: " + message);
    }
}
