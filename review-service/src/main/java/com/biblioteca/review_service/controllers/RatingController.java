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
import com.biblioteca.review_service.models.Rating;
import com.biblioteca.review_service.services.interfaces.IRatingService;

@RestController
@AllArgsConstructor
@CrossOrigin()
@RequestMapping(value = "service/rating")
public class RatingController {

    private IRatingService ratingService;

    @GetMapping("/average")
    public ResponseEntity<?> getBookAverageRating(@RequestParam Integer bookId) {
        try {
            return ResponseEntity.ok(ratingService.getAverageRating(bookId));
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRatings(@RequestParam Integer bookId) {
        try {
            return ResponseEntity.ok(ratingService.getAllRatings(bookId));
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserRatings(@RequestParam Integer userId) {
        try {
            return ResponseEntity.ok(ratingService.getUserRatings(userId));
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getRatingCount(@RequestParam Integer bookId) {
        try {
            return ResponseEntity.ok(ratingService.getRatingCount(bookId));
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody Rating rating) {
        try {
            ratingService.addRating(rating);
            return ResponseEntity.ok().build();
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }   
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeRating(@RequestParam Integer ratingId) {
        try {
            ratingService.removeRating(ratingId);
            return ResponseEntity.ok().build();
        } catch (BadRequestException | InvalidParameterException e) {
            Map<String, String> err = new HashMap<>();
            err.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }   
}
