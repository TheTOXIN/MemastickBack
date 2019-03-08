package com.memastick.backmem.main.service;

import com.memastick.backmem.memes.repository.MemeLikeRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MigrateService {

    private final MemeRepository memeRepository;
    private final MemeLikeRepository memeLikeRepository;
    private final MemetickRepository memetickRepository;
    private final MemetickInventoryService inventoryService;

    @Autowired
    public MigrateService(
        MemeRepository memeRepository,
        MemeLikeRepository memeLikeRepository,
        MemetickRepository memetickRepository, MemetickInventoryService inventoryService
    ) {
        this.memeRepository = memeRepository;
        this.memeLikeRepository = memeLikeRepository;
        this.memetickRepository = memetickRepository;
        this.inventoryService = inventoryService;
    }

    public void migrate() {
        memeRepository.findAll().forEach(meme -> {
            Long sum = memeLikeRepository.sumChromosomeByMemeId(meme.getId()).orElse(0L);
            meme.setChromosomes((int)((long)sum));
            memeRepository.save(meme);
        });

        memetickRepository.findAll().forEach(inventoryService::generateInventory);
    }
}
