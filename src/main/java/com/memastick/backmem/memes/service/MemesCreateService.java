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
import com.memastick.backmem.security.component.OauthData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_TEXT_LEN;

@Service
public class MemesCreateService {

    private final static Logger LOG = LoggerFactory.getLogger(MemesCreateService.class);

    private final NotifyService notifyService;
    private final OauthData oauthData;
    private final MemeRepository memeRepository;
    private final MemetickService memetickService;
    private final EvolveMemeService evolveMemeService;
    private final MemetickInventoryService inventoryService;
    private final MemetickInventoryRepository inventoryRepository;

    @Autowired
    public MemesCreateService(
        OauthData oauthData,
        MemeRepository memeRepository,
        MemetickService memetickService,
        EvolveMemeService evolveMemeService,
        MemetickInventoryService inventoryService,
        NotifyService notifyService,
        MemetickInventoryRepository inventoryRepository
    ) {
        this.oauthData = oauthData;
        this.memeRepository = memeRepository;
        this.memetickService = memetickService;
        this.evolveMemeService = evolveMemeService;
        this.inventoryService = inventoryService;
        this.notifyService = notifyService;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public void create(MemeCreateAPI request) {
        if (!inventoryService.checkState()) throw new CellSmallException();

        Memetick memetick = oauthData.getCurrentMemetick();
        Meme meme = make(request, memetick);

        memeRepository.saveAndFlush(meme);
        evolveMemeService.startEvolve(meme);

        inventoryService.updateCell(memetick);
        memetickService.addDna(memetick, MathUtil.rand(100, 1000));

        notifyService.sendCREATING(memetick, meme);
    }

    public void notification() {
        LOG.info("START check cell notify");

        List<MemetickInventory> inventories = inventoryRepository.findByCellNotifyFalse();

        List<Memetick> memeticks = inventories
            .stream()
            .filter(inventoryService::checkState)
            .peek(i -> i.setCellNotify(true))
            .map(MemetickInventory::getMemetick)
            .collect(Collectors.toList());

        notifyService.sendCELL(memeticks);
        inventoryRepository.saveAll(inventories);
    }

    private Meme make(MemeCreateAPI request, Memetick memetick) {
        if (request.getText() != null && request.getText().length() > MAX_TEXT_LEN) {
            request.setText(request.getText().substring(0, MAX_TEXT_LEN));
        }

        return new Meme(
            request,
            memetick,
            evolveMemeService.computeEPI()
        );
    }
}

