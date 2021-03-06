package com.memastick.backmem.memes.controller;

import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.memes.dto.MemeLohDTO;
import com.memastick.backmem.memes.repository.MemeLohRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memes.service.MemeLohService;
import com.memastick.backmem.security.constant.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.memastick.backmem.main.util.ValidationUtil.validLoh;

@RestController
@RequiredArgsConstructor
@RequestMapping("memes-loh")
public class MemeLohController {

    private final MemeLohService memeLohService;
    private final MemeRepository memeRepository;

    @GetMapping("/meme/{memeId}")
    public ResponseEntity<MemeLohDTO> readByMeme(@PathVariable("memeId") UUID memeId) {
        return ResponseEntity.ok(memeLohService.readByMeme(memeId));
    }

    @PostMapping("/meme/{memeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity saveByMeme(
        @PathVariable("memeId") UUID memeId,
        @RequestBody MemeLohDTO dto
    ) {
        memeLohService.saveByMeme(
            memeRepository.tryFindById(memeId),
            dto
        );

        return ResponseEntity.ok().build();
    }
}
