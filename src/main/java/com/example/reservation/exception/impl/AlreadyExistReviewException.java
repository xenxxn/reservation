package com.example.reservation.exception.impl;

import com.example.reservation.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class AlreadyExistReviewException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "작성이 완료된 리뷰입니다.";
    }
}
