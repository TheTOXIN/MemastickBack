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

    @Transactional
    public void migrate() {

    }
}
