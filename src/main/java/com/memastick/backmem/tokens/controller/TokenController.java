package com.memastick.backmem.tokens.controller;

import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.api.TokenWalletHaveAPI;
import com.memastick.backmem.tokens.service.TokenWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("token-wallets")
public class TokenController {

    private final TokenWalletService tokenWalletService;

    @Autowired
    public TokenController(
        TokenWalletService tokenWalletService
    ) {
        this.tokenWalletService = tokenWalletService;
    }

    @PostMapping("have")
    public ResponseEntity have(@RequestBody TokenWalletHaveAPI request) {
        tokenWalletService.have(request.getType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("my")
    public TokenWalletAPI my() {
        return tokenWalletService.my();
    }
}
