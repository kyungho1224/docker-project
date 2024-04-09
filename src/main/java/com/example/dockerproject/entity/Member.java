package com.example.dockerproject.entity;

import com.example.dockerproject.dto.MemberDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "members")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    public static Member createOf(MemberDto.MemberJoinRequest request) {
        return Member.builder()
          .username(request.getUsername())
          .email(request.getEmail())
          .password(request.getPassword())
          .build();
    }

}
