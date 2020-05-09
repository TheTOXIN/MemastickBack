package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.exception.CellSmallException;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.evolution.service.EvolveService;
import com.memastick.backmem.main.constant.DnaCount;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.memetick.view.CellInventoryView;
import com.memastick.backmem.memetick.view.MemetickInventoryView;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.memastick.backmem.main.constant.DnaCount.*;
import static com.memastick.backmem.main.constant.ValidConstant.MAX_TEXT_LEN;

@Service
@AllArgsConstructor
public class MemesCreateService {

    private final static Logger LOG = LoggerFactory.getLogger(MemesCreateService.class);

    private final NotifyService notifyService;
    private final OauthData oauthData;
    private final MemeRepository memeRepository;
    private final MemetickService memetickService;
    private final EvolveMemeService evolveMemeService;
    private final EvolveService evolveService;
    private final MemetickInventoryRepository inventoryRepository;
    private final MemeCellService memeCellService;

    @Transactional
    public void create(MemeCreateAPI request) {
        Memetick memetick = oauthData.getCurrentMemetick();
        Meme meme = make(request, memetick);

        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);
        if (!memeCellService.checkState(inventory)) throw new CellSmallException();

        memeRepository.saveAndFlush(meme);
        evolveMemeService.startEvolve(meme);

        int dnaCombo = inventory.getCellCombo();
        memeCellService.updateCell(inventory);

        if (inventory.getCellCombo() == MIN_CREATE) dnaCombo = MIN_CREATE;
        memetickService.addDna(memetick, dnaCombo * COF_CREATE);

        notifyService.sendCREATING(memetick, meme);
    }

    public void notification() {
        LOG.info("START check cell notify");

        List<MemetickInventory> inventories = inventoryRepository.findByCellNotifyFalse();

        List<Memetick> memeticks = inventories
            .stream()
            .filter(memeCellService::checkState)
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
            evolveService.computeEPI()
        );
    }
}

