package com.memastick.backmem.memecoin.service;

import com.memastick.backmem.errors.exception.MemeCoinNotEnoughException;
import com.memastick.backmem.memecoin.entity.MemeCoin;
import com.memastick.backmem.memecoin.repository.MemeCoinRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class MemeCoinService {

    private final MemeCoinRepository coinRepository;

    public MemeCoinService(MemeCoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Transactional
    public void transaction(Memetick memetick, long value) {
        if (value < 0 && (balance(memetick) + value) < 0) throw new MemeCoinNotEnoughException();

        MemeCoin memeCoin = new MemeCoin(
            value,
            memetick,
            ZonedDateTime.now()
        );

        coinRepository.save(memeCoin);
    }

    public long balance(Memetick memetick) {
        return coinRepository
            .sumValueByMemetick(memetick)
            .orElse(0L);
    }
}
