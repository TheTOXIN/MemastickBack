package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.entity.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class MemetickAvatarException extends AbstractException {

    public MemetickAvatarException(String message) {
        super(ErrorCode.IMAGE_FORMAT, message);
    }

}
