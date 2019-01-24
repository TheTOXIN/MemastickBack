package com.memastick.backmem.person.controller;

import com.memastick.backmem.person.api.ChangeNickAPI;
import com.memastick.backmem.person.api.MemetickAPI;
import com.memastick.backmem.person.service.MemetickAvatarService;
import com.memastick.backmem.person.service.MemetickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("memetick")
public class MemetickController {

    private final MemetickService memetickService;
    private final MemetickAvatarService memetickAvatarService;

    @Autowired
    public MemetickController(
        MemetickService memetickService,
        MemetickAvatarService memetickAvatarService
    ) {
        this.memetickService = memetickService;
        this.memetickAvatarService = memetickAvatarService;
    }

    @GetMapping("/me")
    public MemetickAPI me() {
        return memetickService.me();
    }

    @GetMapping("/stats/{memetickId}")
    public void myStats() {

    }

    @GetMapping("/home")
    public void home() {

    }

    @GetMapping(
        value = "/avatar/download/{memetickId}",
        produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] downloadAvatar(@PathVariable("memetickId") UUID memetickId) {
        return memetickAvatarService.download(memetickId);
    }

    @PostMapping("/avatar/upload")
    public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile photo) throws IOException {
        memetickAvatarService.upload(photo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/nick/change")
    public ResponseEntity changeNick(@RequestBody ChangeNickAPI request) {
        memetickService.changeNick(request);
        return ResponseEntity.ok().build();
    }

}
