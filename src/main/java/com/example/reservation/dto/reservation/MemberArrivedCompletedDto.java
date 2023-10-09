package com.example.reservation.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberArrivedCompletedDto {
    private Long reservationId;
    private String storeName;
    private String memberId;
    private LocalDateTime arrivedTime;
    private LocalDateTime reservationTime;

    public MemberArrivedCompletedDto(ReservationDto reservationDto) {
        this.reservationId = reservationDto.getId();
        this.storeName = reservationDto.getStoreName();
        this.memberId = reservationDto.getMemberId();
        this.arrivedTime = LocalDateTime.now();
        this.reservationTime = reservationDto.getTime();
    }
}
