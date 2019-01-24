package com.memastick.backmem.memes.repository;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.person.entity.Memetick;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface MemeRepository extends PagingAndSortingRepository<Meme, UUID> {

    List<Meme> findAllByMemetick(Memetick memetick, Pageable pageable);

    long sumDna();

    long countByMemetickId(UUID memetickId);

}
