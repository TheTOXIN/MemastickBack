package com.memastick.backmem.main.service;

import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.main.api.HomeAPI;
import com.memastick.backmem.main.component.HelloMessageGenerate;
import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MainService {

    private final OauthData oauthData;
    private final EvolveMemeService evolveMemeService;
    private final MemeRepository memeRepository;

    private final HelloMessageGenerate messageGenerate;

    public HomeAPI home() {
        return new HomeAPI(
            oauthData.getCurrentMemetick().getNick(),
            messageGenerate.getMessage(),
            evolveMemeService.computeEvolution(),
            memeRepository.countByType(MemeType.EVLV).orElse(0L),
            evolveMemeService.computeSelectTimer()
        );
    }
}
