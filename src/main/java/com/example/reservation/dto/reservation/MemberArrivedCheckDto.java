package com.example.reservation.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberArrivedCheckDto {
    private Long reservationId;
    private String lastPhoneNumber;
}
