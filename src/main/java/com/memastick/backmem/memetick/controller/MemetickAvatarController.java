package com.memastick.backmem.memetick.controller;

import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memetick.service.MemetickAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("memetick-avatars")
@RequiredArgsConstructor
public class MemetickAvatarController {

    private final MemetickAvatarService memetickAvatarService;

    @GetMapping(
        value = "/download/{memetickId}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] downloadAvatar(
        @PathVariable("memetickId") UUID memetickId,
        @RequestParam(name = "cache", required = false, defaultValue = "true") boolean cache,
        HttpServletResponse response
    ) {
        checkCache(cache, response);
        return memetickAvatarService.download(memetickId);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadAvatar(
        @RequestParam("file") MultipartFile photo
    ) throws IOException {
        memetickAvatarService.upload(photo);
        return ResponseEntity.ok().build();
    }

    private void checkCache(boolean cache, HttpServletResponse response) {
        if (cache) {
            response.setHeader(
                "Cache-Control",
                "no-transform, public, max-age=" + GlobalConstant.AVATAR_CACHE
            );
        }
    }
}
