package com.example.dockerproject.domain.review.service;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.member.entity.Member;
import com.example.dockerproject.domain.member.service.MemberService;
import com.example.dockerproject.domain.review.dto.CommentDto;
import com.example.dockerproject.domain.review.entity.Comment;
import com.example.dockerproject.domain.review.entity.Review;
import com.example.dockerproject.domain.review.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;

    public CommentDto.Response create(String email, CommentDto.Request request) {
        Member member = memberService.getMember(email);
        Review review = null;
        Comment parent = null;
        Comment newComment = Comment.createOf(member, review, parent, request.getContents());
        Comment savedComment = commentRepository.save(newComment);
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
        memberService.getMember(email);
        Comment comment = getComment(commentId);
        comment.delete();
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findFirstByIdAndRegisterStatus(commentId, RegisterStatus.REGISTERED)
          .orElseThrow(() -> new RuntimeException("Not found comment"));
    }

}
