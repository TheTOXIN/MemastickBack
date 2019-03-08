package com.memastick.backmem.evolution.controller;

import com.memastick.backmem.evolution.api.EvolveMemeAPI;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("evolve-memes")
public class EvolveMemeController {

    private final EvolveMemeService evolveMemeService;

    @Autowired
    public EvolveMemeController(
        EvolveMemeService evolveMemeService
    ) {
        this.evolveMemeService = evolveMemeService;
    }

    @GetMapping("meme/{id}")
    public EvolveMemeAPI readByMeme(@PathVariable("id") UUID memeId) {
        return evolveMemeService.readByMeme(memeId);
    }

    @PatchMapping("/chance/meme/{id}")
    public ResponseEntity chance(@PathVariable("id") UUID memeId) {
        evolveMemeService.chance(memeId);
        return ResponseEntity.ok().build();
    }
}
