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
import com.memastick.backmem.memetick.repository.MemetickRepository;
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

import static com.memastick.backmem.main.util.TextUtil.clearSpaces;

@Service
@RequiredArgsConstructor
public class MemeCommentService {

    private final OauthData oauthData;
    private final MemeRepository memeRepository;
    private final MemetickRepository memetickRepository;
    private final MemeCommentRepository commentRepository;
    private final MemeCommentVoteRepository voteRepository;

    @Transactional
    public void createComment(Meme meme, String comment) {
        Memetick memetick = oauthData.getCurrentMemetick();

        boolean invalid = !ValidationUtil.validText(comment);
        if (invalid) throw new ValidationException(ErrorCode.MEME_COMMENT);

        boolean exists = commentRepository.existsByMemeAndMemetick(meme, memetick);
        if (exists) throw new EntityExistException(MemeComment.class);

        MemeComment memeComment = new MemeComment(meme, memetick, clearSpaces(comment));
        MemeComment saveComment = commentRepository.save(memeComment);

        if (meme.getCommentId() == null) {
            memeRepository.updateSetComment(saveComment.getId(), meme.getId());
        }
    }

    @Transactional(readOnly = true)
    public List<MemeCommentAPI> readComments(UUID memeId, Pageable pageable) {
        Memetick memetick = oauthData.getCurrentMemetick();

        List<MemeComment> comments = commentRepository.findAllByMemeId(memeId, pageable);

        Map<UUID, Boolean> myVotes = voteRepository.findByCommentsAndMemetick(comments, memetick)
            .stream()
            .collect(Collectors.toMap(MemeCommentVote::getCommentId, MemeCommentVote::isVote, (c1, c2) -> c1));

        List<UUID> memetickIds = comments
            .stream()
            .map(MemeComment::getMemetickId)
            .collect(Collectors.toList());

        Map<UUID, String> nickMap = memetickRepository.findAllById(memetickIds).stream().collect(Collectors.toMap(
            AbstractEntity::getId,
            Memetick::getNick
        ));

        return comments
            .stream()
            .map(comment -> new MemeCommentAPI(
                comment,
                myVotes.get(comment.getId()),
                nickMap.get(comment.getMemetickId()))
            )
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
        comment.setPoint(comment.getPoint() + (voteValue ? 1 : -1));

        voteRepository.save(vote);
        commentRepository.save(comment);

        MemeComment bestComment = commentRepository.findBestComment(comment.getMemeId());
        memeRepository.updateSetComment(bestComment.getId(), bestComment.getMemeId());
    }
}
