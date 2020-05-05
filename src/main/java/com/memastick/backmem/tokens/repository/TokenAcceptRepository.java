package com.memastick.backmem.tokens.repository;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.entity.TokenAccept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenAcceptRepository extends JpaRepository<TokenAccept, UUID> {

    default void create(Meme meme, Memetick memetick, TokenType token) {
        this.save(new TokenAccept(
            memetick.getId(),
            meme.getId(),
            token
        ));
    }

    default boolean checkExist(Memetick memetick, Meme meme, TokenType token) {
        return this.existsByMemetickIdAndMemeIdAndToken(
            memetick.getId(),
            meme.getId(),
            token
        );
    }

    boolean existsByMemetickIdAndMemeIdAndToken(UUID memetickId, UUID memeId, TokenType token);
}
