package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.exception.EntityExistException;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.memes.dto.MemeLohDTO;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memes.entity.MemeLoh;
import com.memastick.backmem.memes.repository.MemeLohRepository;
import com.memastick.backmem.memes.repository.MemeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_LOH;

@Service
@RequiredArgsConstructor
public class MemeLohService {

    private final MemeRepository memeRepository;
    private final MemeLohRepository memeLohRepository;
    private final OauthData oauthData;

    public MemeLohDTO readByMeme(UUID memeId) {
        MemeLohDTO res = new MemeLohDTO();

        memeLohRepository.findAllByMemeId(memeId).forEach(loh -> {
            res.setLol(res.getLol() + loh.getLol());
            res.setOmg(res.getOmg() + loh.getOmg());
            res.setHmm(res.getHmm() + loh.getHmm());
        });

        return res;
    }

    public void saveByMeme(UUID memeId, MemeLohDTO dto) {
        Memetick memetick = oauthData.getCurrentMemetick();
        Meme meme = memeRepository.tryFindById(memeId);

        boolean alreadyExist = memeLohRepository.existsByMemeAndMemetick(meme, memetick);
        if (alreadyExist) throw new EntityExistException(MemeLoh.class);

        MemeLoh loh = new MemeLoh(meme, memetick);

        loh.setLol(Math.min(dto.getLol(), MAX_LOH));
        loh.setOmg(Math.min(dto.getOmg(), MAX_LOH));
        loh.setHmm(Math.min(dto.getHmm(), MAX_LOH));

        memeLohRepository.save(loh);
    }
}
