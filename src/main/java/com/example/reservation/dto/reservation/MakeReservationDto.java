package com.example.reservation.dto.reservation;

import com.example.reservation.constants.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MakeReservationDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private String memberId;
        private String storeName;
        private Integer headCount;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate date;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime time;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String memberId;
        private String phone;
        private String storeName;

        private Integer headCount;

        @Enumerated(EnumType.STRING)
        private ReservationStatus status;
        private LocalDateTime statusUpdatedAt;


        private LocalDateTime time;

        public static Response fromDto(ReservationDto reservationDto){
            return Response.builder()
                    .memberId(reservationDto.getMemberId())
                    .phone(reservationDto.getPhone())
                    .storeName(reservationDto.getStoreName())
                    .headCount(reservationDto.getHeadCount())
                    .status(reservationDto.getStatus())
                    .statusUpdatedAt(LocalDateTime.now())
                    .time(reservationDto.getTime())
                    .build();
        }

    }
}
