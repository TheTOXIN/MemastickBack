package com.memastick.backmem.tokens.controller;

import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.api.TokenWalletHaveAPI;
import com.memastick.backmem.tokens.service.TokenWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("token-wallets")
public class TokenWalletController {

    private final TokenWalletService tokenWalletService;

    @GetMapping("memetick/{memetickId}")
    public TokenWalletAPI read(@PathVariable("memetickId")UUID memetickId) {
        return tokenWalletService.read(memetickId);
    }

    @PostMapping("have")
    public ResponseEntity have(@RequestBody TokenWalletHaveAPI request) {
        tokenWalletService.have(request.getType());
        return ResponseEntity.ok().build();
    }
}
