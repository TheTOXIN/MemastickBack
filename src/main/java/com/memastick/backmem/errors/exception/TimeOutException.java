package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.entity.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class TimeOutException extends AbstractException {

    public TimeOutException(String message) {
        super(ErrorCode.TIME_OUT, message);
    }

}
