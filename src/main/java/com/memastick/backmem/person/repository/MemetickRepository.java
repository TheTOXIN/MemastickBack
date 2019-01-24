package com.memastick.backmem.person.repository;

import com.memastick.backmem.person.entity.Memetick;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemetickRepository extends CrudRepository<Memetick, UUID> {

    @Query("SELECT SUM(m.dna) FROM Memetick m")
    long sumDna();

    @Query("SELECT SUM(m.dna) FROM Memetick m WHERE m.id = :id")
    long sumDnaById(@Param("id") UUID id);

}
