package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.base.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class EntityNotFoundException extends AbstractException {

    public EntityNotFoundException(Class<? extends AbstractEntity> entity, String filed) {
        super(
            ErrorCode.ENTITY_NOT_FOUND,
            String.format("Entity %s - not found", entity.getSimpleName()),
            String.format("By: %s", filed)
        );
    }

}
