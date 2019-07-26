package com.memastick.backmem.memecoin.repository;

import com.memastick.backmem.memecoin.entity.MemeCoin;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemeCoinRepository extends JpaRepository<MemeCoin, UUID> {

    @Query("SELECT SUM(mc.value) FROM MemeCoin mc WHERE mc.memetick = :memetick")
    Optional<Long> sumValueByMemetick(@Param("memetick")Memetick memetick);
}
