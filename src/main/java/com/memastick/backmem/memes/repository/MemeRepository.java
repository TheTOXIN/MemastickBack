package com.memastick.backmem.memes.repository;

import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemeRepository extends PagingAndSortingRepository<Meme, UUID> {

    Optional<Long> countByMemetickIdAndType(UUID memetickId, MemeType type);

    Optional<Long> countByType(MemeType type);

    @Query("SELECT SUM(m.chromosomes) FROM Meme m WHERE m.memetick.id = :memetickId")
    Optional<Long> sumChromosomeByMemetickId(@Param("memetickId") UUID memetickId);

    @Query("SELECT SUM(m.chromosomes) FROM Meme m")
    Optional<Long> sumChromosome();

    List<Meme> findByType(MemeType type, Pageable pageable);

    List<Meme> findByMemetick(Memetick currentMemetick, Pageable pageable);

    Meme findByEvolveMeme(EvolveMeme evolve);

    @Query(
        nativeQuery = true,
        value = "SELECT * FROM memes m " +
            "INNER JOIN evolve_memes em ON em.id = m.evolve_meme_id " +
            "WHERE em.population = :population ORDER BY m.chromosomes DESC LIMIT 1"
    )

    Meme findSuperMeme(@Param("population") long population);
}
