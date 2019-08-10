package com.memastick.backmem.errors.consts;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum  ErrorCode {

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND),
    TIME_IN(HttpStatus.GATEWAY_TIMEOUT),
    TIME_OUT(HttpStatus.GATEWAY_TIMEOUT),
    IMAGE_FORMAT(HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_NICK(HttpStatus.UNPROCESSABLE_ENTITY),
    EXPIRE_NICK(HttpStatus.GATEWAY_TIMEOUT),
    LESS_TOKEN(HttpStatus.LOCKED),
    ALLOWANCE_EMPTY(HttpStatus.NOT_FOUND),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE),
    TOKEN_SELF(HttpStatus.LOCKED),
    CELL_SMALL(HttpStatus.LOCKED),
    EMAIL_NOT_SEND(HttpStatus.INTERNAL_SERVER_ERROR),
    MEME_COIN_ENOUGH(HttpStatus.LOCKED),
    MINE_FAIL(HttpStatus.UNPROCESSABLE_ENTITY);

    @Getter
    private HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }

}
