package com.memastick.backmem.memes.controller;

import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/pages/read")
    public List<MemePageAPI> readPages(Pageable pageable) {
        return memeService.readPages(pageable);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody MemeCreateAPI request) {
        memeService.create(request);
        return ResponseEntity.ok().build();
    }

}
