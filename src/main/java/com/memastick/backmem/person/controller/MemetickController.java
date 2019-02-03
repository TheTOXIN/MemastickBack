package com.memastick.backmem.person.controller;

import com.memastick.backmem.person.api.ChangeNickAPI;
import com.memastick.backmem.person.api.MemetickPreviewAPI;
import com.memastick.backmem.person.api.MemetickViewAPI;
import com.memastick.backmem.person.service.MemetickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public MemetickViewAPI meView() {
        return memetickService.viewByMe();
    }

    @GetMapping("view/{id}")
    public MemetickViewAPI loginView(@PathVariable("id") UUID id) {
        return memetickService.viewById(id);
    }

    @GetMapping("preview/{id}")
    public MemetickPreviewAPI preview(@PathVariable("id") UUID id) {
        return memetickService.previewById(id);
    }

    @PutMapping("/nick/change")
    public ResponseEntity changeNick(@RequestBody ChangeNickAPI request) {
        memetickService.changeNick(request);
        return ResponseEntity.ok().build();
    }

}
