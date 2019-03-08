package com.memastick.backmem.memes.controller;

import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.constant.MemeFilter;
import com.memastick.backmem.memes.dto.MemeAPI;
import com.memastick.backmem.memes.service.MemeService;
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

    @Autowired
    public MemeController(
        MemeService memeService
    ) {
        this.memeService = memeService;
    }

    @GetMapping("read/{id}")
    public MemeAPI read(@PathVariable("id") UUID memeId) {
        return memeService.read(memeId);
    }

    @GetMapping(value = "/pages/filter/{filter}")
    public List<MemePageAPI> pages(
        @PathVariable("filter") MemeFilter filter,
        Pageable pageable
    ) {
        return memeService.pagesByFilter(filter, pageable);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody MemeCreateAPI request) {
        memeService.create(request);
        return ResponseEntity.ok().build();
    }
}
