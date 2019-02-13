package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemeRepository extends PagingAndSortingRepository<Meme, UUID> {

    Optional<Long> countByMemetickIdAndType(UUID memetickId, MemeType type);

    Optional<Long> countByType(MemeType type);

    @Query("SELECT MAX(m.chromosomes) FROM Meme m")
    Integer maxChromosomes();
//TODO SUKA BLYAT
    @Query("SELECT MIN(m.chromosomes) FROM Meme m")
    Integer minChromosomes();

}
