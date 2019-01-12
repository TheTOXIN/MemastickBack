package com.memastick.backmem.base.entity;

import com.memastick.backmem.errors.consts.ErrorCode;

public abstract class AbstractException extends RuntimeException {

    public AbstractException(ErrorCode code, String message) {
        super(String.format("%s: %s", code, message));
    }

}
