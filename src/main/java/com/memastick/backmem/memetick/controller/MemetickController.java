package com.memastick.backmem.memetick.controller;

import com.memastick.backmem.memetick.api.ChangeNickAPI;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.api.MemetickRatingAPI;
import com.memastick.backmem.memetick.constant.MemetickRatingFilter;
import com.memastick.backmem.memetick.service.MemetickRatingService;
import com.memastick.backmem.memetick.service.MemetickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("memeticks")
public class MemetickController {

    private final MemetickService memetickService;
    private final MemetickRatingService ratingService;

    @Autowired
    public MemetickController(
        MemetickService memetickService,
        MemetickRatingService ratingService
    ) {
        this.memetickService = memetickService;
        this.ratingService = ratingService;
    }

    @GetMapping("view/me")
    public MemetickAPI viewMe() {
        return memetickService.viewByMe();
    }

    @GetMapping("view/{id}")
    public MemetickAPI view(@PathVariable("id") UUID id) {
        return memetickService.viewById(id);
    }

    @GetMapping("/rating/{filter}")
    public MemetickRatingAPI rating(@PathVariable("filter") MemetickRatingFilter filter) {
        return ratingService.rating(filter);
    }

    @PutMapping("/nick/change")
    public ResponseEntity changeNick(@RequestBody ChangeNickAPI request) {
        memetickService.changeNick(request);
        return ResponseEntity.ok().build();
    }
}
