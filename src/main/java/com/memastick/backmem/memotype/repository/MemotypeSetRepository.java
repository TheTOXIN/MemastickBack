package com.memastick.backmem.memotype.repository;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemotypeSetRepository extends CrudRepository<MemotypeSet, UUID> {

    Optional<MemotypeSet> findByName(String name);

    default MemotypeSet tryFindByName(String name) {
        return this
            .findByName(name)
            .orElseThrow(() -> new EntityNotFoundException(MemotypeSet.class, "name"));
    }

    @Query("SELECT ms.name FROM MemotypeSet ms WHERE ms.id = :id")
    String findNameById(@Param("id") UUID setId);
}
