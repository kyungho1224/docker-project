package com.example.dockerproject.domain.review.dto;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class RegisterRequest {

        private String title;
        private String contents;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class RegisterResponse {

        private Long id;
        private String title;
        private String contents;
        private LocalDateTime createdAt;

        public static RegisterResponse of(Review review) {
            return RegisterResponse.builder()
              .id(review.getId())
              .title(review.getTitle())
              .contents(review.getContents())
              .createdAt(review.getCreatedAt())
              .build();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class SimpleInfo {

        private Long id;
        private String title;
        private LocalDateTime updatedAt;

        public static SimpleInfo of(Review review) {
            return SimpleInfo.builder()
              .id(review.getId())
              .title(review.getTitle())
              .updatedAt(review.getUpdatedAt())
              .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class DetailInfo {

        private Long id;
        private String title;
        private String contents;
        private RegisterStatus registerStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<CommentDto.Response> commentList;

        public static DetailInfo of(Review review) {
            return DetailInfo.builder()
              .id(review.getId())
              .title(review.getTitle())
              .contents(review.getContents())
              .registerStatus(review.getRegisterStatus())
              .createdAt(review.getCreatedAt())
              .updatedAt(review.getUpdatedAt())
              .commentList(CommentDto.Response.of(review.getComments()))
              .build();
        }
    }

}
