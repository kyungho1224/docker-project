package com.example.dockerproject.domain.review.repository;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

//    Optional<Review> findByIdAndRegisterStatus(Long id, RegisterStatus registerStatus);

//    @Query("SELECT r FROM Review r WHERE r.id = :id AND r.registerStatus = :registerStatus")
    @Query("SELECT DISTINCT r FROM Review r LEFT JOIN FETCH r.comments c WHERE r.id = :id AND r.registerStatus = :registerStatus")
//    @EntityGraph(attributePaths = "comments")
    Optional<Review> findByIdAndRegisterStatus(@Param("id") Long id, @Param("registerStatus") RegisterStatus registerStatus);

    Page<Review> findAllByRegisterStatus(RegisterStatus registerStatus, Pageable pageable);

}
