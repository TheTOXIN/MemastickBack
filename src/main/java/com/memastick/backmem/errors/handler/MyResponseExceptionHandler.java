package com.memastick.backmem.errors.handler;

import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.api.ResponseErrorAPI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Log logger = LogFactory.getLog(MyResponseExceptionHandler.class);

    @ExceptionHandler(value = {AbstractException.class})
    public ResponseEntity<ResponseErrorAPI> handleConflict(AbstractException ex) {
        ResponseErrorAPI response = ex.getResponse();
        logger.debug(String.format("%s: %s - %s", response.getCode(), response.getMessage(), response.getCause()));
        return ResponseEntity.status(response.getCode().getStatus()).body(response);
    }
}
