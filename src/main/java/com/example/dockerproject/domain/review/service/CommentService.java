package com.example.dockerproject.domain.review.service;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.member.entity.Member;
import com.example.dockerproject.domain.member.service.MemberService;
import com.example.dockerproject.domain.review.dto.CommentDto;
import com.example.dockerproject.domain.review.entity.Comment;
import com.example.dockerproject.domain.review.entity.Review;
import com.example.dockerproject.domain.review.repository.CommentRepository;
import com.example.dockerproject.domain.review.repository.querydsl.CommentRepositoryCustomImpl;
import com.example.dockerproject.exception.ApiErrorCode;
import com.example.dockerproject.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentRepositoryCustomImpl commentRepositoryCustom;
    private final MemberService memberService;
    private final ReviewService reviewService;

    public CommentDto.Response create(String email, Long reviewId, CommentDto.Request request) {
        Member member = memberService.getMember(email);
        Review review = reviewService.getReview(reviewId);
        Comment parent = request.getParentId() != null ? getComment(request.getParentId()) : null;

        Comment newComment = Comment.createOf(member, review, parent, request.getContents());
        Comment savedComment = commentRepository.save(newComment);
        Optional.ofNullable(parent).ifPresentOrElse(it -> {
            it.addComment(savedComment);
        }, () -> {
            review.addComment(savedComment);
        });
        return CommentDto.Response.of(savedComment);
    }

    public CommentDto.Response update(String email, Long commentId, CommentDto.Request request) {
        Member currentMember = memberService.getMember(email);
        Comment comment = getComment(commentId);
        if (currentMember.equals(comment.getMember())) {
            comment.updateData(request);
            Comment updatedComment = commentRepository.save(comment);
            return CommentDto.Response.of(updatedComment);
        }
        throw new RuntimeException("denied permission");
    }

    public void delete(String email, Long commentId) {
        Member currentMember = memberService.getMember(email);
        Comment comment = getComment(commentId);
        if (currentMember.equals(comment.getMember())) {
            comment.delete();
        }
        throw new RuntimeException("denied permission");
    }

    public Comment getComment(Long commentId) {
        return commentRepositoryCustom.findFirstByIdAndRegisterStatus(commentId, RegisterStatus.REGISTERED)
          .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_FOUND_COMMENT));
    }

}
