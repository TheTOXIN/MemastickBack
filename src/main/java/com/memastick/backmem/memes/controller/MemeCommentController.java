package com.memastick.backmem.memes.controller;

import com.memastick.backmem.memes.api.MemeCommentAPI;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memes.service.MemeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("memes-comment")
public class MemeCommentController {

    private final MemeCommentService memeCommentService;
    private final MemeRepository memeRepository;

    @PostMapping("/meme/{memeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createByMeme(
        @PathVariable("memeId") UUID memeId,
        @RequestBody String comment
    ) {
        memeCommentService.createComment(
            memeRepository.tryFindById(memeId),
            comment
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/meme/{memeId}")
    public List<MemeCommentAPI> readByMeme(
        @PathVariable("memeId") UUID memeId,
        Pageable pageable
    ) {
        return memeCommentService.readComments(memeId, pageable);
    }

    @PatchMapping("/vote/{commentId}")
    public ResponseEntity voteByComment(
        @PathVariable("commentId") UUID commentId,
        @RequestBody String vote
    ) {
        memeCommentService.voteComment(commentId, Boolean.valueOf(vote));
        return ResponseEntity.ok().build();
    }
}
