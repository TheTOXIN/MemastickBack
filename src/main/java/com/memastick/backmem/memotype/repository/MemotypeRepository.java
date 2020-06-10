package com.memastick.backmem.memotype.repository;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import com.memastick.backmem.memotype.entity.Memotype;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface MemotypeRepository extends CrudRepository<Memotype, UUID> {

    @Query(
        value = "SELECT * FROM memotype WHERE rarity = :rarity OFFSET floor(" +
            "random() * (SELECT count(*) from memotype WHERE rarity = :rarity)" +
            ") LIMIT 1",
        nativeQuery = true
    )
    Optional<Memotype> randomMemotypeByRarity(@Param("rarity") String rarity);

    @Query("SELECT m.image FROM Memotype m WHERE m.id = :memotypeId")
    Optional<String> findImageByMemotypeId(@Param("memotypeId") UUID memotypeId);

    Optional<Long> countByRarity(MemotypeRarity rarity);

    List<Memotype> findAllBySetId(UUID setId);

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
