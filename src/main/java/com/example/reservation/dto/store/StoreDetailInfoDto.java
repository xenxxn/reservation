package com.example.reservation.dto.store;

import com.example.reservation.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StoreDetailInfoDto {
    private String storeName;
    private String storeAddr;
    private String storeInfo;
    private String distance;
    private String rating;
    private Long count;


    public static StoreDetailInfoDto fromEntity(StoreEntity storeEntity) {
        return StoreDetailInfoDto.builder()
                .storeName(storeEntity.getStoreName())
                .storeAddr(storeEntity.getStoreAddr())
                .storeInfo(storeEntity.getStoreInfo())
                .rating(String.format("%.2f", storeEntity.getRating()))
                .count(storeEntity.getRatingCount())
                .build();
    }

    public static StoreDetailInfoDto fromDto(StoreDto storeDto) {
        return StoreDetailInfoDto.builder()
                .storeName(storeDto.getStoreName())
                .storeAddr(storeDto.getStoreAddr())
                .storeInfo(storeDto.getStoreInfo())
                .distance(String.format("%.3fkm", storeDto.getDistance()))
                .rating(String.format("%.2f", storeDto.getRating()))
                .count(storeDto.getRatingCount())
                .build();
    }
}
