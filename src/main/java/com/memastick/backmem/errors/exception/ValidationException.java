package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class ValidationException extends AbstractException {

    public ValidationException(ErrorCode code) {
        super(code);
    }

    public ValidationException(ErrorCode code, String message) {
        super(code, message);
    }

}
