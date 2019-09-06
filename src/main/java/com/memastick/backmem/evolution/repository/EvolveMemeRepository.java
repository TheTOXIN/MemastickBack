package com.memastick.backmem.evolution.repository;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.memes.entity.Meme;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EvolveMemeRepository extends JpaRepository<EvolveMeme, UUID> {

    @EntityGraph("joinedMeme")
    List<EvolveMeme> findByStep(EvolveStep step);

    @EntityGraph("joinedMeme")
    List<EvolveMeme> findByStep(EvolveStep step, Pageable pageable);

    @EntityGraph("joinedMeme")
    @Query("SELECT em FROM EvolveMeme em WHERE em.meme.type = com.memastick.backmem.memes.constant.MemeType.SLCT")
    List<EvolveMeme> findAllSelection();

    @EntityGraph("joinedMeme")
    EvolveMeme findByMemeId(UUID memeId);

    @EntityGraph("joinedMeme")
    EvolveMeme findByMeme(Meme meme);

    @Query("SELECT em.step FROM EvolveMeme em WHERE em.meme = :meme")
    Object findStepByMeme(@Param("meme") Meme meme);
}
