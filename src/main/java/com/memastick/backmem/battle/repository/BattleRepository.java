package com.memastick.backmem.battle.repository;

import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BattleRepository extends CrudRepository<Battle, UUID> {

    @Query("SELECT b FROM Battle b WHERE b.forward.memetickId = :forward AND b.defender.memetickId = :defender")
    Optional<Battle> findByMembers(@Param("forward") UUID forward, @Param("defender") UUID defender);

    @EntityGraph("joinedMembers")
    default Battle findBattleById(UUID battleId) {
        return this
            .findById(battleId)
            .orElseThrow(() -> new EntityNotFoundException(Battle.class, "id"));
    }
}
