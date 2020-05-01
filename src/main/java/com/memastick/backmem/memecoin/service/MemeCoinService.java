package com.memastick.backmem.memecoin.service;

import com.memastick.backmem.errors.exception.MemeCoinNotEnoughException;
import com.memastick.backmem.memecoin.entity.MemeCoin;
import com.memastick.backmem.memecoin.repository.MemeCoinRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.notification.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class MemeCoinService {

    private final MemeCoinRepository coinRepository;
    private final NotifyService notifyService;

    @Transactional
    @CacheEvict(value = "balanceMemeCoins", key = "#memetick.id")
    public void transaction(Memetick memetick, long value) {
        if (value == 0) return;
        if (value < 0 && (balance(memetick) + value) < 0) throw new MemeCoinNotEnoughException();

        coinRepository.save(new MemeCoin(
            value,
            memetick.getId(),
            ZonedDateTime.now()
        ));

        notifyService.sendMEMECOIN(memetick, value);
    }

    @Cacheable(value = "balanceMemeCoins", key = "#memetick.id")
    public long balance(Memetick memetick) {
        return coinRepository
            .sumValueByMemetick(memetick.getId())
            .orElse(0L);
    }
}
