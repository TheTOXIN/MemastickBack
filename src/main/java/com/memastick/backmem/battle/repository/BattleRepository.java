package com.memastick.backmem.battle.repository;

import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BattleRepository extends CrudRepository<Battle, UUID> {

    Optional<Long> countByStatus(BattleStatus status);

    @Query(
        "SELECT b.id FROM Battle b WHERE b.status = :status AND " +
            "NOT(b.forward.memetickId = :memetickId OR b.defender.memetickId = :memetickId)"
    )
    List<UUID> findAvailableBattleIds(@Param("status") BattleStatus status, @Param("memetickId") UUID memetickId);

    @Query(
        "SELECT b FROM Battle b WHERE " +
            "b.forward.memetickId = :memetickId OR b.defender.memetickId = :memetickId"
    )
    List<Battle> findAllByMemetickId(@Param("memetickId") UUID memetickId);

    @Query(
        "SELECT b FROM Battle b WHERE b.id = :battleId AND " +
            "(b.forward.memetickId = :memetickId OR b.defender.memetickId = :memetickId)"
    )
    Optional<Battle> findByMemetickIdAndBattleId(
        @Param("memetickId") UUID memetickId,
        @Param("battleId") UUID battleId
    );

    @EntityGraph("joinedMembers")
    default Battle tryFindByMemetickAndId(Memetick memetick, UUID battleId) {
        return this
            .findByMemetickIdAndBattleId(memetick.getId(), battleId)
            .orElseThrow(() -> new EntityNotFoundException(Battle.class, "id or memetickId"));
    }

    @EntityGraph("joinedMembers")
    default Battle tryFindById(UUID battleId) {
        return this
            .findById(battleId)
            .orElseThrow(() -> new EntityNotFoundException(Battle.class, "id"));
    }
}
