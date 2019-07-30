package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.entity.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class TimeInException extends AbstractException {

    public TimeInException(String message) {
        super(ErrorCode.TIME_IN, message);
    }
}
