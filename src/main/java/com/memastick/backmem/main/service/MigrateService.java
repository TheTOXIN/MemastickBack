package com.memastick.backmem.main.service;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.repository.DonaterMessageRepository;
import com.memastick.backmem.donaters.repository.DonaterRatingRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.memotype.repository.MemotypeSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.memastick.backmem.main.constant.GlobalConstant.URL;

@Service
@RequiredArgsConstructor
public class MigrateService {

    private final MemotypeRepository memotypeRepository;
    private final MemotypeSetRepository memotypeSetRepository;

    public List<UUID> memotypesIds = Arrays.asList(
        UUID.fromString("04c35b59-7a26-4d30-be5f-f22db2c4ea67"),
        UUID.fromString("66b354a8-5095-40ad-943a-5daf7bd9eb23"),
        UUID.fromString("4f508d5a-9893-4ce7-b2d3-5360eb1bf1d1")
    );

    @Transactional
    public void migrate() {
        memotypesIds.forEach(id -> {
            Optional<Memotype> optional = memotypeRepository.findById(id);
            if (optional.isEmpty()) return;

            Memotype memotype = optional.get();
            MemotypeSet set = memotypeSetRepository.findById(memotype.getSetId()).get();

            set.setSize(set.getSize() - 1);

            memotypeRepository.findAllBySetId(set.getId()).forEach(m -> {
                if (m.getNumber() > memotype.getNumber()) {
                    m.setNumber(m.getNumber() - 1);
                    memotypeRepository.save(m);
                }
            });

            memotypeRepository.delete(memotype);
            memotypeSetRepository.save(set);
        });
    }
}
