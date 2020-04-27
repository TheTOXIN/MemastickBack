package com.memastick.backmem.memes.repository;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeComment;
import com.memastick.backmem.memes.entity.MemeCommentVote;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemeCommentRepository extends JpaRepository<MemeComment, UUID> {

    List<MemeComment> findAllByMemeId(UUID memeId, Pageable pageable);

    default MemeComment tryFindById(UUID commentId) {
        return this
            .findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException(MemeComment.class, "id"));
    }

    boolean existsByMemeAndMemetick(Meme meme, Memetick memetick);
}
