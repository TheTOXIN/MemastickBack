package com.memastick.backmem.memes.controller;

import com.memastick.backmem.memes.service.MemeLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("meme-likes")
public class MemeLikeController {

    private final MemeLikeService memeLikeService;

    @Autowired
    public MemeLikeController(
        MemeLikeService memeLikeService
    ) {
        this.memeLikeService = memeLikeService;
    }

    @PatchMapping("trigger/{memId}")
    public ResponseEntity trigger(@PathVariable("memId") UUID memId) {
        memeLikeService.likeTrigger(memId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("chromosome/{memId}/{count}")
    public ResponseEntity chromosome(
        @PathVariable("memId") UUID memId,
        @PathVariable("count") int count
    ) {
        memeLikeService.chromosomeTrigger(memId, count);
        return ResponseEntity.ok().build();
    }

}
