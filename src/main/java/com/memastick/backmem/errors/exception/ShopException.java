package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class ShopException extends AbstractException {

    public ShopException(ErrorCode code) {
        super(code);
    }
}
