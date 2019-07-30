package com.memastick.backmem.evolution.controller;

import com.memastick.backmem.evolution.api.EvolveMemeAPI;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("meme/{memeId}")
    public EvolveMemeAPI readByMeme(@PathVariable("memeId") UUID memeId) {
        return evolveMemeService.readByMeme(memeId);
    }

    @GetMapping("meme/chance/{memeId}")
    public Float readChance(@PathVariable("memeId") UUID memeId) {
        return evolveMemeService.readChance(memeId);
    }

    @PatchMapping("meme/resurrect/{memeId}")
    public void resurrectMeme(@PathVariable("memeId") UUID memeId) {
        evolveMemeService.resurrectMeme(memeId);
    }
}
