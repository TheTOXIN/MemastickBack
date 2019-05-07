package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemeRepository extends JpaRepository<Meme, UUID> {

    Optional<Long> countByMemetickIdAndType(UUID memetickId, MemeType type);

    Optional<Long> countByType(MemeType type);

    Optional<Long> countByPopulation(long population);

    @Query("SELECT SUM(m.chromosomes) FROM Meme m WHERE m.memetick.id = :memetickId")
    Optional<Long> sumChromosomeByMemetickId(@Param("memetickId") UUID memetickId);

    @Query("SELECT SUM(m.chromosomes) FROM Meme m")
    Optional<Long> sumChromosome();

    Optional<Meme> findByPopulationAndIndexer(long population, long indexer);

    List<Meme> findByType(MemeType individ, Pageable pageable);

    List<Meme> findByMemetick(Memetick currentMemetick, Pageable pageable);

    @Query("SELECT m FROM Meme m WHERE :day - m.population = :step")
    List<Meme> findAllByStepEvolveDay(
        @Param("day") long day,
        @Param("step") long step,
        Pageable pageable
    );

    @Query(
        nativeQuery = true,
        value = "SELECT * FROM memes m WHERE m.population = :population ORDER BY m.chromosomes DESC LIMIT 1"
    )//TODO check
    Meme findSuperMeme(@Param("population") long population);
}
