package com.memastick.backmem.memotype.repository;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memotype.entity.Memotype;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemotypeRepository extends CrudRepository<Memotype, UUID> {

    default Memotype tryFindById(UUID memotypeId) {
        return this
            .findById(memotypeId)
            .orElseThrow(() -> new EntityNotFoundException(Memotype.class, "id"));
    }
}
