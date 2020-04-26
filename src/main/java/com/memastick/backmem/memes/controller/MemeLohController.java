package com.memastick.backmem.memes.controller;

import com.memastick.backmem.memes.dto.MemeLohDTO;
import com.memastick.backmem.memes.service.MemeLohService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("memes-loh")
public class MemeLohController {

    private final MemeLohService memeLohService;

    @GetMapping("/meme/{memeId}")
    public ResponseEntity<MemeLohDTO> readByMeme(
        @PathVariable("memeId") UUID memeId
    ) {
        return ResponseEntity.ok(
            memeLohService.readByMeme(memeId)
        );
    }

    @PostMapping("/meme/{memeId}")
    public ResponseEntity saveByMeme(
        @PathVariable("memeId") UUID memeId,
        @RequestBody MemeLohDTO dto
    ) {
        memeLohService.saveByMeme(memeId, dto);
        return ResponseEntity.ok().build();
    }
}
