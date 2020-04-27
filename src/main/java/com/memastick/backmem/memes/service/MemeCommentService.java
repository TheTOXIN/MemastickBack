package com.memastick.backmem.memes.service;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EntityExistException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.memes.api.MemeCommentAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeComment;
import com.memastick.backmem.memes.entity.MemeCommentVote;
import com.memastick.backmem.memes.repository.MemeCommentRepository;
import com.memastick.backmem.memes.repository.MemeCommentVoteRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemeCommentService {

    private final OauthData oauthData;
    private final MemeRepository memeRepository;
    private final MemeCommentRepository commentRepository;
    private final MemeCommentVoteRepository voteRepository;

    @Transactional
    public void createComment(UUID memeId, String comment) {
        Memetick memetick = oauthData.getCurrentMemetick();
        Meme meme = memeRepository.tryFindById(memeId);

        boolean invalid = !ValidationUtil.validText(comment);
        if (invalid) throw new ValidationException(ErrorCode.MEME_COMMENT);

        boolean exists = commentRepository.existsByMemeAndMemetick(meme, memetick);
        if (exists) throw new EntityExistException(MemeComment.class);

        MemeComment memeComment = new MemeComment(meme, memetick, comment);
        commentRepository.save(memeComment);
    }

    @Transactional(readOnly = true)
    public List<MemeCommentAPI> readComments(UUID memeId, Pageable pageable) {
        Memetick memetick = oauthData.getCurrentMemetick();

        List<MemeComment> comments = commentRepository.findAllByMemeId(memeId, pageable);

        Map<UUID, Boolean> myVotes = voteRepository.findByCommentInAndMemetick(comments, memetick)
            .stream()
            .collect(Collectors.toMap(v -> v.getComment().getId(), MemeCommentVote::isVote));

        return comments
            .stream()
            .map(c -> new MemeCommentAPI(c, myVotes.get(c.getId())))
            .collect(Collectors.toList());
    }

    @Transactional
    public void voteComment(UUID commentId, boolean voteValue) {
        Memetick memetick = oauthData.getCurrentMemetick();
        MemeComment comment = commentRepository.tryFindById(commentId);

        Optional<MemeCommentVote> optionalVote = voteRepository.findByCommentAndMemetick(comment, memetick);
        MemeCommentVote vote = optionalVote.orElse(new MemeCommentVote(comment, memetick));

        if (optionalVote.isPresent() && vote.isVote() == voteValue) return;

        vote.setVote(voteValue);
        voteRepository.save(vote);

        computePoint(comment);
    }

    private void computePoint(MemeComment comment) {
        Long approve = voteRepository.countByCommentAndVote(comment, true).orElse(0L);
        Long disapprove = voteRepository.countByCommentAndVote(comment, false).orElse(0L);

        int point = (int) (approve + (disapprove * -1));
        comment.setPoint(point);

        commentRepository.save(comment);
    }
}
