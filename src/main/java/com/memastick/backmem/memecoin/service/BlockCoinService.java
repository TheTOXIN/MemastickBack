package com.memastick.backmem.memecoin.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.BlockCoinException;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.util.CryptoUtil;
import com.memastick.backmem.memecoin.entity.BlockCoin;
import com.memastick.backmem.memecoin.repository.BlockCoinRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.entity.MemetickInventory;
import com.memastick.backmem.memetick.repository.MemetickInventoryRepository;
import com.memastick.backmem.memetick.service.MemetickInventoryService;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.BCException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_NONCE;
import static com.memastick.backmem.main.constant.GlobalConstant.PICKAXE_HOURS;

@Service
@AllArgsConstructor
public class BlockCoinService {

    private final BlockCoinRepository blockCoinRepository;
    private final MemeCoinService memeCoinService;
    private final MemetickInventoryService inventoryService;
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
        }

        block.setCache(0);

        return blockCoinRepository.save(block).getHash();
    }

    @Transactional
    public void mineBlock(int nonce) {
        if (nonce < 0) throw new BlockCoinException("Nonce less zero");

        Memetick memetick = oauthData.getCurrentMemetick();
        BlockCoin block = blockCoinRepository.findByMemetick(memetick);

        if ((block.getNonce()) > MAX_NONCE) throw new BlockCoinException(ErrorCode.MINE_END);

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
        if (!inventoryService.checkPickaxe(token)) throw new BlockCoinException("Token expire");

        Memetick memetick = oauthData.getCurrentMemetick();
        BlockCoin block = blockCoinRepository.findByMemetick(memetick);

        blockCoinRepository.delete(block);
        memeCoinService.transaction(memetick, block.getCache());
    }
}
