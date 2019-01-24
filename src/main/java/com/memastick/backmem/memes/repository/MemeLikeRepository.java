package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.person.entity.Memetick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemeLikeRepository extends JpaRepository<MemeLike, UUID> {

    MemeLike findByMemeAndMemetick(Meme meme, Memetick memetick);

    long countByMemeAndLikeTrue(Meme meme);

    long sumChromosomeByMeme(Meme meme);

    long sumChromosome();

    long sumChromosomeByMemetickId(UUID memetickId);

}
