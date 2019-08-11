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
        Optional<BlockCoin> optional = this.findByMemetickId(memetick.getId());
        if (optional.isEmpty()) throw new BlockCoinException("Block not found");
        return optional.get();
    }
}
