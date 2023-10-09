package com.example.reservation.dto.review;

import com.example.reservation.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private Long reservationId;
    private String memberId;
    private String storeName;
    private double rating;
    private String contents;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static ReviewDto fromEntity(ReviewEntity reviewEntity) {
        return ReviewDto.builder()
                .id(reviewEntity.getId())
                .reservationId(reviewEntity.getReservationId())
                .memberId(reviewEntity.getMemberId())
                .storeName(reviewEntity.getStoreName())
                .rating(reviewEntity.getRating())
                .contents(reviewEntity.getContents())
                .createDate(reviewEntity.getCreateDate())
                .updateDate(reviewEntity.getUpdateDate())
                .build();
    }
}
