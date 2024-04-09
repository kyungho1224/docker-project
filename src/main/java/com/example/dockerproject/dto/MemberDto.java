package com.example.dockerproject.dto;

import com.example.dockerproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class MemberJoinRequest {
        private String username;
        private String email;
        private String password;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class MemberJoinResponse {
        private Long id;
        private String username;
        private String email;

        public static MemberJoinResponse of(Member member) {
            return MemberJoinResponse.builder()
              .id(member.getId())
              .username(member.getUsername())
              .email(member.getEmail())
              .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class MemberLoginRequest {
        private String email;
        private String password;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class MemberLoginResponse {
        private Long id;
        private String username;
        private String email;

        public static MemberLoginResponse of(Member member) {
            return MemberLoginResponse.builder()
              .id(member.getId())
              .username(member.getUsername())
              .email(member.getEmail())
              .build();
        }
    }

}
