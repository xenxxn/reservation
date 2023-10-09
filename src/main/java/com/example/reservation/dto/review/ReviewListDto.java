package com.example.reservation.dto.review;

import com.example.reservation.constants.ReviewSort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListDto {
    String storeName;
    ReviewSort sort;
}
