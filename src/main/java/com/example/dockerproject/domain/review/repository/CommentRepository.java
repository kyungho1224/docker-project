package com.example.dockerproject.domain.review.repository;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.review.entity.Comment;
import com.example.dockerproject.domain.review.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
