package com.memastick.backmem.person.controller;

import com.memastick.backmem.person.api.ChangeNickAPI;
import com.memastick.backmem.person.api.MemetickPreviewAPI;
import com.memastick.backmem.person.api.MemetickViewAPI;
import com.memastick.backmem.person.service.MemetickAvatarService;
import com.memastick.backmem.person.service.MemetickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("memeticks")
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

    @GetMapping(
        value = "/avatar/download/{id}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] downloadAvatar(@PathVariable("id") UUID id) {
        return memetickAvatarService.download(id);
    }

    @PostMapping("/avatar/upload")
    public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile photo) throws IOException {
        memetickAvatarService.upload(photo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/nick/change")
    public ResponseEntity changeNick(@RequestBody ChangeNickAPI request) {
        memetickService.changeNick(request);
        return ResponseEntity.ok().build();
    }

}
