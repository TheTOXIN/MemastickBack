package com.memastick.backmem.memes.repository;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.errors.exception.EntityExistException;
import com.memastick.backmem.memes.entity.MemeComment;
import com.memastick.backmem.memes.entity.MemeCommentVote;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface MemeCommentVoteRepository extends JpaRepository<MemeCommentVote, UUID> {

    @EntityGraph("joinedComment")
    List<MemeCommentVote> findByCommentInAndMemetick(List<MemeComment> comments, Memetick memetick);

    Optional<MemeCommentVote> findByCommentAndMemetick(MemeComment comment, Memetick memetick);

    Optional<Long> countByCommentAndVote(MemeComment comment, boolean vote);
}
