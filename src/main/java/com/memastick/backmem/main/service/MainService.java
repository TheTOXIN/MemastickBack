package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.main.api.NotifyCountAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.notification.impl.NotifyBellService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private final OauthData oauthData;
    private final EvolveMemeService evolveMemeService;
    private final MemeRepository memeRepository;
    private final MemetickInventoryService inventoryService;
    private final NotifyBellService notifyBellService;

    @Autowired
    public MainService(
        OauthData oauthData,
        EvolveMemeService evolveMemeService,
        MemeRepository memeRepository,
        MemetickInventoryService inventoryService,
        NotifyBellService notifyBellService
    ) {
        this.oauthData = oauthData;
        this.evolveMemeService = evolveMemeService;
        this.memeRepository = memeRepository;
        this.inventoryService = inventoryService;
        this.notifyBellService = notifyBellService;
    }

    public HomeAPI home() {
        User user = oauthData.getCurrentUser();

        return new HomeAPI(
            user.getMemetick().getNick(),
            evolveMemeService.computeEvolution(),
            memeRepository.countByType(MemeType.EVLV).orElse(0L),
            evolveMemeService.computeSelectTimer()
        );
    }

    public NotifyCountAPI notifyCount() {
        User user = oauthData.getCurrentUser();

        return new NotifyCountAPI(
            inventoryService.countItems(user.getMemetick()),
            notifyBellService.count(user)
        );
    }
}
