package com.example.dockerproject.domain.review.controller;

import com.example.dockerproject.domain.review.dto.ReviewDto;
import com.example.dockerproject.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto.RegisterResponse> register(
      Authentication authentication,
      @RequestBody ReviewDto.RegisterRequest request
    ) {
        var response = reviewService.create(authentication.getName(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto.DetailInfo> getOne(
      Authentication authentication,
      @PathVariable Long reviewId
    ) {
        var response = reviewService.selectOne(authentication.getName(), reviewId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDto.SimpleInfo>> getAll(
      Authentication authentication,
      Pageable pageable
    ) {
        var response = reviewService.selectAll(authentication.getName(), pageable);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewDto.DetailInfo> modify(
      Authentication authentication,
      @PathVariable Long reviewId,
      @RequestBody ReviewDto.RegisterRequest request
    ) {
        var response = reviewService.update(authentication.getName(), reviewId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> unregister(
      Authentication authentication,
      @PathVariable Long reviewId
    ) {
        reviewService.delete(authentication.getName(), reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
