package com.memastick.backmem.evolution.service;

import com.memastick.backmem.evolution.entity.EvolveMeme;
import com.memastick.backmem.evolution.repository.EvolveMemeRepository;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.notification.service.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EvolveSelecterService {

    private static final Logger log = LoggerFactory.getLogger(EvolveNexterService.class);

    private final EvolveMemeRepository evolveMemeRepository;
    private final EvolveMemeService evolveMemeService;
    private final NotifyService notifyService;
    private final MemeCoinService coinService;

    @Autowired
    public EvolveSelecterService(
        EvolveMemeService evolveMemeService,
        EvolveMemeRepository evolveMemeRepository,
        NotifyService notifyService,
        MemeCoinService coinService
    ) {
        this.evolveMemeService = evolveMemeService;
        this.evolveMemeRepository = evolveMemeRepository;
        this.notifyService = notifyService;
        this.coinService = coinService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void select() {
        log.info("START SELECT EVOLVE - {}", evolveMemeService.computeEvolution());

        // TODO OPTIMIZE

        List<EvolveMeme> evolveMemes = evolveMemeRepository.findAllSelection()
            .stream()
            .peek(e -> e.setChance(evolveMemeService.computeChance(e.getMeme())))
            .sorted(Comparator.comparing(EvolveMeme::getChance))
            .collect(Collectors.toList());

        if (evolveMemes.isEmpty()) return;

        float avg = evolveMemes.get(evolveMemes.size() / 2).getChance();

        evolveMemes.forEach(e -> {
            Meme meme = e.getMeme();

            boolean isSurvive = e.getChance() >= avg || e.isImmunity();
            meme.setType(isSurvive ? MemeType.INDV : MemeType.DEAD);

            notifyService.sendMEME(meme);
            if (isSurvive) coinService.transaction(meme.getMemetick(), 100);
        });

        evolveMemeRepository.saveAll(evolveMemes);

        log.info("END SELECT EVOLVE - {}", evolveMemeService.computeEvolution());
    }
}
