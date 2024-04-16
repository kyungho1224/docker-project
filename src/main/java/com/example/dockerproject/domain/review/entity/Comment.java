package com.example.dockerproject.domain.review.entity;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.common.entity.BaseEntity;
import com.example.dockerproject.domain.member.entity.Member;
import com.example.dockerproject.domain.review.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false, columnDefinition = "VARCHAR(100) NOT NULL COMMENT '댓글 내용'")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) NOT NULL COMMENT '등록 상태'")
    private RegisterStatus registerStatus;

    public static Comment createOf(Member member, Review review, Comment parent, String contents) {
        return Comment.builder()
          .member(member)
          .contents(contents)
          .review(review)
          .parent(parent)
          .registerStatus(RegisterStatus.REGISTERED)
          .build();
    }

    public void updateData(CommentDto.Request request) {
        this.contents = request.getContents();
    }

    public void delete() {
        this.registerStatus = RegisterStatus.UNREGISTERED;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

}
