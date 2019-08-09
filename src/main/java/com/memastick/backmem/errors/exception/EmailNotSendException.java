package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class EmailNotSendException extends AbstractException {

    public EmailNotSendException(String email) {
        super(ErrorCode.EMAIL_NOT_SEND, "Not send email - " + email);
    }
}
