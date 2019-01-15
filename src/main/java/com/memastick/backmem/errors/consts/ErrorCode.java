package com.memastick.backmem.errors.consts;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum  ErrorCode {

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND),
    TIME_IN(HttpStatus.GATEWAY_TIMEOUT),
    TIME_OUT(HttpStatus.GATEWAY_TIMEOUT);

    @Getter
    private HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }

}
