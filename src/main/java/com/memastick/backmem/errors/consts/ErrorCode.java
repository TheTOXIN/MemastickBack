package com.memastick.backmem.errors.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND),
    ENTITY_EXIST(HttpStatus.UNPROCESSABLE_ENTITY),
    TIME_IN(HttpStatus.GATEWAY_TIMEOUT),
    TIME_OUT(HttpStatus.GATEWAY_TIMEOUT),
    IMAGE_FORMAT(HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_NICK(HttpStatus.UNPROCESSABLE_ENTITY),
    EXPIRE_NICK(HttpStatus.GATEWAY_TIMEOUT),
    EXIST_NICK(HttpStatus.NOT_ACCEPTABLE),
    LESS_TOKEN(HttpStatus.LOCKED),
    ALLOWANCE_EMPTY(HttpStatus.NOT_FOUND),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE),
    TOKEN_SELF(HttpStatus.LOCKED),
    CELL_SMALL(HttpStatus.UNPROCESSABLE_ENTITY),
    EMAIL_INVALID(HttpStatus.UNPROCESSABLE_ENTITY),
    EMAIL_NOT_SEND(HttpStatus.INTERNAL_SERVER_ERROR),
    MEME_COIN_ENOUGH(HttpStatus.LOCKED),
    MINE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR),
    MINE_END(HttpStatus.UNPROCESSABLE_ENTITY),
    BATTLE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    BATTLE_REQUEST_ME(HttpStatus.UNPROCESSABLE_ENTITY),
    BATTLE_COOKIE(HttpStatus.UNPROCESSABLE_ENTITY),
    MEME_LOH(HttpStatus.UNPROCESSABLE_ENTITY),
    MEME_COMMENT(HttpStatus.UNPROCESSABLE_ENTITY);

    @Getter
    private HttpStatus status;
}
