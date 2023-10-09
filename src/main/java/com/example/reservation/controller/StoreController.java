package com.example.reservation.controller;

import com.example.reservation.dto.review.ReviewContentsDto;
import com.example.reservation.dto.review.ReviewDto;
import com.example.reservation.dto.review.ReviewListDto;
import com.example.reservation.dto.store.StoreDetailInfoDto;
import com.example.reservation.service.ReviewService;
import com.example.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final ReviewService reviewService;


    @GetMapping("/detail")
    public ResponseEntity<?> storeDetail(@RequestParam String storeName) {
        StoreDetailInfoDto findStore = storeService.findByStoreName(storeName);
        return ResponseEntity.ok(findStore);
    }


    @GetMapping("/review")
    public ResponseEntity<?> reviewListByStoreId(@RequestBody ReviewListDto reviewListDto,
                                                 @RequestParam(name = "p", defaultValue = "1") Integer page){
        Page<ReviewDto> list = reviewService.reviewListByStoreName(
                reviewListDto.getStoreName(), reviewListDto.getSort(), page - 1);

        Page<ReviewContentsDto> responseList = list.map(ReviewContentsDto::fromDto);

        return ResponseEntity.ok(responseList);
    }
}
