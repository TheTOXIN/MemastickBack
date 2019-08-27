package com.memastick.backmem.battle.repository;

import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.entity.BattleVote;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BattleVoteRepository extends CrudRepository<BattleVote, UUID> {

    @Query("SELECT bv.battle.id FROM BattleVote bv WHERE bv.memetick.id = :memetickId")
    List<UUID> findAllBattleIds(@Param("memetickId") UUID memetickId);

    Optional<BattleVote> findByBattleAndMemetick(Battle battle, Memetick memetick);
}
