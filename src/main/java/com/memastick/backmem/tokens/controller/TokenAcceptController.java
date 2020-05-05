package com.memastick.backmem.tokens.controller;

import com.memastick.backmem.memes.dto.MemeLohDTO;
import com.memastick.backmem.tokens.api.TokenAcceptAPI;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.service.TokenAcceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("token-accept")
public class TokenAcceptController {

    private final TokenAcceptService tokenAcceptService;

    @PatchMapping("/token/{token}/meme/{memeId}")
    public ResponseEntity accept(
        @PathVariable("token") TokenType token,
        @PathVariable("memeId") UUID memeId,
        @RequestBody TokenAcceptAPI request
    ) {
        tokenAcceptService.accept(token, memeId, request);
        return ResponseEntity.ok().build();
    }
}
