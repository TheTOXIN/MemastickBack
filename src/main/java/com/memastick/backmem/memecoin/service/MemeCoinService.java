package com.memastick.backmem.memecoin.service;

import com.memastick.backmem.errors.exception.MemeCoinNotEnoughException;
import com.memastick.backmem.memecoin.entity.MemeCoin;
import com.memastick.backmem.memecoin.repository.MemeCoinRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.notification.service.NotifyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class MemeCoinService {

    private final MemeCoinRepository coinRepository;
    private final NotifyService notifyService;

    public MemeCoinService(
        MemeCoinRepository coinRepository,
        NotifyService notifyService
    ) {
        this.coinRepository = coinRepository;
        this.notifyService = notifyService;
    }

    @Transactional
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

    public long balance(Memetick memetick) {
        return coinRepository
            .sumValueByMemetick(memetick.getId())
            .orElse(0L);
    }
}
