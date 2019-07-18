package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.exception.CellSmallException;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static com.memastick.backmem.main.constant.GlobalConstant.CELL_GROWTH;

@Service
public class MemesCreateService {

    private final TaskScheduler taskScheduler;
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
        NotifyService notifyService,
        TaskScheduler taskScheduler
    ) {
        this.securityService = securityService;
        this.memeRepository = memeRepository;
        this.memetickService = memetickService;
        this.evolveMemeService = evolveMemeService;
        this.inventoryService = inventoryService;
        this.notifyService = notifyService;
        this.taskScheduler = taskScheduler;
    }

    @Transactional
    public void create(MemeCreateAPI request) {
        if (!inventoryService.checkState()) throw new CellSmallException();

        Memetick memetick = securityService.getCurrentMemetick();
        Meme meme = make(request, memetick);

        memeRepository.saveAndFlush(meme);
        evolveMemeService.startEvolve(meme);

        inventoryService.updateCell(memetick);
        memetickService.addDna(memetick, MathUtil.rand(100, 1000));

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime end = now.plusHours(CELL_GROWTH);

        notifyService.sendCREATING(memetick, meme);
        taskScheduler.schedule(() -> notifyService.sendCELL(memetick), end.toInstant(ZoneOffset.UTC));
    }

    private Meme make(MemeCreateAPI request, Memetick memetick) {
        return new Meme(
            request.getFireId(),
            request.getUrl(),
            memetick,
            evolveMemeService.computeEPI()
        );
    }
}

