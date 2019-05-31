package com.memastick.backmem.memes.controller;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.api.MemeImgAPI;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.constant.MemeFilter;
import com.memastick.backmem.memes.service.MemeService;
import com.memastick.backmem.memes.service.MemesCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("memes")
public class MemeController {

    private final MemeService memeService;
    private final MemesCreateService createService;

    @Autowired
    public MemeController(
        MemeService memeService,
        MemesCreateService createService
    ) {
        this.memeService = memeService;
        this.createService = createService;
    }

    @GetMapping("/img/{id}")
    public MemeImgAPI readImg(@PathVariable("id") UUID memeId) {
        return memeService.readImg(memeId);
    }

    @GetMapping("/page/{id}")
    public MemePageAPI page(@PathVariable("id") UUID memeId) {
        return memeService.page(memeId);
    }

    @GetMapping(value = "/pages")
    public List<MemePageAPI> pages(
        @RequestParam(name = "filter", required = false) MemeFilter filter,
        @RequestParam(name = "step", required = false) EvolveStep step,
        @RequestParam(name = "memetick", required = false) UUID memetickId,
        Pageable pageable
    ) {
        // TODO refactor to DTO
        return memeService.pages(
            filter,
            step,
            memetickId,
            pageable
        );
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody MemeCreateAPI request) {
        createService.create(request);
        return ResponseEntity.ok().build();
    }
}
