package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLoh;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemeLohRepository extends JpaRepository<MemeLoh, UUID> {

    List<MemeLoh> findAllByMemeId(UUID memeId);

    boolean existsByMemeAndMemetick(Meme meme, Memetick memetick);
}
