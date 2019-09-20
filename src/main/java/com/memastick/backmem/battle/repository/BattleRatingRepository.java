package com.memastick.backmem.battle.repository;

import com.memastick.backmem.battle.entity.BattleRating;
import com.memastick.backmem.battle.view.BattleMyRatingView;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BattleRatingRepository extends PagingAndSortingRepository<BattleRating, UUID> {

    Optional<BattleRating> findByMemetick(Memetick memetick);

    @Override
    @EntityGraph("joinedRatingMemetick")
    Page<BattleRating> findAll(Pageable pageable);

    @EntityGraph("joinedRatingMemetick")
    @Query("SELECT br FROM BattleRating br WHERE br.date <= :date")
    List<BattleRating> findAllExpire(@Param("date") LocalDate date);

    @Query(
        value =  "SELECT rank.position FROM (" +
            "SELECT br.memetick_id, ROW_NUMBER() OVER (ORDER BY br.score DESC) AS position FROM battle_rating br" +
            ") AS rank WHERE rank.memetick_id = :memetickId",
        nativeQuery = true
    )
    Integer findMyPosition(@Param("memetickId") UUID memetickId);
}
