package com.example.reservation.dto.review;

import com.example.reservation.entity.ReservationEntity;
import com.example.reservation.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReviewWritingDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private double rating;
        private String contents;

        public static ReviewEntity toEntity(Request request, ReservationEntity reservationEntity) {
            return ReviewEntity.builder()
                    .reservationId(reservationEntity.getId())
                    .memberId(reservationEntity.getMemberId())
                    .storeName(reservationEntity.getStoreName())
                    .rating(request.getRating())
                    .contents(request.getContents())
                    .createDate(LocalDateTime.now())
                    .build();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String memberId;
        private String storeName;
        private double rating;
        private String contents;
        private LocalDateTime createDate;

        public static Response fromDto (ReviewDto reviewDto) {
            return Response.builder()
                    .memberId(reviewDto.getMemberId())
                    .storeName(reviewDto.getStoreName())
                    .rating(reviewDto.getRating())
                    .contents(reviewDto.getContents())
                    .createDate(reviewDto.getCreateDate())
                    .build();
        }
    }

}
