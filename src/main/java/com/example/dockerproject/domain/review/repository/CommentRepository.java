package com.example.dockerproject.domain.review.repository;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.review.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findFirstByIdAndRegisterStatus(Long commentId, RegisterStatus registerStatus);

    Optional<Comment> findFirstByParentIdAndRegisterStatus(Long parentId, RegisterStatus registerStatus);

}
