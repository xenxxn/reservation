package com.example.reservation.service;

import com.example.reservation.dto.review.ReviewDto;
import com.example.reservation.dto.store.StoreDetailInfoDto;
import com.example.reservation.constants.StoreSort;
import com.example.reservation.entity.StoreEntity;
import com.example.reservation.exception.impl.NoStoreException;
import com.example.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreDetailInfoDto findByStoreName(String storeName) {
        StoreEntity store = storeRepository.findByStoreName(storeName)
                .orElseThrow(NoStoreException::new);
        return StoreDetailInfoDto.fromEntity(store);
    }


    public void updateRatingForAddReview(ReviewDto reviewDto) {
        StoreEntity store = storeRepository.findByStoreName(reviewDto.getStoreName())
                .orElseThrow(NoStoreException::new);
        Long ratingCount = store.getRatingCount();
        double rating = getNewRatingForAddReview(store.getRating(), ratingCount, reviewDto.getRating());

        store.setRating(rating);
        store.setRatingCount(ratingCount + 1);
        storeRepository.save(store);
    }

    public void updateRatingForUpdateReview(ReviewDto updatedReview, double originalRating) {
        StoreEntity store = storeRepository.findByStoreName(updatedReview.getStoreName())
                .orElseThrow(NoStoreException::new);

        double newRating =
                getNewRatingForEditReview(
                        store.getRating(),
                        store.getRatingCount(),
                        originalRating,
                        updatedReview.getRating());

        store.setRating(newRating);
        storeRepository.save(store);
    }



    private PageRequest getPageRequestBySortTypeAndPage(StoreSort storeSortType, int page){
        PageRequest pageRequest = PageRequest.of(page, 5);
        //상점명순
        if(storeSortType == StoreSort.ALPHABET){
            return PageRequest.of(page, 3, Sort.by("storeName"));
            //별점순
        }else if(storeSortType == StoreSort.RATING){
            return PageRequest.of(page, 3, Sort.by("rating").descending());
            //별점갯수순
        }else if(storeSortType == StoreSort.RATING_COUNT){
            return PageRequest.of(page, 3, Sort.by("ratingCount").descending());
        }
        return pageRequest;
    }


    private double getNewRatingForAddReview(double rating, Long ratingCount,
                                            double reviewRating){
        return rating * ((double)ratingCount / (ratingCount + 1)) +
                reviewRating / (ratingCount + 1);
    }

    private double getNewRatingForEditReview(double rating, Long ratingCount,
                                             double reviewOldRating,
                                             double reviewNewRating){
        return rating - (reviewOldRating - reviewNewRating) / ratingCount;
    }
}
