package com.memastick.backmem.tokens.repository;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.tokens.entity.TokenWallet;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenWalletRepository extends CrudRepository<TokenWallet, UUID> {

    @CacheEvict(value = "walletByMemetick", key = "#memetick.id")
    default void save(TokenWallet wallet, Memetick memetick) {
        this.save(wallet);
    }

    @Cacheable(value = "walletByMemetick", key = "#memetickId")
    TokenWallet findByMemetickId(UUID memetickId);
}
