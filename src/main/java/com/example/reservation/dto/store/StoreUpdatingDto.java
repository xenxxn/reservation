package com.example.reservation.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class StoreUpdatingDto {
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
        private double lat;
        private double lnt;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public static Response fromDto(StoreDto storeDto){
            return Response.builder()
                    .partnerId(storeDto.getPartnerId())
                    .storeName(storeDto.getStoreName())
                    .storeAddr(storeDto.getStoreAddr())
                    .storeInfo(storeDto.getStoreInfo())
                    .lat(storeDto.getLat())
                    .lnt(storeDto.getLnt())
                    .createDate(storeDto.getCreateDate())
                    .updateDate(storeDto.getUpdateDate())
                    .build();
        }
    }
}
