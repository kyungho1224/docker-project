package com.example.dockerproject.domain.store.dto;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.store.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class RegisterRequest {

        @NotBlank(message = "제목은 필수 입력입니다")
        private String title;

        @NotBlank(message = "내용은 필수 입력입니다")
        private String message;
        private RegisterStatus registerStatus;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class RegisterResponse {

        private Long id;
        private String title;
        private String message;
        private RegisterStatus registerStatus;
        private LocalDateTime createdAt;

        public static RegisterResponse of(Notice notice) {
            return RegisterResponse.builder()
              .id(notice.getId())
              .title(notice.getTitle())
              .message(notice.getMessage())
              .registerStatus(notice.getRegisterStatus())
              .createdAt(notice.getCreatedAt())
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
        private RegisterStatus registerStatus;
        private LocalDateTime createdAt;

        public static SimpleInfo of(Notice notice) {
            return SimpleInfo.builder()
              .id(notice.getId())
              .title(notice.getTitle())
              .registerStatus(notice.getRegisterStatus())
              .createdAt(notice.getCreatedAt())
              .build();
        }

        public static List<SimpleInfo> of(List<Notice> notices) {
            return notices.stream().map(SimpleInfo::of).toList();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class DetailInfo {

        private Long id;
        private String title;
        private String message;
        private RegisterStatus registerStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static DetailInfo of(Notice notice) {
            return DetailInfo.builder()
              .build();
        }

        public static List<DetailInfo> of(List<Notice> notices) {
            return notices.stream().map(DetailInfo::of).toList();
        }

    }

}
