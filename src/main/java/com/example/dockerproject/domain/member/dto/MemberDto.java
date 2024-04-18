package com.example.dockerproject.domain.member.dto;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.member.constant.MemberRole;
import com.example.dockerproject.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class JoinRequest {

        @NotBlank(message = "이름은 필수 입력입니다")
        private String name;

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
        private String email;
        private String mobile;
        private String address;
        private RegisterStatus registerStatus;
        private MemberRole memberRole;
        private LocalDateTime createdAt;

        public static JoinResponse of(Member member) {
            return JoinResponse.builder()
              .id(member.getId())
              .name(member.getName())
              .email(member.getEmail())
              .mobile(member.getMobile())
              .address(member.getAddress())
              .registerStatus(member.getRegisterStatus())
              .memberRole(member.getMemberRole())
              .createdAt(member.getCreatedAt())
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

        public static LoginResponse of(Member member, String accessToken) {
            return LoginResponse.builder()
              .id(member.getId())
              .email(member.getEmail())
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

        public static SimpleInfo of(Member member) {
            return SimpleInfo.builder()
              .id(member.getId())
              .name(member.getName())
              .email(member.getEmail())
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
        private String email;
        private String mobile;
        private String address;
        private RegisterStatus registerStatus;
        private MemberRole memberRole;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static DetailInfo of(Member member) {
            return DetailInfo.builder()
              .id(member.getId())
              .name(member.getName())
              .email(member.getEmail())
              .mobile(member.getMobile())
              .address(member.getAddress())
              .registerStatus(member.getRegisterStatus())
              .memberRole(member.getMemberRole())
              .createdAt(member.getCreatedAt())
              .updatedAt(member.getUpdatedAt())
              .build();
        }

    }

}
