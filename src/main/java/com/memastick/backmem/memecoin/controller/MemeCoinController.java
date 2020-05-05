package com.memastick.backmem.memecoin.controller;

import com.memastick.backmem.memecoin.api.MemeCoinAPI;
import com.memastick.backmem.memecoin.repository.MemeCoinRepository;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("meme-coins")
@AllArgsConstructor
public class MemeCoinController {

    private final MemeCoinRepository coinRepository;
    private final MemeCoinService memeCoinService;
    private final MemetickRepository memetickRepository;
    private final OauthData oauthData;

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
            memetickRepository.tryFindById(memetickId),
            value
        );
    }
}
