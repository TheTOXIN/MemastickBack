package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.evolution.service.EvolveService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.main.api.InitAPI;
import com.memastick.backmem.main.api.NotifyCountAPI;
import com.memastick.backmem.main.component.HomeMessageGenerator;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.memetick.service.MemetickRankService;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.impl.NotifyBellService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MainService {

    private final OauthData oauthData;
    private final EvolveMemeService evolveMemeService;
    private final EvolveService evolveService;
    private final MemetickInventoryService inventoryService;
    private final NotifyBellService notifyBellService;
    private final HomeMessageGenerator messageGenerate;
    private final MemetickRankService memetickRankService;
    private final MemetickService memetickService;

    public HomeAPI home() {
        Memetick memetick = oauthData.getCurrentMemetick();

        return new HomeAPI(
            memetickService.preview(memetick),
            memetickRankService.rank(memetick),
            messageGenerate.getMessage(),
            evolveService.computeEvolution(),
            evolveMemeService.computeSelectTimer(),
            memetick.isCreed()
        );
    }

    public InitAPI init() {
        User user = oauthData.getCurrentUser();

        return new InitAPI(
            GlobalConstant.VER,
            new NotifyCountAPI(
                inventoryService.countItems(user.getMemetick()),
                notifyBellService.count(user)
            )
        );
    }
}
