package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
