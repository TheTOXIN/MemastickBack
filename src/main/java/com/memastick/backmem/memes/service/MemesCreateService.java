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
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemesCreateService {

    public final NotifyService notifyService;
    private final TaskScheduler taskScheduler;
    private final SecurityService securityService;
    private final MemeRepository memeRepository;
    private final MemetickService memetickService;
    private final EvolveMemeService evolveMemeService;
    private final MemetickInventoryService inventoryService;
    private final MemetickInventoryRepository inventoryRepository;

    @Autowired
    public MemesCreateService(
        SecurityService securityService,
        MemeRepository memeRepository,
        MemetickService memetickService,
        EvolveMemeService evolveMemeService,
        MemetickInventoryService inventoryService,
        MemetickInventoryRepository inventoryRepository,
        TaskScheduler taskScheduler,
        NotifyService notifyService
    ) {
        this.securityService = securityService;
        this.memeRepository = memeRepository;
        this.memetickService = memetickService;
        this.evolveMemeService = evolveMemeService;
        this.inventoryService = inventoryService;
        this.inventoryRepository = inventoryRepository;
        this.taskScheduler = taskScheduler;
        this.notifyService = notifyService;
    }

    @Transactional
    public void create(MemeCreateAPI request) {
        if (!inventoryService.checkState()) throw new CellSmallException();

        Memetick memetick = securityService.getCurrentMemetick();
        Meme meme = make(request, memetick);

        memeRepository.saveAndFlush(meme);
        evolveMemeService.startEvolve(meme);

        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);

        inventory.setCellCreating(LocalDateTime.now());
        inventory.setCellNotify(false);

        inventoryRepository.save(inventory);
        memetickService.addDna(memetick, MathUtil.rand(0, 1000));

        notifyService.sendCREATING(memetick, meme);
    }

    @Scheduled(fixedDelay = 3600000)
    public void notification() {
        List<MemetickInventory> inventories = inventoryRepository.findByCellNotifyFalse();

        inventories
            .stream()
            .filter(inventoryService::checkState)
            .forEach(inventory -> notifyService.sendCELL(inventory.getMemetick()));

        inventories.forEach(i -> i.setCellNotify(true));

        inventoryRepository.saveAll(inventories);
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
