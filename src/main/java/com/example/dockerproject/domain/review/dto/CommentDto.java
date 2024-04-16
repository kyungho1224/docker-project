package com.example.dockerproject.domain.review.dto;

import com.example.dockerproject.domain.review.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Request {

        private String contents;    // 내용
        private Long parentId;    // 부모댓글

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private Long id;
        private String contents;
//        private ReviewDto.SimpleInfo review;
        private Comment parent;
        private LocalDateTime createdAt;

        public static Response of(Comment comment) {
            return Response.builder()
              .id(comment.getId())
              .contents(comment.getContents())
//              .review(ReviewDto.SimpleInfo.of(comment.getReview()))
              .parent(comment.getParent())
              .createdAt(comment.getCreatedAt())
              .build();
        }

        public static List<Response> of(List<Comment> comments) {
            return comments.stream().map(Response::of).toList();
        }

    }

}
