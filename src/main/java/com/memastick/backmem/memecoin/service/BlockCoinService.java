package com.memastick.backmem.memecoin.service;

import com.memastick.backmem.errors.exception.BlockCoinException;
import com.memastick.backmem.main.util.CryptoUtil;
import com.memastick.backmem.memecoin.entity.BlockCoin;
import com.memastick.backmem.memecoin.repository.BlockCoinRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class BlockCoinService {

    private final BlockCoinRepository blockCoinRepository;
    private final MemeCoinService memeCoinService;
    private final OauthData oauthData;

    @Transactional
    public String makeBlock() {
        Memetick memetick = oauthData.getCurrentMemetick();

        BlockCoin block = blockCoinRepository
            .findByMemetickId(memetick.getId())
            .orElse(new BlockCoin(memetick.getId()));

        var timestamp = new Timestamp(System.currentTimeMillis());
        var hash = CryptoUtil.hashSHA256(memetick.getId().toString() + timestamp.getTime());

        block.setTimestamp(timestamp);
        block.setHash(hash);
        block.setCache(0);

        return blockCoinRepository.save(block).getHash();
    }

    @Transactional
    public void mineBlock(int nonce) {
        if (nonce < 0) throw new BlockCoinException("Nonce less zero");

        Memetick memetick = oauthData.getCurrentMemetick();
        BlockCoin block = blockCoinRepository.findByMemetick(memetick);

        String target = CryptoUtil.profString();
        String hash = CryptoUtil.hashSHA256(block.getHash() + nonce);

        if (!hash.startsWith(target)) throw new BlockCoinException("Nonce not fit");

        block.setHash(CryptoUtil.hashSHA256(hash));
        block.setCache(block.getCache() + 1);
        block.setNonce(block.getNonce() + nonce);

        blockCoinRepository.save(block);
    }

    @Transactional
    public void flushBlock() {
        Memetick memetick = oauthData.getCurrentMemetick();
        BlockCoin block = blockCoinRepository.findByMemetick(memetick);

        if (block.getCache() == 0) return;

        memeCoinService.transaction(memetick, block.getCache());
        blockCoinRepository.delete(block);
    }
}
