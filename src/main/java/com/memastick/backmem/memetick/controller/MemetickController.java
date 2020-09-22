package com.memastick.backmem.memetick.controller;

import com.memastick.backmem.memetick.api.ChangeNickAPI;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.memetick.api.MemetickRatingAPI;
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

    @GetMapping("view/me")
    public MemetickAPI viewMe() {
        return memetickService.viewByMe();
    }

    @GetMapping("view/{id}")
    public MemetickAPI view(@PathVariable("id") UUID id) {
        return memetickService.viewById(id);
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
