package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.entity.Meme;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemeRepository extends PagingAndSortingRepository<Meme, UUID> {

    long countByMemetickId(UUID memetickId);

}
