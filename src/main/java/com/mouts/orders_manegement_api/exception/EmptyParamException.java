package com.mouts.orders_manegement_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyParamException extends RuntimeException {

    public EmptyParamException(String message) {
        super(message);
    }
}
