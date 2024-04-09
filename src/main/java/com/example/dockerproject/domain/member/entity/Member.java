package com.example.dockerproject.domain.member.entity;

import com.example.dockerproject.common.entity.BaseEntity;
import com.example.dockerproject.domain.member.constant.MemberRole;
import com.example.dockerproject.domain.member.constant.RegisterStatus;
import com.example.dockerproject.domain.member.dto.MemberDto;
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
public class Member extends BaseEntity {

    private String username;

    private String email;

    private String password;

    private MemberRole memberRole;

    private RegisterStatus registerStatus;

    public static Member createOf(MemberDto.MemberJoinRequest request, String encryptPassword) {
        return Member.builder()
          .username(request.getUsername())
          .email(request.getEmail())
          .password(encryptPassword)
          .memberRole(MemberRole.SILVER)
          .registerStatus(RegisterStatus.REGISTERED)
          .build();
    }

    public void updateRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

}
