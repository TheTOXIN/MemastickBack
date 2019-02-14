package com.memastick.backmem.evolution.repository;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.memes.entity.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EvolveMemeRepository extends JpaRepository<EvolveMeme, UUID> {

    List<EvolveMeme> findByStep(EvolveStep step);

    EvolveMeme findByMeme(Meme meme);

}
