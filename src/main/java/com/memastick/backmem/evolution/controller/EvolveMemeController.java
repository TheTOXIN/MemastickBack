package com.memastick.backmem.evolution.controller;

import com.memastick.backmem.evolution.api.EvolveMemeAPI;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("evolve-memes")
@AllArgsConstructor
public class EvolveMemeController {

    private final EvolveMemeService evolveMemeService;

    @GetMapping("meme/{memeId}")
    public EvolveMemeAPI readByMeme(@PathVariable("memeId") UUID memeId) {
        return evolveMemeService.readByMeme(memeId);
    }

    @GetMapping("meme/chance/{memeId}")
    public Float readChance(@PathVariable("memeId") UUID memeId) {
        return evolveMemeService.readChance(memeId);
    }
}
