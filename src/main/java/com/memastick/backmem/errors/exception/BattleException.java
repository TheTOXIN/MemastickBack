package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class BattleException extends AbstractException {

    public BattleException(String message) {
        super(ErrorCode.BATTLE_ERROR, message);
    }
}
