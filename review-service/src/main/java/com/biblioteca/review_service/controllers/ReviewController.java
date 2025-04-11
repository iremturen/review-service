package com.biblioteca.review_service.controllers;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.review_service.exceptions.BadRequestException;
import com.biblioteca.review_service.exceptions.InvalidParameterException;
import com.biblioteca.review_service.models.Review;
import com.biblioteca.review_service.services.interfaces.IReviewService;

@RestController
@AllArgsConstructor
@CrossOrigin()
@RequestMapping(value = "service/review")
public class ReviewController {

    private IReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews(@RequestParam Integer bookId) {
        try {
            return ResponseEntity.ok(reviewService.getAllReviews(bookId));
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserReviews(@RequestParam Integer userId) {
        try {
            return ResponseEntity.ok(reviewService.getUserReviews(userId));
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getReviewCount(@RequestParam Integer bookId) {
        try {
            return ResponseEntity.ok(reviewService.getReviewCount(bookId));
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage()); 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        try {
            reviewService.addReview(review);
            return ResponseEntity.ok().build();
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }  
    
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeReview(@RequestParam Integer reviewId) {
        try {
            reviewService.removeReview(reviewId);
            return ResponseEntity.ok().build();
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

}
