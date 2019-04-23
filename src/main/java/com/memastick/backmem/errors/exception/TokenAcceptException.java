package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.entity.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class TokenAcceptException extends AbstractException {

    public TokenAcceptException(ErrorCode code) {
        super(code, "Error accept token");
    }
}
