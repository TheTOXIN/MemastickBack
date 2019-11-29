package com.memastick.backmem.main.service;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.service.DonaterService;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MigrateService {

    private final DonaterService donaterService;

    public void migrate() {
        for (MemotypeRarity rarity : MemotypeRarity.values()) {
            for (int i = 1; i <= 6 - rarity.getLvl(); i++) {
                DonaterRating rating = new DonaterRating();

                rating.setRarity(rarity);
                rating.setName("МЕСТО СВОБОДНО");

                donaterService.createRating(rating);
            }
        }

        DonaterMessage message = new DonaterMessage();

        message.setName("Юрий Белоусов");
        message.setMessage("Не хочешь срать? Не мучай жопу!");

        donaterService.createMessage(message);
    }
}
