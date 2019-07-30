package com.memastick.backmem.user.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private final EvolveNexterService evolveNexterService;
    private final TokenAllowanceSendService allowanceSendService;
    private final NotifyService notifyService;
    private final EvolveSelecterService evolveSelecterService;
    private final MigrateService migrateService;
    private final TranslatorAdminService translatorAdminService;

    @Autowired
    public AdminController(
        EvolveNexterService evolveNexterService,
        TokenAllowanceSendService allowanceSendService,
        NotifyService notifyService,
        EvolveSelecterService evolveSelecterService,
        MigrateService migrateService,
        TranslatorAdminService translatorAdminService
    ) {
        this.evolveNexterService = evolveNexterService;
        this.allowanceSendService = allowanceSendService;
        this.notifyService = notifyService;
        this.evolveSelecterService = evolveSelecterService;
        this.migrateService = migrateService;
        this.translatorAdminService = translatorAdminService;
    }

    @GetMapping("test")
    public void test() { }

    @PatchMapping("admin-translate")
    public void adminTranslate(@RequestBody IdAPI request) {
        translatorAdminService.publish(request.getId());
    }

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
