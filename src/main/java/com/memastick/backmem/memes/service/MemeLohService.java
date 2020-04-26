package com.memastick.backmem.memes.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.EntityExistException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.constant.ValidConstant;
import com.memastick.backmem.main.projection.MemeLohSum;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.main.util.ValidationUtil;
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

import static com.memastick.backmem.main.constant.ValidConstant.MAX_LOH;
import static com.memastick.backmem.main.constant.ValidConstant.MIN_LOH;
import static com.memastick.backmem.main.util.MathUtil.limit;

@Service
@RequiredArgsConstructor
public class MemeLohService {

    private final MemeRepository memeRepository;
    private final MemeLohRepository memeLohRepository;
    private final OauthData oauthData;

    public MemeLohDTO readByMeme(UUID memeId) {
        MemeLohDTO res = new MemeLohDTO();

        MemeLohSum sum = memeLohRepository.sumByMemeId(memeId);

        res.setLol(sum.getLol() != null ? sum.getLol(): 0);
        res.setOmg(sum.getOmg() != null ? sum.getOmg(): 0);
        res.setHmm(sum.getHmm() != null ? sum.getHmm(): 0);

        return res;
    }

    public void saveByMeme(UUID memeId, MemeLohDTO dto) {
        if (!ValidationUtil.validLoh(dto)) throw new ValidationException(ErrorCode.MEME_LOH);

        Memetick memetick = oauthData.getCurrentMemetick();
        Meme meme = memeRepository.tryFindById(memeId);

        boolean alreadyExist = memeLohRepository.existsByMemeAndMemetick(meme, memetick);
        if (alreadyExist) throw new EntityExistException(MemeLoh.class);

        MemeLoh loh = new MemeLoh(meme, memetick);

        loh.setLol(limit(dto.getLol(), MAX_LOH, MIN_LOH));
        loh.setOmg(limit(dto.getOmg(), MAX_LOH, MIN_LOH));
        loh.setHmm(limit(dto.getHmm(), MAX_LOH, MIN_LOH));

        memeLohRepository.save(loh);
    }
}
