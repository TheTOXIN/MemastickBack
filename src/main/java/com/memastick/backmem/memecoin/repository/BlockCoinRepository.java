package com.memastick.backmem.memecoin.repository;

import com.memastick.backmem.memecoin.entity.BlockCoin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlockCoinRepository extends CrudRepository<BlockCoin, UUID> {

    Optional<BlockCoin> findByMemetickId(UUID memetickId);
}
