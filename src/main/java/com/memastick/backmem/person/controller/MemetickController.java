package com.memastick.backmem.person.controller;

import com.memastick.backmem.person.api.ChangeNickAPI;
import com.memastick.backmem.person.api.MemetickAPI;
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
    public MemetickAPI viewMe() {
        return memetickService.viewByMe();
    }

    @GetMapping("view/{id}")
    public MemetickAPI view(@PathVariable("id") UUID id) {
        return memetickService.viewById(id);
    }

    @PutMapping("/nick/change")
    public ResponseEntity changeNick(@RequestBody ChangeNickAPI request) {
        memetickService.changeNick(request);
        return ResponseEntity.ok().build();
    }

}
