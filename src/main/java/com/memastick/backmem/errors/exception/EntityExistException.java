package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class EntityExistException extends AbstractException {

    public EntityExistException(Class<? extends AbstractEntity> entity) {
        super(
            ErrorCode.ENTITY_EXIST,
            String.format("Entity %s - exist", entity.getSimpleName())
        );
    }
}
