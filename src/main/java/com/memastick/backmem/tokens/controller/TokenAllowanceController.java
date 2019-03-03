package com.memastick.backmem.tokens.controller;

import com.memastick.backmem.tokens.api.TokenWalletAPI;
import com.memastick.backmem.tokens.service.TokenAllowanceSendService;
import com.memastick.backmem.tokens.service.TokenAllowanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PatchMapping("take")
    public TokenWalletAPI takeAllowance() {
        return allowanceService.take();
    }
}
