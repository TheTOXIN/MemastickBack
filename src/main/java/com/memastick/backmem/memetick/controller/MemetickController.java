package com.memastick.backmem.memetick.controller;

import com.memastick.backmem.memetick.api.*;
import com.memastick.backmem.memetick.constant.MemetickRatingFilter;
import com.memastick.backmem.memetick.service.MemetickRatingService;
import com.memastick.backmem.memetick.service.MemetickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("memeticks")
public class MemetickController {

    private final MemetickService memetickService;
    private final MemetickRatingService ratingService;

    @GetMapping("/{id}")
    public MemetickAPI read(@PathVariable("id") UUID id) {
        return memetickService.readById(id);
    }

    @GetMapping("profile/me")
    public MemetickProfileAPI profileMe() {
        return memetickService.profileByMe();
    }

    @GetMapping("profile/{id}")
    public MemetickProfileAPI profile(@PathVariable("id") UUID id) {
        return memetickService.profileById(id);
    }

    @PostMapping("list")
    public List<MemetickPreviewAPI> list(@RequestBody List<UUID> memetickIds) {
        return memetickService.list(memetickIds);
    }

    @GetMapping("/rating/{filter}")
    public ResponseEntity<MemetickRatingAPI> rating(@PathVariable("filter") MemetickRatingFilter filter) {
        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
            .body(ratingService.rating(filter));
    }

    @PutMapping("/nick/change")
    public ResponseEntity changeNick(@RequestBody ChangeNickAPI request) {
        memetickService.changeNick(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/creed/agree")
    public ResponseEntity creedAgree() {
        memetickService.creedAgree();
        return ResponseEntity.ok().build();
    }
}
