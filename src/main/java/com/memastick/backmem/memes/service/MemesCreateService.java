package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.exception.CellSmallException;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.service.InviteCodeService;
import com.memastick.backmem.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemesCreateService {

    private final NotifyService notifyService;
    private final SecurityService securityService;
    private final MemeRepository memeRepository;
    private final MemetickService memetickService;
    private final EvolveMemeService evolveMemeService;
    private final MemetickInventoryService inventoryService;

    @Autowired
    public MemesCreateService(
        SecurityService securityService,
        MemeRepository memeRepository,
        MemetickService memetickService,
        EvolveMemeService evolveMemeService,
        MemetickInventoryService inventoryService,
        NotifyService notifyService
    ) {
        this.securityService = securityService;
        this.memeRepository = memeRepository;
        this.memetickService = memetickService;
        this.evolveMemeService = evolveMemeService;
        this.inventoryService = inventoryService;
        this.notifyService = notifyService;
    }

    @Transactional
    public void create(MemeCreateAPI request) {
        if (!inventoryService.checkState()) throw new CellSmallException();

        Memetick memetick = securityService.getCurrentMemetick();
        Meme meme = make(request, memetick);

        memeRepository.save(meme);
        memeRepository.flush();

        evolveMemeService.startEvolve(meme);

        inventoryService.updateCell(memetick);
        memetickService.addDna(memetick, MathUtil.rand(0, 1000));

        notifyService.sendCREATING(memetick, meme);
    }

    private Meme make(MemeCreateAPI request, Memetick memetick) {
        long population = evolveMemeService.evolveDay();
        long indexer = memeRepository.countByPopulation(population).orElse(0L) + 1;

        return new Meme(
            request.getFireId(),
            request.getUrl(),
            memetick,
            population,
            indexer
        );
    }
}
