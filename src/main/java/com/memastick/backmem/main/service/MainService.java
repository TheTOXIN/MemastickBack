package com.memastick.backmem.main.service;

import com.memastick.backmem.chat.service.ChatService;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.evolution.service.EvolveService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.main.api.InitAPI;
import com.memastick.backmem.main.api.NotifyCountAPI;
import com.memastick.backmem.main.component.HomeMessageGenerator;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.service.MemeCellService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.memetick.service.MemetickRankService;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.impl.NotifyBellService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MemeCellService memeCellService;
    private final ChatService chatService;

    @Transactional(readOnly = true)
    public HomeAPI home() {
        Memetick memetick = oauthData.getCurrentMemetick();

        return new HomeAPI(
            memetickService.preview(memetick),
            memetickRankService.rank(memetick),
            messageGenerate.getMessage(),
            evolveService.computeEvolution(),
            evolveMemeService.computeSelectTimer(),
            memeCellService.stateCell(memetick),
            chatService.readHome(),
            memetick.isCreed()
        );
    }

    @Transactional(readOnly = true)
    public InitAPI init() {
        User user = oauthData.getCurrentUser();

        return new InitAPI(
            user.getLogin(),
            GlobalConstant.VER,
            new NotifyCountAPI(
                inventoryService.countItems(user.getMemetick()),
                notifyBellService.count(user)
            )
        );
    }
}
