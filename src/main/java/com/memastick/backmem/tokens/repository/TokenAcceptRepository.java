package com.memastick.backmem.tokens.repository;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.tokens.entity.TokenAccept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenAcceptRepository extends JpaRepository<TokenAccept, UUID> {

    default void create(Meme meme, Memetick memetick) {
        this.save(new TokenAccept(
           memetick.getId(),
           meme.getId()
        ));
    }

    default boolean existsByMemetickAndMeme(Memetick memetick, Meme meme) {
        return this.existsByMemetickIdAndMemeId(
            memetick.getId(),
            meme.getId()
        );
    }

    boolean existsByMemetickIdAndMemeId(UUID memetickId, UUID memeId);
}
