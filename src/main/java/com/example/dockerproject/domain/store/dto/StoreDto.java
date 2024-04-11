package com.example.dockerproject.domain.store.dto;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.store.entity.Store;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class StoreDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class JoinRequest {

        @NotBlank(message = "상점명은 필수 입력입니다")
        private String name;

        private String description;

        @NotBlank(message = "이메일은 필수 입력입니다")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력입니다")
        private String password;

        @NotBlank(message = "휴대폰 번호는 필수 입력입니다")
        private String mobile;

        @NotBlank(message = "주소는 필수 입력입니다")
        private String address;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class JoinResponse {

        private Long id;
        private String name;
        private String description;
        private String email;
        private String mobile;
        private String address;
        private LocalDateTime createdAt;

        public static JoinResponse of(Store store) {
            return JoinResponse.builder()
              .id(store.getId())
              .name(store.getName())
              .description(store.getDescription())
              .email(store.getEmail())
              .mobile(store.getMobile())
              .address(store.getAddress())
              .createdAt(store.getCreatedAt())
              .build();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class LoginRequest {

        @NotBlank(message = "이메일은 필수 입력입니다")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력입니다")
        private String password;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class LoginResponse {

        private Long id;
        private String email;
        private String accessToken;

        public static LoginResponse of(Store store, String accessToken) {
            return LoginResponse.builder()
              .id(store.getId())
              .email(store.getEmail())
              .accessToken(accessToken)
              .build();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class SimpleInfo {

        private Long id;
        private String name;
        private String email;
        private LocalDateTime createdAt;

        public static SimpleInfo of(Store store) {
            return SimpleInfo.builder()
              .id(store.getId())
              .name(store.getName())
              .email(store.getEmail())
              .createdAt(store.getCreatedAt())
              .build();
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class DetailInfo {

        private Long id;
        private String name;
        private String description;
        private String email;
        private String mobile;
        private String address;
        private RegisterStatus registerStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static DetailInfo of(Store store) {
            return DetailInfo.builder()
              .id(store.getId())
              .name(store.getName())
              .description(store.getDescription())
              .email(store.getEmail())
              .mobile(store.getMobile())
              .address(store.getAddress())
              .registerStatus(store.getRegisterStatus())
              .createdAt(store.getCreatedAt())
              .updatedAt(store.getUpdatedAt())
              .build();
        }

    }

}
