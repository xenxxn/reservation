package com.example.reservation.exception.impl;

import com.example.reservation.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NotCompleteReservationException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "예약이 완료되지 않았습니다. 완료 후 리뷰 작성 부탁드립니다.";
    }
}
