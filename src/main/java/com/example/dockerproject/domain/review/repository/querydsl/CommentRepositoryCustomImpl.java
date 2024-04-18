package com.example.dockerproject.domain.review.repository.querydsl;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.review.entity.Comment;
import com.example.dockerproject.domain.review.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public Optional<Comment> findFirstByIdAndRegisterStatus(Long commentId, RegisterStatus registerStatus) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QComment qComment = QComment.comment;
        return Optional.ofNullable(queryFactory.selectFrom(qComment)
          .where(
            qComment.id.eq(commentId).and(qComment.registerStatus.eq(registerStatus))
          )
          .orderBy(qComment.createdAt.asc())
          .fetchFirst());
    }

}
