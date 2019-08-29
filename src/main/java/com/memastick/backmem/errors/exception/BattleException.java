package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

import java.util.Map;

public class BattleException extends AbstractException {

    private final static Map<ErrorCode, String> MESSAGES = Map.of(
        ErrorCode.BATTLE_REQUEST_ME, "You cant send request to self meme"
    );

    public BattleException(ErrorCode code) {
        super(code, MESSAGES.get(code));
    }

    public BattleException(String message) {
        super(ErrorCode.BATTLE_ERROR, message);
    }
}
