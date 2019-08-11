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
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BlockCoinService {

    private final BlockCoinRepository blockCoinRepository;
    private final MemeCoinService memeCoinService;
    private final PickaxeService pickaxeService;
    private final OauthData oauthData;

    @Transactional
    public String makeBlock() {
        Memetick memetick = oauthData.getCurrentMemetick();
        Optional<BlockCoin> optional = blockCoinRepository.findByMemetickId(memetick.getId());

        BlockCoin block = optional.orElse(new BlockCoin(memetick.getId()));

        if (optional.isEmpty()) {
            var timestamp = new Timestamp(System.currentTimeMillis());
            var hash = CryptoUtil.hashSHA256(memetick.getId().toString() + timestamp.getTime());

            block.setTimestamp(timestamp);
            block.setHash(hash);
        } else {
            block.setNonce(0);
            block.setCache(0);
        }

        return blockCoinRepository.save(block).getHash();
    }

    @Transactional
    public void mineBlock(int nonce) {
        if (nonce < 0) throw new BlockCoinException("Nonce less zero");

        Memetick memetick = oauthData.getCurrentMemetick();
        BlockCoin block = blockCoinRepository.findByMemetick(memetick);

        pickaxeService.checkate(block.getNonce());

        String target = CryptoUtil.profString();
        String hash = CryptoUtil.hashSHA256(block.getHash() + nonce);

        if (!hash.startsWith(target)) throw new BlockCoinException("Nonce not fit");

        block.setHash(CryptoUtil.hashSHA256(hash));
        block.setCache(block.getCache() + 1);
        block.setNonce(block.getNonce() + nonce);

        blockCoinRepository.save(block);
    }

    @Transactional
    public void flushBlock(UUID token) {
        pickaxeService.deactivate(token);

        Memetick memetick = oauthData.getCurrentMemetick();
        BlockCoin block = blockCoinRepository.findByMemetick(memetick);

        blockCoinRepository.delete(block);
        memeCoinService.transaction(memetick, block.getCache());
    }
}
