package com.memastick.backmem.memetick.controller;

import com.memastick.backmem.memetick.service.MemetickAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.memastick.backmem.main.constant.GlobalConstant.AVATAR_CACHE;

@RestController
@RequestMapping("memetick-avatars")
public class MemetickAvatarController {

    private final MemetickAvatarService memetickAvatarService;

    @Autowired
    public MemetickAvatarController(
        MemetickAvatarService memetickAvatarService
    ) {
        this.memetickAvatarService = memetickAvatarService;
    }

    @GetMapping(
        value = "/download/{id}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] downloadAvatar(
        @PathVariable("id") UUID id,
        HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setHeader(
            "Cache-Control",
            "no-transform, public, max-age=" + (AVATAR_CACHE)
        );
        return memetickAvatarService.download(id);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile photo) throws IOException {
        memetickAvatarService.upload(photo);
        return ResponseEntity.ok().build();
    }

}
