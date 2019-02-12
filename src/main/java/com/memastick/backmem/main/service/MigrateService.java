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

    @Autowired
    private MemetickRepository memetickRepository;

    @Autowired
    private MemetickInventoryService memetickInventoryService;

    @Autowired
    public MigrateService(
        MemeRepository memeRepository,
        MemeLikeRepository memeLikeRepository
    ) {
        this.memeRepository = memeRepository;
        this.memeLikeRepository = memeLikeRepository;
    }

    public void migrate() {
        memetickRepository.findAll().forEach(memetickInventoryService::generateInventory);
//        memeRepository.findAll().forEach(meme -> {
//            Long sum = memeLikeRepository.sumChromosomeByMemeId(meme.getId()).orElse(0L);
//            meme.setChromosomes((int)((long)sum));
//            memeRepository.save(meme);
//        });
    }
}
