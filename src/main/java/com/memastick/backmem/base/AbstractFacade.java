package com.memastick.backmem.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractFacade {

    @PersistenceContext
    protected EntityManager em;
}
