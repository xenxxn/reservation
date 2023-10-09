package com.example.reservation.controller;

import com.example.reservation.dto.review.ReviewDto;
import com.example.reservation.dto.review.ReviewEditingDto;
import com.example.reservation.dto.review.ReviewWritingDto;
import com.example.reservation.entity.MemberEntity;
import com.example.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/review/add/{reservationId}")
    public ResponseEntity<?> addReview(@PathVariable Long reservationId,
                                       @RequestBody ReviewWritingDto.Request request,
                                       @AuthenticationPrincipal MemberEntity memberEntity){
        ReviewDto reviewDto = reviewService.addReview(
                reservationId, memberEntity.getMemberId(), request
        );
        return ResponseEntity.ok(ReviewWritingDto.Response.fromDto(reviewDto));
    }


    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/review/list/{memberId}")
    public ResponseEntity<?> reviewList(@PathVariable String memberId,
                                        @RequestParam(value = "p", defaultValue = "1") Integer page,
                                        @AuthenticationPrincipal MemberEntity memberEntity){
        if(!memberEntity.getMemberId().equals(memberId)){
            throw new RuntimeException("아이디가 일치하지 않습니다.");
        }
        Page<ReviewDto> list = reviewService.reviewListByMemberId(memberId, page - 1);
        return ResponseEntity.ok(list);
    }


    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/review/edit/{reviewId}")
    public ResponseEntity<?> editReview(@PathVariable Long reviewId,
                                        @RequestBody ReviewEditingDto.Request request,
                                        @AuthenticationPrincipal MemberEntity memberEntity){
        ReviewDto editedReview = reviewService.updateReview(reviewId, memberEntity.getMemberId(), request);
        return ResponseEntity.ok(ReviewEditingDto.Response.fromDto(editedReview));
    }


}
