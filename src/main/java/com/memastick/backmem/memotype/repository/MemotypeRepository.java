package com.memastick.backmem.memotype.repository;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memotype.entity.Memotype;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface MemotypeRepository extends CrudRepository<Memotype, UUID> {

    default Memotype tryFindById(UUID memotypeId) {
        return this
            .findById(memotypeId)
            .orElseThrow(() -> new EntityNotFoundException(Memotype.class, "id"));
    }

    default List<Memotype> findAllMemotypes() {
        return StreamSupport
            .stream(this.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
