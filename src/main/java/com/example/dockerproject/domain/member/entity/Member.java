package com.example.dockerproject.domain.member.entity;

import com.example.dockerproject.common.entity.BaseEntity;
import com.example.dockerproject.domain.member.constant.MemberRole;
import com.example.dockerproject.common.constant.RegisterStatus;
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

    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '이름'")
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) NOT NULL COMMENT '이메일'")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '비밀번호'")
    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(15) NOT NULL COMMENT '휴대폰'")
    private String mobile;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '주소'")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '등록 상태'")
    private RegisterStatus registerStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '멤버십 등급'")
    private MemberRole memberRole;

    public static Member createOf(MemberDto.JoinRequest request, String encryptPassword) {
        return Member.builder()
          .name(request.getName())
          .email(request.getEmail())
          .password(encryptPassword)
          .mobile(request.getMobile())
          .address(request.getAddress())
          .registerStatus(RegisterStatus.REGISTERED)
          .memberRole(MemberRole.SILVER)
          .build();
    }

    public void updateRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public void delete() {
        this.registerStatus = RegisterStatus.UNREGISTERED;
    }

}
