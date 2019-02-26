package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private final SecurityService securityService;
    private final EvolveMemeService evolveMemeService;

    @Autowired
    public MainService(
        SecurityService securityService,
        EvolveMemeService evolveMemeService
    ) {
        this.securityService = securityService;
        this.evolveMemeService = evolveMemeService;
    }

    public HomeAPI home() {
        return new HomeAPI(
            securityService.getCurrentMemetick().getNick(),
            evolveMemeService.evolveDay()
        );
    }
}
