package com.example.dockerproject.domain.review.entity;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.common.entity.BaseEntity;
import com.example.dockerproject.domain.member.entity.Member;
import com.example.dockerproject.domain.review.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "reviews")
@Entity
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '제목'")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT NOT NULL COMMENT '내용'")
    private String contents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '등록 상태'")
    private RegisterStatus registerStatus;

    public static Review createOf(Member member, ReviewDto.RegisterRequest request) {
        return Review.builder()
            .member(member)
            .title(request.getTitle())
            .contents(request.getContents())
            .registerStatus(RegisterStatus.REGISTERED)
            .build();
    }

    public void updateData(ReviewDto.RegisterRequest request) {
        this.title = request.getTitle();
        this.contents = request.getContents();
    }

    public void delete() {
        this.registerStatus = RegisterStatus.UNREGISTERED;
    }

}
