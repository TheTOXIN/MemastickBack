package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private final SecurityService securityService;
    private final EvolveMemeService evolveMemeService;
    private final MemeRepository memeRepository;

    @Autowired
    public MainService(
        SecurityService securityService,
        EvolveMemeService evolveMemeService,
        MemeRepository memeRepository
    ) {
        this.securityService = securityService;
        this.evolveMemeService = evolveMemeService;
        this.memeRepository = memeRepository;
    }

    public HomeAPI home() {
        return new HomeAPI(
            securityService.getCurrentMemetick().getNick(),
            evolveMemeService.evolveDay(),
            memeRepository.countByType(MemeType.EVOLVE).orElse(0L)
        );
    }
}
