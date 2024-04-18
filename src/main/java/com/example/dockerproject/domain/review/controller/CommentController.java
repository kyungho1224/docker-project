package com.example.dockerproject.domain.review.controller;

import com.example.dockerproject.domain.review.dto.CommentDto;
import com.example.dockerproject.domain.review.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews/{reviewId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto.Response> register(
      Authentication authentication,
      @PathVariable Long reviewId,
      @Validated @RequestBody CommentDto.Request request
    ) {
        var response = commentService.create(authentication.getName(), reviewId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto.Response> modify(
      Authentication authentication,
      @PathVariable Long reviewId,
      @PathVariable Long commentId,
      @RequestBody CommentDto.Request request
    ) {
        var response = commentService.update(authentication.getName(), commentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> unregister(
      Authentication authentication,
      @PathVariable Long reviewId,
      @PathVariable Long commentId
    ) {
        commentService.delete(authentication.getName(), commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
