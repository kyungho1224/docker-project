package com.example.dockerproject.domain.review.service;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.member.entity.Member;
import com.example.dockerproject.domain.member.service.MemberService;
import com.example.dockerproject.domain.review.dto.ReviewDto;
import com.example.dockerproject.domain.review.entity.Review;
import com.example.dockerproject.domain.review.repository.ReviewRepository;
import com.example.dockerproject.exception.ApiErrorCode;
import com.example.dockerproject.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberService memberService;

    public ReviewDto.RegisterResponse create(String email, ReviewDto.RegisterRequest request) {
        Member member = memberService.getMember(email);
        Review newReview = Review.createOf(member, request);
        Review savedReview = reviewRepository.save(newReview);
        return ReviewDto.RegisterResponse.of(savedReview);
    }

    public ReviewDto.DetailInfo selectOne(String email, Long reviewId) {
        memberService.getMember(email);
        return reviewRepository.findByIdAndRegisterStatus(reviewId, RegisterStatus.REGISTERED)
          .map(ReviewDto.DetailInfo::of)
          .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_FOUND_REVIEW));
    }

    public Page<ReviewDto.SimpleInfo> selectAll(String email, Pageable pageable) {
        memberService.getMember(email);
        return reviewRepository.findAllByRegisterStatus(RegisterStatus.REGISTERED, pageable)
          .map(ReviewDto.SimpleInfo::of);
    }

    public ReviewDto.DetailInfo update(String email, Long reviewId, ReviewDto.RegisterRequest request) {
        Member member = memberService.getMember(email);
        Review review = getReview(reviewId);
        if (review.getMember().equals(member)) {
            review.updateData(request);
            Review updatedReview = reviewRepository.save(review);
            return ReviewDto.DetailInfo.of(updatedReview);
        }
        throw new RuntimeException("denied permission");
    }

    public void delete(String email, Long reviewId) {
        Member member = memberService.getMember(email);
        Review review = getReview(reviewId);
        if (review.getMember().equals(member)) {
            review.delete();
        }
        throw new RuntimeException("denied permission");
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.findByIdAndRegisterStatus(reviewId, RegisterStatus.REGISTERED)
          .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_FOUND_REVIEW));
    }

}
