package com.memastick.backmem.memetick.service;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.errors.exception.SettingException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.api.ChangeNickAPI;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.api.MemetickRatingAPI;
import com.memastick.backmem.memetick.constant.MemetickRatingFilter;
import com.memastick.backmem.memetick.dto.MemetickRatingDTO;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.notification.service.BellNotificationService;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MemetickService {

    private final MemetickRepository memetickRepository;
    private final SecurityService securityService;
    private final MemetickMapper memetickMapper;
    private final BellNotificationService bellNotificationService;
    private final MemeRepository memeRepository;

    @Autowired
    public MemetickService(
        MemetickRepository memetickRepository,
        SecurityService securityService,
        MemetickMapper memetickMapper,
        BellNotificationService bellNotificationService,
        MemeRepository memeRepository
    ) {
        this.memetickRepository = memetickRepository;
        this.securityService = securityService;
        this.memetickMapper = memetickMapper;
        this.bellNotificationService = bellNotificationService;
        this.memeRepository = memeRepository;
    }

    public MemetickAPI viewByMe() {
        return memetickMapper.toMemetickAPI(securityService.getCurrentMemetick());
    }

    public MemetickAPI viewById(UUID id) {
        return memetickMapper.toMemetickAPI(findById(id));
    }

    public void addDna(Memetick memetick, int dna) {
//        if (dna == 0) return; //TODO correct dna add
        bellNotificationService.sendDna(dna);
        memetick.setDna(memetick.getDna() + dna);
        memetickRepository.save(memetick);
    }

    public void changeNick(ChangeNickAPI request) {
        if (!ValidationUtil.checkNick(request.getNick())) throw new ValidationException(ErrorCode.INVALID_NICK);

        Memetick memetick = securityService.getCurrentMemetick();

        if (memetick.getNickChanged().plusWeeks(1).isAfter(ZonedDateTime.now()))
            throw new SettingException(ErrorCode.EXPIRE_NICK);

        request.setNick(request.getNick().replaceAll("\\s", ""));

        memetick.setNick(request.getNick());
        memetick.setNickChanged(ZonedDateTime.now());

        memetickRepository.save(memetick);
    }

    public MemetickRatingAPI rating(MemetickRatingFilter filter) {
        HashMap<MemetickRatingFilter, Function<Memetick, Long>> mapFilter = new HashMap<>() {{
            put(MemetickRatingFilter.DNA, Memetick::getDna);
            put(MemetickRatingFilter.IND, m -> memeRepository.countByMemetickIdAndType(m.getId(), MemeType.INDIVID).orElse(0L));
            put(MemetickRatingFilter.CHR, m -> memeRepository.sumChromosomeByMemetickId(m.getId()).orElse(0L));
        }};

        List<Memetick> memeticks = memetickRepository.findAll();

        Map<UUID, Long> rateMap = memeticks
            .stream()
            .collect(Collectors.toMap(Memetick::getId, m -> mapFilter.get(filter).apply(m)));

        memeticks.sort(Comparator.comparing(m -> 0 - rateMap.get(m.getId())));

        Map<UUID, Integer> posMap = memeticks
            .stream()
            .collect(Collectors.toMap(AbstractEntity::getId, memeticks::indexOf));

        Memetick memetick = securityService.getCurrentMemetick();
        MemetickRatingDTO me = memetickMapper.toRatingDTO(
            memetick,
            rateMap.get(memetick.getId()),
            posMap.get(memetick.getId())
        );

        List<MemetickRatingDTO> top = memeticks
            .stream()
            .limit(10)
            .map(m -> memetickMapper.toRatingDTO(m, rateMap.get(m.getId()), posMap.get(m.getId())))
            .collect(Collectors.toList());

        return new MemetickRatingAPI(top, me);
    }

    public Memetick findById(UUID id) {
        Optional<Memetick> byId = memetickRepository.findById(id);
        if (byId.isEmpty()) throw new EntityNotFoundException(Memetick.class, "id");
        return byId.get();
    }
}
