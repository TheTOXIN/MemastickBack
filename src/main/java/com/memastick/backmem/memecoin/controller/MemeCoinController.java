package com.memastick.backmem.memecoin.controller;

import com.memastick.backmem.memecoin.api.MemeCoinAPI;
import com.memastick.backmem.memecoin.repository.MemeCoinRepository;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.service.MemetickService;
import com.memastick.backmem.security.component.OauthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("meme-coins")
public class MemeCoinController {

    private final MemeCoinRepository coinRepository;
    private final MemeCoinService memeCoinService;
    private final MemetickService memetickService;
    private final OauthData oauthData;

    @Autowired
    public MemeCoinController(
        MemeCoinRepository coinRepository,
        MemeCoinService memeCoinService,
        OauthData oauthData,
        MemetickService memetickService
    ) {
        this.coinRepository = coinRepository;
        this.memeCoinService = memeCoinService;
        this.oauthData = oauthData;
        this.memetickService = memetickService;
    }

    @GetMapping("history")
    public Page<MemeCoinAPI> history(Pageable pageable) {
        return coinRepository.findByMemetickId(
            oauthData.getCurrentMemetick().getId(),
            pageable
        ).map(MemeCoinAPI::new);
    }

    @PutMapping("transaction/{value}")
    public void transaction(
        @PathVariable("value") long value,
        @RequestBody UUID memetickId
    ) {
        memeCoinService.transaction(
            memetickService.findById(memetickId),
            value
        );
    }
}
