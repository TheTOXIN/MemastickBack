package com.memastick.backmem.errors.exception;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.base.entity.AbstractException;
import com.memastick.backmem.errors.consts.ErrorCode;

public class EntityNotFound extends AbstractException {

    public EntityNotFound(Class<? extends AbstractEntity> entity) {
        super(
            ErrorCode.ENTITY_NOT_FOUND,
            String.format("Entity %s - not found", entity.getSimpleName())
        );
    }

}
