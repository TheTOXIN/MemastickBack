package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.service.TokenAllowanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private final SecurityService securityService;
    private final EvolveMemeService evolveMemeService;
    private final TokenAllowanceService tokenAllowanceService;

    @Autowired
    public MainService(
            SecurityService securityService,
            EvolveMemeService evolveMemeService,
            TokenAllowanceService tokenAllowanceService
    ) {
        this.securityService = securityService;
        this.evolveMemeService = evolveMemeService;
        this.tokenAllowanceService = tokenAllowanceService;
    }

    public HomeAPI home() {
        return new HomeAPI(
            securityService.getCurrentMemetick().getNick(),
            evolveMemeService.evolveDay(),
            tokenAllowanceService.have()
        );
    }
}
