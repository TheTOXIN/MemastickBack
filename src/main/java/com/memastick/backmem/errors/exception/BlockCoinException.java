package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class BlockCoinException extends AbstractException {

    public BlockCoinException(String text) {
        super(ErrorCode.MINE_FAIL, text);
    }
}
