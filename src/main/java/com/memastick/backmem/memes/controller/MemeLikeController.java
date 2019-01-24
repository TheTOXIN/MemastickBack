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

    @GetMapping("read/{id}")
    public MemeLikeStateAPI read(@PathVariable("id") UUID id) {
        return memeLikeService.readStateById(id);
    }

    @PatchMapping("trigger/{id}")
    public ResponseEntity trigger(@PathVariable("id") UUID id) {
        memeLikeService.likeTrigger(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("chromosome/{id}/{count}")
    public ResponseEntity chromosome(
        @PathVariable("id") UUID id,
        @PathVariable("count") int count
    ) {
        memeLikeService.chromosomeTrigger(id, count);
        return ResponseEntity.ok().build();
    }

}
