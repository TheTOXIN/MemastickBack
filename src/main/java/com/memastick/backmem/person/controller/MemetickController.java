package com.memastick.backmem.person.controller;

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

    @GetMapping("me")
    public MemetickAPI me() {
        return memetickService.me();
    }

    @GetMapping(
        value = "avatar/download/{id}",
        produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] downloadAvatar(
        @PathVariable("id") UUID memetickId
    ) {
        return memetickAvatarService.download(memetickId);
    }

    @PostMapping("avatar/upload/{memetickId}")
    @PreAuthorize("@abac.hasPermissionById(#memetickId)")
    public ResponseEntity<Void> uploadAvatar(
        @RequestParam("file") MultipartFile photo,
        @P("memetickId") @PathVariable("memetickId") UUID memetickId
    ) throws IOException {
        memetickAvatarService.upload(memetickId, photo);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public void changeNick() {

    }

    @GetMapping
    public void myStats() {

    }

}
