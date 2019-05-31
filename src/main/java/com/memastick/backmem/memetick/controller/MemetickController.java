package com.memastick.backmem.memetick.controller;

import com.memastick.backmem.memetick.api.ChangeNickAPI;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.memetick.api.MemetickRatingAPI;
import com.memastick.backmem.memetick.constant.MemetickRatingFilter;
import com.memastick.backmem.memetick.service.MemetickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("memeticks")
public class MemetickController {

    private final MemetickService memetickService;

    @Autowired
    public MemetickController(
        MemetickService memetickService
    ) {
        this.memetickService = memetickService;
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
        return memetickService.rating(filter);
    }

    @GetMapping("/following")// TODO to new controller
    public List<MemetickPreviewAPI> following() {
        return memetickService.following();
    }

    @PutMapping("/nick/change")
    public ResponseEntity changeNick(@RequestBody ChangeNickAPI request) {
        memetickService.changeNick(request);
        return ResponseEntity.ok().build();
    }
}
