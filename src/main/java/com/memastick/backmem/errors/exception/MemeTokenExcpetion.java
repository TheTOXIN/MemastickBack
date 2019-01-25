package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.entity.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class MemeTokenExcpetion extends AbstractException {

    public MemeTokenExcpetion() {
        super(ErrorCode.LESS_TOKEN);
    }

}
