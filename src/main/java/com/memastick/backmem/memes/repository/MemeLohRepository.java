package com.memastick.backmem.memes.repository;

import com.memastick.backmem.main.projection.MemeLohSum;
import com.memastick.backmem.memes.dto.MemeLohDTO;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLoh;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.util.List;
import java.util.UUID;

@Repository
public interface MemeLohRepository extends JpaRepository<MemeLoh, UUID> {

    @Query("SELECT sum(ml.lol) as lol, sum(ml.omg) as omg, sum(ml.hmm) as hmm FROM MemeLoh ml WHERE ml.meme.id = :memeId")
    MemeLohSum sumByMemeId(@Param("memeId") UUID memeId);

    boolean existsByMemeAndMemetick(Meme meme, Memetick memetick);
}
