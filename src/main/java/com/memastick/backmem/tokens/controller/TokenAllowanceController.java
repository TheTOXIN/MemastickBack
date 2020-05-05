package com.memastick.backmem.tokens.controller;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.service.TokenAllowanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("token-allowance")
public class TokenAllowanceController {

    private final TokenAllowanceService allowanceService;

    @Autowired
    public TokenAllowanceController(
        TokenAllowanceService allowanceService
    ) {
        this.allowanceService = allowanceService;
    }

    @GetMapping("have")
    public ResponseEntity haveAllowance() {
        if (allowanceService.have()) return ResponseEntity.ok().build();
        else return ResponseEntity.status(ErrorCode.ALLOWANCE_EMPTY.getStatus()).build();
    }

    @PatchMapping("take")
    public TokenWalletAPI takeAllowance() {
        return allowanceService.take();
    }
}
