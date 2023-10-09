package com.example.reservation.exception.impl;

import com.example.reservation.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class RatingLimitException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "최소 1점 ~ 최대 5점 사이로 별점을 매겨주세요.";
    }
}
