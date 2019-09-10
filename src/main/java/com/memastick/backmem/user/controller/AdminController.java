package com.memastick.backmem.user.controller;

import com.memastick.backmem.battle.repository.BattleVoteRepository;
import com.memastick.backmem.evolution.service.EvolveNexterService;
import com.memastick.backmem.evolution.service.EvolveSelecterService;
import com.memastick.backmem.main.service.MigrateService;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.tokens.service.TokenAllowanceSendService;
import com.memastick.backmem.translator.serivce.TranslatorPublishService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AdminController {

    private final EvolveNexterService evolveNexterService;
    private final TokenAllowanceSendService allowanceSendService;
    private final NotifyService notifyService;
    private final EvolveSelecterService evolveSelecterService;
    private final MigrateService migrateService;
    private final TranslatorPublishService publishService;
    private final BattleVoteRepository battleVoteRepository;

    @GetMapping("test}")
    public void test() {

    }

    @GetMapping("day-publish")
    public void dayPublish() {
        publishService.publish();
    }

    @GetMapping("clear-votes")
    public void clearVotes() {
        battleVoteRepository.deleteAll();
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
