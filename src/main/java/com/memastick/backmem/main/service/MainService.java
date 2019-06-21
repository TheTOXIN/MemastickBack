package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.notification.impl.NotifyBellService;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private final SecurityService securityService;
    private final EvolveMemeService evolveMemeService;
    private final MemeRepository memeRepository;
    private final MemetickInventoryService inventoryService;
    private final NotifyBellService notifyBellService;

    @Autowired
    public MainService(
        SecurityService securityService,
        EvolveMemeService evolveMemeService,
        MemeRepository memeRepository,
        MemetickInventoryService inventoryService,
        NotifyBellService notifyBellService
    ) {
        this.securityService = securityService;
        this.evolveMemeService = evolveMemeService;
        this.memeRepository = memeRepository;
        this.inventoryService = inventoryService;
        this.notifyBellService = notifyBellService;
    }

    public HomeAPI home() {
        User user = securityService.getCurrentUser();

        return new HomeAPI(
            user.getMemetick().getNick(),
            evolveMemeService.computeEvolution(),
            memeRepository.countByType(MemeType.EVLV).orElse(0L),
            inventoryService.countItems(user.getMemetick()),
            notifyBellService.count(user)
        );
    }
}
