package com.memastick.backmem.tokens.controller;

import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.api.TokenWalletHaveAPI;
import com.memastick.backmem.tokens.service.TokenWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("token-wallets")
public class TokenWalletController {

    private final TokenWalletService tokenWalletService;

    @Autowired
    public TokenWalletController(
            TokenWalletService tokenWalletService
    ) {
        this.tokenWalletService = tokenWalletService;
    }

    @PostMapping("have")
    public ResponseEntity have(@RequestBody TokenWalletHaveAPI request) {
        tokenWalletService.have(request.getType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("memetick/{memetickId}")
    public TokenWalletAPI read(@PathVariable("memetickId")UUID memetickId) {
        return tokenWalletService.read(memetickId);
    }
}
