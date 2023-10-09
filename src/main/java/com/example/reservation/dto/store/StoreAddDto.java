package com.example.reservation.dto.store;

import com.example.reservation.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class StoreAddDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private String storeName;
        private String storeAddr;
        private String storeInfo;
        private double lat;
        private double lnt;

        public static StoreEntity toEntity(Request request, String partnerId) {
            return StoreEntity.builder()
                    .partnerId(partnerId)
                    .storeName(request.getStoreName())
                    .storeAddr(request.getStoreAddr())
                    .storeInfo(request.getStoreInfo())
                    .lat(request.getLat())
                    .lnt(request.getLnt())
                    .createDate(LocalDateTime.now())
                    .rating(0.0)
                    .ratingCount(0L)
                    .build();
        }


    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String partnerId;
        private String storeName;
        private String storeAddr;
        private String storeInfo;
        private LocalDateTime createDate;

        public static Response fromDto(StoreDto storeDto) {
            return Response.builder()
                    .partnerId(storeDto.getPartnerId())
                    .storeName(storeDto.getStoreName())
                    .storeAddr(storeDto.getStoreAddr())
                    .storeInfo(storeDto.getStoreInfo())
                    .createDate(storeDto.getCreateDate())
                    .build();
        }

    }
}
