package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.entity.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class TokenWalletException extends AbstractException {

    public TokenWalletException() {
        super(ErrorCode.LESS_TOKEN);
    }

}
