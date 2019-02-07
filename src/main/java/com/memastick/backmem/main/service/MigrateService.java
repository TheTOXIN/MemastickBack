package com.memastick.backmem.main.service;

import com.memastick.backmem.main.api.MigrateAPI;
import com.memastick.backmem.memes.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MigrateService {

    private final MemeRepository memeRepository;

    @Autowired
    public MigrateService(
        MemeRepository memeRepository
    ) {
        this.memeRepository = memeRepository;
    }

    public void migrate(MigrateAPI request) {
        memeRepository.findAll().forEach(meme -> {
            for (String url : request.getUrls()) {
                if (url.contains(meme.getFireId().toString())) {
                    meme.setUrl(url);
                    memeRepository.save(meme);
                    break;
                }
            }
        });
    }
}
