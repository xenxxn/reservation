package com.example.reservation.service;
import com.example.reservation.constants.ReservationStatus;
import com.example.reservation.dto.review.ReviewDto;
import com.example.reservation.dto.review.ReviewEditingDto;
import com.example.reservation.constants.ReviewSort;
import com.example.reservation.dto.review.ReviewWritingDto;
import com.example.reservation.entity.ReservationEntity;
import com.example.reservation.entity.ReviewEntity;
import com.example.reservation.exception.impl.*;
import com.example.reservation.repository.MemberRepository;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreService storeService;
    private final ReservationRepository reservationRepository;

    public ReviewDto addReview(Long reservationId, String memberId, ReviewWritingDto.Request request) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(NoReservationException::new);
        validateReviewAvailable(reservationEntity, memberId);
        validateReviewDetail(request.getRating(), request.getContents());

        ReviewEntity reviewEntity = ReviewWritingDto.Request.toEntity(request, reservationEntity);
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);

        storeService.updateRatingForAddReview(ReviewDto.fromEntity(savedReview));
        return ReviewDto.fromEntity(reviewEntity);
    }

    public void validateReviewAvailable(ReservationEntity reservationEntity, String memberId) {
        if (!memberRepository.existsByMemberId(memberId)) {
            throw new NotExistIdException();
        }
        if (!reservationEntity.getMemberId().equals(memberId)) {
            throw new FailAccessAuthorityReservationException();
        }
        if (reviewRepository.existsByReservationId(reservationEntity.getId())) {
            throw new AlreadyExistReviewException();
        }
        if (!reservationEntity.getStatus().equals(ReservationStatus.COMPLETE)) {
            throw new NotCompleteReservationException();
        }
    }

    public void validateReviewDetail(double rating, String contents){
        if (rating >= 5 || rating < 0) {
            throw new RatingLimitException();
        }
        if (contents.length() < 10 ||contents.length() > 200) {
            throw new ReviewLengthLimitException();
        }
    }

    public ReviewDto updateReview(Long reviewId, String memberId, ReviewEditingDto.Request request) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(NoReviewException::new);
        double originalRating = reviewEntity.getRating();

        if (!reviewEntity.getMemberId().equals(memberId)) {
            throw new NotMatchIdException();
        }
        validateReviewDetail(request.getRating(), request.getContents());

        reviewEntity.setRating(request.getRating());
        reviewEntity.setContents(request.getContents());
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        ReviewDto updatedReview = ReviewDto.fromEntity(savedReview);

        storeService.updateRatingForUpdateReview(updatedReview, originalRating);
        return updatedReview;
    }

    public Page<ReviewDto> reviewListByMemberId(String memberId, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        Page<ReviewEntity> reviewList = reviewRepository.findByMemberIdOrderByCreateDateDesc(memberId, pageRequest);

        if (reviewList.getSize() == 0) {
            throw new NoReviewException();
        }
        return reviewList.map(ReviewDto::fromEntity);
    }
    
    public Page<ReviewDto> reviewListByStoreName(String storeName, ReviewSort reviewSort, Integer page){
        PageRequest pageRequest = PageRequest.of(page, 5);
        Page<ReviewEntity> reviewEntityPage;
        
        if (reviewSort.equals(ReviewSort.RATING_DESC)) {
            reviewEntityPage = reviewRepository.findByStoreNameOrderByRatingDesc(storeName, pageRequest);
        } else if (reviewSort.equals(ReviewSort.RATING_ASC)) {
            reviewEntityPage = reviewRepository.findByStoreNameOrderByRatingAsc(storeName, pageRequest);
        } else {
            reviewEntityPage = reviewRepository.findByStoreNameOrderByCreateDateDesc(storeName, pageRequest);
        }

        if (reviewEntityPage.getNumberOfElements() == 0) {
            throw new NoReviewException();
        }
        return reviewEntityPage.map(ReviewDto::fromEntity);
    }
}
