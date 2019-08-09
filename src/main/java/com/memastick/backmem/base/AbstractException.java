package com.memastick.backmem.base;

import com.memastick.backmem.errors.api.ResponseErrorAPI;
import com.memastick.backmem.errors.consts.ErrorCode;
import lombok.Getter;

public abstract class AbstractException extends RuntimeException {

    @Getter
    private ResponseErrorAPI response = new ResponseErrorAPI();

    public AbstractException(ErrorCode code, String message, String cause) {
        response.setCode(code);
        response.setCause(cause);
        response.setMessage(message);
    }

    public AbstractException(ErrorCode code, String message) {
        response.setCode(code);
        response.setMessage(message);
    }

    public AbstractException(ErrorCode code) {
        response.setCode(code);
    }

}
