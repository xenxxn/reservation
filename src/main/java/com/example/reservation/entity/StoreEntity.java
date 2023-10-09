package com.example.reservation.entity;

import com.example.reservation.dto.store.StoreUpdatingDto;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "store")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partner_id")
    private String partnerId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_addr")
    private String storeAddr;

    @Column(name = "store_info")
    private String storeInfo;

    private double lat;
    private double lnt;
    private double rating;

    @Column(name = "rating_count")
    private Long ratingCount;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public void edit(StoreUpdatingDto.Request request) {
        if (StringUtils.hasText(request.getStoreName())) {
            this.storeName = request.getStoreName();
        }
        if(StringUtils.hasText(request.getStoreAddr())){
            this.storeAddr = request.getStoreAddr();
        }
        if(StringUtils.hasText(request.getStoreInfo())){
            this.storeInfo = request.getStoreInfo();
        }
        if(request.getLat() != 0 && request.getLnt() != 0){
            this.lat = request.getLat();
            this.lnt = request.getLnt();
        }
        this.updateDate = LocalDateTime.now();
    }

}
