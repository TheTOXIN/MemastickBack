package com.memastick.backmem.memecoin.controller;

import com.memastick.backmem.memecoin.api.MemeCoinAPI;
import com.memastick.backmem.memecoin.entity.MemeCoin;
import com.memastick.backmem.memecoin.repository.MemeCoinRepository;
import com.memastick.backmem.security.component.OauthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("meme-coins")
public class MemeCoinController {

    private final MemeCoinRepository coinRepository;
    private final OauthData oauthData;

    public MemeCoinController(
        MemeCoinRepository coinRepository,
        OauthData oauthData
    ) {
        this.coinRepository = coinRepository;
        this.oauthData = oauthData;
    }

    @GetMapping("history")
    public List<MemeCoinAPI> history(Pageable pageable) {
        return coinRepository.findByMemetick(
            oauthData.getCurrentMemetick(),
            pageable
        ).stream().map(MemeCoinAPI::new).collect(Collectors.toList());
    }
}
