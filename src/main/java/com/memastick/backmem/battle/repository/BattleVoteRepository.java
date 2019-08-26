package com.memastick.backmem.battle.repository;

import com.memastick.backmem.battle.entity.BattleVote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BattleVoteRepository extends CrudRepository<BattleVote, UUID> {

    @Query("SELECT bv.battleId FROM BattleVote bv WHERE bv.battleId = :memetickId")
    List<UUID> findAllBattleIds(@Param("memetickId") UUID memetickId);
}
