package com.memastick.backmem.main.service;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.repository.DonaterMessageRepository;
import com.memastick.backmem.donaters.repository.DonaterRatingRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MigrateService {

    public void migrate() {

    }
}
