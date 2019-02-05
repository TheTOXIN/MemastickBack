package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.person.entity.Memetick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemeLikeRepository extends JpaRepository<MemeLike, UUID> {

    Optional<MemeLike> findByMemeAndMemetick(Meme meme, Memetick memetick);

    Optional<Long> countByMemeIdAndIsLikeTrue(UUID memeId);

    @Query("SELECT SUM(ml.chromosome) FROM MemeLike ml")
    Optional<Long> sumChromosome();

    @Query("SELECT SUM(ml.chromosome) FROM MemeLike ml WHERE ml.meme.id = :memeId")
    Optional<Long> sumChromosomeByMemeId(@Param("memeId") UUID memeId);

    @Query(
        "SELECT SUM(ml.chromosome) FROM MemeLike ml WHERE ml.meme.id IN (" +
            "SELECT m.id FROM Meme m WHERE m.memetick.id = :memetickId)"
    )
    Optional<Long> sumChromosomeByMemetickId(@Param("memetickId") UUID memetickId);

}
