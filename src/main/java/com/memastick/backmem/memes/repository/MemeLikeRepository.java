package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLike;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemeLikeRepository extends JpaRepository<MemeLike, UUID> {

    Optional<MemeLike> findByMemeAndMemetick(Meme meme, Memetick memetick);

    List<MemeLike> findByMemetickAndIsLikeTrue(Memetick memetick, Pageable pageable);
}
