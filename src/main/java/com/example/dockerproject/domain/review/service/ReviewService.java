package com.example.dockerproject.domain.review.service;

import com.example.dockerproject.common.constant.RegisterStatus;
import com.example.dockerproject.domain.member.entity.Member;
import com.example.dockerproject.domain.member.service.MemberService;
import com.example.dockerproject.domain.review.dto.ReviewDto;
import com.example.dockerproject.domain.review.entity.Review;
import com.example.dockerproject.domain.review.repository.ReviewRepository;
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
          .orElseThrow(() -> new RuntimeException("Not found review"));
    }

    public Page<ReviewDto.SimpleInfo> selectAll(String email, Pageable pageable) {
        memberService.getMember(email);
        System.out.println("--------------------- : " + RegisterStatus.REGISTERED);
        return reviewRepository.findAllByRegisterStatus(RegisterStatus.REGISTERED, pageable)
          .map(ReviewDto.SimpleInfo::of);
    }

    public ReviewDto.DetailInfo update(String email, Long reviewId, ReviewDto.RegisterRequest request) {
        Member member = memberService.getMember(email);
        return reviewRepository.findByIdAndRegisterStatus(reviewId, RegisterStatus.REGISTERED)
          .filter(review -> review.getMember().equals(member))
          .map(review -> {
              review.updateData(request);
              return reviewRepository.save(review);
          })
          .map(ReviewDto.DetailInfo::of)
          .orElseThrow(() -> new RuntimeException("Not found review"));
    }

    public void delete(String email, Long reviewId) {
        Member member = memberService.getMember(email);
        reviewRepository.findByIdAndRegisterStatus(reviewId, RegisterStatus.REGISTERED)
          .orElseThrow(() -> new RuntimeException("Not found review"));
    }

}
