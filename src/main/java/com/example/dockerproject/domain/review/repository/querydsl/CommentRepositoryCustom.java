package com.example.dockerproject.domain.review.repository.querydsl;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.review.entity.Comment;

import java.util.Optional;

public interface CommentRepositoryCustom {

    Optional<Comment> findFirstByIdAndRegisterStatus(Long commentId, RegisterStatus registerStatus);

}
