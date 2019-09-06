package com.memastick.backmem.memecoin.repository;

import com.memastick.backmem.memecoin.entity.MemeCoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemeCoinRepository extends PagingAndSortingRepository<MemeCoin, UUID> {

    @Query("SELECT SUM(mc.value) FROM MemeCoin mc WHERE mc.memetickId = :memetickId")
    Optional<Long> sumValueByMemetick(@Param("memetickId") UUID memetickId);

    Page<MemeCoin> findByMemetickId(UUID memetickId, Pageable pageable);
}
