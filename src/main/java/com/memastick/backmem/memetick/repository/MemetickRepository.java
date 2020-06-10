package com.memastick.backmem.memetick.repository;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memetick.entity.Memetick;
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

    @Query("SELECT m.dna FROM Memetick m WHERE m.id = :id")
    Optional<Long> findDnaByMemetickId(@Param("id") UUID id);

    Optional<Memetick> findByNick(String nick);

    @Query("SELECT m.cookies FROM Memetick m WHERE m.id = :memetickId")
    Optional<Integer> findCookieByMemetickId(@Param("memetickId") UUID memetickId);

    @Query("SELECT m.nick FROM Memetick m WHERE m.id = :memetickId")
    Optional<String> findNickByMemetickId(@Param("memetickId") UUID memetickId);

    default Memetick tryFindById(UUID memtickId) {
        return this
            .findById(memtickId)
            .orElseThrow(() -> new EntityNotFoundException(Memetick.class, "id"));
    }
}
