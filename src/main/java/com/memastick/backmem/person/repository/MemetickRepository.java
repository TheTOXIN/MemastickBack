package com.memastick.backmem.person.repository;

import com.memastick.backmem.person.entity.Memetick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemetickRepository extends JpaRepository<Memetick, UUID> {

    @Query("SELECT SUM(m.dna) FROM Memetick m")
    Optional<Long> sumDna();

    @Query("SELECT SUM(m.dna) FROM Memetick m WHERE m.id = :id")
    Optional<Long> sumDnaById(@Param("id") UUID id);

}
