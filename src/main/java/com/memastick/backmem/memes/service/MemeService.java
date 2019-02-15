package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.api.MemeCreateAPI;
import com.memastick.backmem.memes.api.MemePageAPI;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.mapper.MemeMapper;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.service.SecurityService;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.tokens.service.TokenWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MemeService {

    private final SecurityService securityService;
    private final MemeRepository memeRepository;
    private final MemetickService memetickService;
    private final MemeLikeService memeLikeService;
    private final EvolveMemeService evolveMemeService;
    private final TokenWalletService tokenWalletService;
    private final MemeMapper memeMapper;
    private final MemetickMapper memetickMapper;

    @Autowired
    public MemeService(
        SecurityService securityService,
        MemeRepository memeRepository,
        MemetickService memetickService,
        @Lazy MemeLikeService memeLikeService,
        @Lazy EvolveMemeService evolveMemeService,
        TokenWalletService tokenWalletService,
        MemeMapper memeMapper,
        MemetickMapper memetickMapper
    ) {
        this.securityService = securityService;
        this.memeRepository = memeRepository;
        this.memetickService = memetickService;
        this.memeLikeService = memeLikeService;
        this.evolveMemeService = evolveMemeService;
        this.tokenWalletService = tokenWalletService;
        this.memeMapper = memeMapper;
        this.memetickMapper = memetickMapper;
    }

    @Transactional
    public void create(MemeCreateAPI request) {
        Memetick memetick = securityService.getCurrentMemetick();

        tokenWalletService.have(TokenType.CREATING, memetick);

        Meme meme = makeMeme(request, memetick);

        memeRepository.save(meme);
        evolveMemeService.startEvolve(meme);

        tokenWalletService.take(TokenType.CREATING, memetick);
        memetickService.addDna(memetick, MathUtil.rand(0, 100));
    }

    @Transactional
    public List<MemePageAPI> readPages(Pageable pageable) {
        return memeRepository.findAll(pageable)
            .stream()
            .map(this::mapToPage)
            .collect(Collectors.toList());
    }

    public Meme findById(UUID id) {
        Optional<Meme> byId = memeRepository.findById(id);
        if (byId.isEmpty()) throw new EntityNotFoundException(Meme.class, "id");
        return byId.get();
    }

    private Meme makeMeme(MemeCreateAPI request, Memetick memetick) {
        return new Meme(
            request.getFireId(),
            request.getUrl(),
            memetick,
            ZonedDateTime.now(),
            MemeType.EVOLVE,
            0
        );
    }

    private MemePageAPI mapToPage(Meme meme) {
        return new MemePageAPI(
            memeMapper.toMemeDTO(meme),
            memeLikeService.readByMeme(meme),
            memetickMapper.toPreviewDTO(meme.getMemetick())
        );
    }
}
