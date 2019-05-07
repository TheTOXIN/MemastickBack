package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.exception.CellSmallException;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.api.MemeImgAPI;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.constant.MemeFilter;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.mapper.MemeMapper;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MemeService {

    public final NotifyService notifyService;
    private final TaskScheduler taskScheduler;
    private final SecurityService securityService;
    private final MemeRepository memeRepository;
    private final MemetickService memetickService;
    private final EvolveMemeService evolveMemeService;
    private final MemeMapper memeMapper;
    private final MemeLikeService memeLikeService;
    private final MemePoolService memePoolService;
    private final MemetickInventoryService inventoryService;
    private final MemetickInventoryRepository inventoryRepository;

    @Autowired
    public MemeService(
        SecurityService securityService,
        MemeRepository memeRepository,
        MemetickService memetickService,
        @Lazy EvolveMemeService evolveMemeService,
        @Lazy MemeMapper memeMapper,
        @Lazy MemeLikeService memeLikeService,
        @Lazy MemePoolService memePoolService,
        MemetickInventoryService inventoryService,
        MemetickInventoryRepository inventoryRepository,
        TaskScheduler taskScheduler,
        NotifyService notifyService
    ) {
        this.securityService = securityService;
        this.memeRepository = memeRepository;
        this.memetickService = memetickService;
        this.evolveMemeService = evolveMemeService;
        this.memeMapper = memeMapper;
        this.memeLikeService = memeLikeService;
        this.memePoolService = memePoolService;
        this.inventoryService = inventoryService;
        this.inventoryRepository = inventoryRepository;
        this.taskScheduler = taskScheduler;
        this.notifyService = notifyService;
    }

    @Transactional
    public void create(MemeCreateAPI request) {
        if (inventoryService.stateCell() != 100) throw new CellSmallException();

        Memetick memetick = securityService.getCurrentMemetick();
        Meme meme = makeMeme(request, memetick);

        memeRepository.saveAndFlush(meme);
        evolveMemeService.startEvolve(meme);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusDays(1);

        MemetickInventory inventory = inventoryRepository.findByMemetick(memetick);
        inventory.setCellCreating(now);

        inventoryRepository.save(inventory);
        memetickService.addDna(memetick, MathUtil.rand(0, 1000));

        notifyService.sendCREATING(memetick, meme);
        taskScheduler.schedule(
            () -> notifyService.sendCELL(memetick),
            end.toInstant(ZoneOffset.UTC)
        );
    }

    @Transactional
    public List<MemePageAPI> pages(MemeFilter filter, EvolveStep step, Pageable pageable) {
        return read(filter, step, pageable)
            .stream()
            .map(memeMapper::toPageAPI)
            .collect(Collectors.toList());
    }

    public MemePageAPI page(UUID memeId) {
        Meme meme = findById(memeId);
        return  memeMapper.toPageAPI(meme);
    }

    public MemeImgAPI readImg(UUID memeId) {
        Meme meme = findById(memeId);
        return new MemeImgAPI(meme.getUrl());
    }

    public Meme findById(UUID id) {
        Optional<Meme> byId = memeRepository.findById(id);
        if (byId.isEmpty()) throw new EntityNotFoundException(Meme.class, "id");
        return byId.get();
    }

    public void moveIndex(Meme meme) {
        long newIndex = meme.getIndexer() + 1;
        long oldIndex = meme.getIndexer();

        Meme prevMeme = memeRepository.findByPopulationAndIndexer(
            meme.getPopulation(),
            newIndex
        ).orElse(null);

        if (prevMeme == null) return;

        meme.setIndexer(newIndex);
        prevMeme.setIndexer(oldIndex);

        memeRepository.save(meme);
        memeRepository.save(prevMeme);
    }

    private List<Meme> read(MemeFilter filter, EvolveStep step, Pageable pageable) {
        List<Meme> memes = new ArrayList<>();

        Memetick memetick = securityService.getCurrentMemetick();

        switch (filter) { //TODO default pageable and sorting
            case INDV: memes = memeRepository.findByType(MemeType.INDIVID, pageable); break;
            case SELF: memes = memeRepository.findByMemetick(memetick, pageable); break;
            case LIKE: memes = memeLikeService.findMemesByLikeFilter(memetick, pageable); break;
            case DTHS: memes = memeRepository.findByType(MemeType.DEATH, pageable); break;
            case EVLV: memes = memeRepository.findByType(MemeType.EVOLVE, pageable); break;
            case POOL: memes = memePoolService.generate(step, pageable); break;
        }

        return memes;
    }

    private Meme makeMeme(MemeCreateAPI request, Memetick memetick) {
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
