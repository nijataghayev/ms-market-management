package com.example.marketmanagement.controller;

import com.example.marketmanagement.model.ReviewReqDto;
import com.example.marketmanagement.model.ReviewResDto;
import com.example.marketmanagement.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: nijataghayev
 */

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    @Operation(summary = "Get reviews for a product", description = "Returns a list of reviews for the specified product ID.")
    public List<ReviewResDto> getReviewsForProduct(@PathVariable Long productId) {
        return reviewService.getReviewsForProduct(productId);
    }

    @PostMapping("/{userId}/product/{productId}")
    @Operation(summary = "Create a new review", description = "Creates a new review for the specified user ID and product ID.")
    public void createReview(@RequestParam Long userId, @RequestParam Long productId, @RequestBody @Valid ReviewReqDto reviewDto) {
        reviewService.createReview(userId, productId, reviewDto);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete review by ID", description = "Deletes the review with the specified review ID.")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
