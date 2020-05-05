package com.memastick.backmem.main.service;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.repository.DonaterMessageRepository;
import com.memastick.backmem.donaters.repository.DonaterRatingRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.memastick.backmem.main.constant.GlobalConstant.URL;

@Service
@RequiredArgsConstructor
public class MigrateService {

    private final MemotypeRepository memotypeRepository;

    public void migrate() {
        memotypeRepository.findAll().forEach(memotype -> {
            if (memotype.getImage().startsWith(URL)) {
                memotype.setImage(
                    memotype.getImage().replaceAll("\\.png", ".webp")
                );
                memotypeRepository.save(memotype);
            }
        });
    }
}
