package com.example.reservation.dto.reservation;

import com.example.reservation.constants.ReservationStatus;
import com.example.reservation.entity.ReservationEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@Builder
public class ReservationDto {
    private Long id;
    private String memberId;
    private String phone;
    private String partnerId;
    private String storeName;
    private Integer headCount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private LocalDateTime statusUpdatedAt;
    private LocalDateTime time;

    public static ReservationDto fromEntity(ReservationEntity reservationEntity) {
        return ReservationDto.builder()
                .id(reservationEntity.getId())
                .memberId(reservationEntity.getMemberId())
                .phone(reservationEntity.getPhone())
                .partnerId(reservationEntity.getPartnerId())
                .storeName(reservationEntity.getStoreName())
                .headCount(reservationEntity.getHeadCount())
                .status(reservationEntity.getStatus())
                .statusUpdatedAt(reservationEntity.getStatusUpdatedAt())
                .time(reservationEntity.getTime())
                .build();
    }

}
