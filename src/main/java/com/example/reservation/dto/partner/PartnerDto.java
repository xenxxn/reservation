package com.example.reservation.dto.partner;

import com.example.reservation.entity.PartnerEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PartnerDto {
    private String partnerId;
    private String password;
    private String partnerName;
    private String phone;
    private Long storeId;
    private String storeName;
    private String memberType;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    public static PartnerDto fromEntity(PartnerEntity partnerEntity) {
        return PartnerDto.builder()
                .partnerId(partnerEntity.getPartnerId())
                .password(partnerEntity.getPassword())
                .phone(partnerEntity.getPhone())
                .memberType(partnerEntity.getMemberType())
                .registerDate(partnerEntity.getRegisterDate())
                .updateDate(partnerEntity.getUpdateDate())
                .build();
    }
}
