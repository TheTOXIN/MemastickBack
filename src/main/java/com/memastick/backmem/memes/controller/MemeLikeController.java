package com.memastick.backmem.memes.controller;

import com.memastick.backmem.memes.api.MemeLikeAPI;
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

    @GetMapping("read/{fireId}")
    public MemeLikeAPI read(@PathVariable("fireId") UUID fireId) {
        return memeLikeService.readByFireId(fireId);
    }

    @PatchMapping("trigger/{fireId}")
    public ResponseEntity trigger(@PathVariable("fireId") UUID fireId) {
        memeLikeService.likeTrigger(fireId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("chromosome/{fireId}/{count}")
    public ResponseEntity chromosome(
        @PathVariable("fireId") UUID fireId,
        @PathVariable("count") int count
    ) {
        memeLikeService.chromosomeTrigger(fireId, count);
        return ResponseEntity.ok().build();
    }

}
