package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemeRepository extends JpaRepository<Meme, UUID> {

    Optional<Long> countByMemetickIdAndType(UUID memetickId, MemeType type);

    Optional<Long> countByType(MemeType type);

    Optional<Long> countByEvolutionAndPopulation(long evolution, long population);

    @Query("SELECT MAX(m.chromosomes) FROM Meme m WHERE m.type = :type")
    Optional<Long> maxByCromosome(@Param("type") MemeType type);

    @Query("SELECT MIN(m.chromosomes) FROM Meme m WHERE m.type = :type")
    Optional<Long> minByCromosome(@Param("type") MemeType type);

    @Query("SELECT SUM(m.chromosomes) FROM Meme m WHERE m.memetick.id = :memetickId")
    Optional<Long> sumChromosomeByMemetickId(@Param("memetickId") UUID memetickId);

    @Query("SELECT SUM(m.chromosomes) FROM Meme m")
    Optional<Long> sumChromosome();

    Optional<Meme> findByEvolutionAndPopulationAndIndividuation(long evolution, long population, long individuation);

    List<Meme> findByType(MemeType type, Pageable pageable);

    List<Meme> findByMemetick(Memetick memetick, Pageable pageable);

    @Query(
        nativeQuery = true,
        value = "SELECT * FROM memes m WHERE m.evolution = :evolution ORDER BY m.chromosomes DESC LIMIT 1"
    )
    Meme findSuperMeme(@Param("evolution") long evolution);
}
