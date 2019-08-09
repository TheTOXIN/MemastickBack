package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class SettingException extends AbstractException {

    public SettingException(ErrorCode code) {
        super(code);
    }

}
