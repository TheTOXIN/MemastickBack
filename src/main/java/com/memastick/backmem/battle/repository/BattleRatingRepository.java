package com.memastick.backmem.battle.repository;

import com.memastick.backmem.battle.entity.BattleRating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BattleRatingRepository extends CrudRepository<BattleRating, UUID> {
}
