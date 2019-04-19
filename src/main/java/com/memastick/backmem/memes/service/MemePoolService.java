package com.memastick.backmem.memes.service;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemePoolService {

    private final MemeRepository memeRepository;

    @Autowired
    public MemePoolService(MemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }

    public List<Meme> generate(Pageable pageable) {
        PageRequest poolPageble = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(
                Sort.Order.desc(("adaptation")),
                Sort.Order.desc(("creating"))
            )
        );

        return memeRepository.findAll(poolPageble).getContent();
    }
}
