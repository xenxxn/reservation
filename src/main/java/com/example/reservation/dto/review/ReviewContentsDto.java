package com.example.reservation.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewContentsDto {
    private String memberId;
    private String rating;
    private String contents;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static ReviewContentsDto fromDto (ReviewDto reviewDto){
        return ReviewContentsDto.builder()
                .memberId(reviewDto.getMemberId())
                .rating(String.format("%.1f", reviewDto.getRating()))
                .contents(reviewDto.getContents())
                .createDate(reviewDto.getCreateDate())
                .updateDate(reviewDto.getUpdateDate())
                .build();
    }
}
