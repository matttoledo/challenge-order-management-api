package com.mouts.orders_manegement_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "id and products could not be empty")
public class EmptyParamException extends RuntimeException {

    public EmptyParamException(String message) {
        super(message);
    }

    public EmptyParamException(String message, Throwable cause) {
        super(message, cause);
    }
}