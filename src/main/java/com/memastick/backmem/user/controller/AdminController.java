package com.memastick.backmem.user.controller;

import com.memastick.backmem.battle.component.BattleTrigger;
import com.memastick.backmem.evolution.service.EvolveNexterService;
import com.memastick.backmem.evolution.service.EvolveSelecterService;
import com.memastick.backmem.main.api.IdAPI;
import com.memastick.backmem.main.service.MigrateService;
import com.memastick.backmem.memecoin.entity.MemeCoin;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.tokens.service.TokenAllowanceSendService;
import com.memastick.backmem.translator.serivce.TranslatorAdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AdminController {

    private final EvolveNexterService evolveNexterService;
    private final TokenAllowanceSendService allowanceSendService;
    private final NotifyService notifyService;
    private final EvolveSelecterService evolveSelecterService;
    private final MigrateService migrateService;

    private final BattleTrigger battleTrigger;

    @GetMapping("test")
    public void test() { battleTrigger.ratingCheck();}

    @GetMapping("next-evolve")
    public void nextEvolve() {
        evolveNexterService.next();
    }

    @GetMapping("select-evolve")
    public void selectEvolve() {
        evolveSelecterService.select();
    }

    @GetMapping("send-allowance")
    public void allowance() {
        allowanceSendService.allowance();
    }

    @PatchMapping("admin-message")
    public void message(@RequestBody String message) {
        notifyService.sendADMIN(message);
    }

    @PostMapping("migrate")
    public void migrate() {
        migrateService.migrate();
    }
}
