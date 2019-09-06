package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class MemeCoinNotEnoughException extends AbstractException {

    public MemeCoinNotEnoughException() {
        super(ErrorCode.MEME_COIN_ENOUGH, "Not enough meme coins");
    }
}
