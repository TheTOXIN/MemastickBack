package com.memastick.backmem.battle.repository;

import com.memastick.backmem.battle.entity.BattleRating;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BattleRatingRepository extends PagingAndSortingRepository<BattleRating, UUID> {

    Optional<BattleRating> findByMemetick(Memetick memetick);
}
