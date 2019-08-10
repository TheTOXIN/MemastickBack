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

        refreshBlock(memetick, block, 0);

        return blockCoinRepository.save(block).getHash();
    }

    @Transactional
    public String mineBlock(int nonce) {
        Memetick memetick = oauthData.getCurrentMemetick();
        BlockCoin block = findByMemetick(memetick);

        String target = CryptoUtil.profString();
        String hash = CryptoUtil.hashSHA256(block.getHash() + nonce);

        if (!hash.startsWith(target)) throw new BlockCoinException("Nonce not fit");
        else refreshBlock(memetick, block, block.getCache() + 1);

        return blockCoinRepository.save(block).getHash();
    }

    @Transactional
    public void flushBlock() {
        Memetick memetick = oauthData.getCurrentMemetick();
        BlockCoin block = findByMemetick(memetick);

        if (block.getCache() == 0) return;

        memeCoinService.transaction(memetick, block.getCache());
        blockCoinRepository.delete(block);
    }

    private void refreshBlock(Memetick memetick, BlockCoin block, int cache) {
        var timestamp = new Timestamp(System.currentTimeMillis());
        var hash = CryptoUtil.hashSHA256(memetick.getId().toString() + timestamp.getTime());

        block.setTimestamp(timestamp);
        block.setHash(hash);

        block.setCache(cache);
    }

    private BlockCoin findByMemetick(Memetick memetick) {
        Optional<BlockCoin> optional = blockCoinRepository.findByMemetickId(memetick.getId());
        if (optional.isEmpty()) throw new BlockCoinException("Block not found");
        return optional.get();
    }
}
