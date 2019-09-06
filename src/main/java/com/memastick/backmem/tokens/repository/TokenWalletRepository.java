package com.memastick.backmem.tokens.repository;

import com.memastick.backmem.tokens.entity.TokenWallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenWalletRepository extends CrudRepository<TokenWallet, UUID> {

    TokenWallet findByMemetickId(UUID memetickId);
}
