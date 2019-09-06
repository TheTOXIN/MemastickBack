package com.memastick.backmem.memecoin.repository;

import com.memastick.backmem.errors.exception.BlockCoinException;
import com.memastick.backmem.memecoin.entity.BlockCoin;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlockCoinRepository extends CrudRepository<BlockCoin, UUID> {

    Optional<BlockCoin> findByMemetickId(UUID memetickId);

    default BlockCoin findByMemetick(Memetick memetick) {
        return this
            .findByMemetickId(memetick.getId())
            .orElseThrow(() -> new BlockCoinException("Block not found"));
    }
}
