package com.memastick.backmem.memes.controller;

import com.memastick.backmem.memes.api.MemeLikeStateAPI;
import com.memastick.backmem.memes.service.MemeLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("read/{memId}")
    public MemeLikeStateAPI read(@PathVariable("memId") UUID memId) {
        return memeLikeService.readStateById(memId);
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
