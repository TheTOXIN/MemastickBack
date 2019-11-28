package com.memastick.backmem.main.service;

import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.repository.DonaterRatingRepository;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MigrateService {

    private final DonaterRatingRepository donaterRatingRepository;

    public void migrate() {
        if (donaterRatingRepository.count() == 0) {
            for (MemotypeRarity rarity: MemotypeRarity.values()) {
                for (int i = 1; i <= 6 - rarity.getLvl(); i++) {
                    DonaterRating rating = new DonaterRating();

                    rating.setRarity(rarity);
                    rating.setAvatar("https://iupac.org/wp-content/uploads/2018/05/default-avatar.png");
                    rating.setName("TEST_" + i);

                    donaterRatingRepository.save(rating);
                }
            }
        }
    }
}
