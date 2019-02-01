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

    long countByMemeIdAndIsLikeTrue(UUID memeId);

    @Query("SELECT SUM(ml.chromosome) FROM MemeLike ml")
    long sumChromosome();

    @Query("SELECT SUM(ml.chromosome) FROM MemeLike ml WHERE ml.memetick.id = :memetickId")
    long sumChromosomeByMemetickId(@Param("memetickId") UUID memetickId);

    @Query("SELECT SUM(ml.chromosome) FROM MemeLike ml WHERE ml.meme.id = :memeId")
    long sumChromosomeByMemeId(@Param("memeId") UUID memeId);

}
