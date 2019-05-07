package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.entity.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class CellSmallException extends AbstractException {

    public CellSmallException() {
        super(ErrorCode.CELL_SMALL, "Cell not ready");
    }
}