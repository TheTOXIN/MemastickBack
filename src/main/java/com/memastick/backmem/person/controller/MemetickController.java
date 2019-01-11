package com.memastick.backmem.person.controller;

import com.memastick.backmem.person.api.MemetickAPI;
import com.memastick.backmem.person.service.MemetickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("memetick")
public class MemetickController {

    private final MemetickService memetickService;

    @Autowired
    public MemetickController(
        MemetickService memetickService
    ) {
        this.memetickService = memetickService;
    }

    @GetMapping("me")
    public MemetickAPI me() {
        return memetickService.me();
    }

}
