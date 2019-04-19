package com.memastick.backmem.tokens.controller;

import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.service.TokenAcceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("token-accept")
public class TokenAcceptController {

    private final TokenAcceptService tokenAcceptService;

    @Autowired
    public TokenAcceptController(
        TokenAcceptService tokenAcceptService
    ) {
        this.tokenAcceptService = tokenAcceptService;
    }

    @PatchMapping("/token/{token}/meme/{memeId}")
    public ResponseEntity accept(
        @PathVariable("token") TokenType token,
        @PathVariable("memeId") UUID memeId
    ) {
        tokenAcceptService.accept(token, memeId);
        return ResponseEntity.ok().build();
    }
}
