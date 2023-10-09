package com.example.reservation.dto.store;

import com.example.reservation.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDto {
    private Long id;
    private String partnerId;
    private String storeName;
    private String storeAddr;
    private String storeInfo;
    private double lat;
    private double lnt;
    private double distance;
    private double rating;
    private Long ratingCount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static StoreDto fromEntity(StoreEntity storeEntity) {
        return StoreDto.builder()
                .id(storeEntity.getId())
                .partnerId(storeEntity.getPartnerId())
                .storeName(storeEntity.getStoreName())
                .storeAddr(storeEntity.getStoreAddr())
                .storeInfo(storeEntity.getStoreInfo())
                .lat(storeEntity.getLat())
                .lnt(storeEntity.getLnt())
                .rating(storeEntity.getRating())
                .ratingCount(storeEntity.getRatingCount())
                .createDate(storeEntity.getCreateDate())
                .updateDate(storeEntity.getUpdateDate())
                .build();
    }
}
