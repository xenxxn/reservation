package com.example.reservation.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReviewEditingDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private double rating;
        private String contents;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String storeName;
        private String memberId;
        private double rating;
        private String contents;

        public static Response fromDto(ReviewDto reviewDto) {
            return Response.builder()
                    .storeName(reviewDto.getStoreName())
                    .memberId(reviewDto.getMemberId())
                    .rating(reviewDto.getRating())
                    .contents(reviewDto.getContents())
                    .build();
        }
    }
}
