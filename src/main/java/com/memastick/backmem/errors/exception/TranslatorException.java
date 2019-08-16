package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class TranslatorException extends AbstractException {

    public TranslatorException() {
        super(ErrorCode.NOT_ACCEPTABLE);
    }
}
