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

    private final DonaterRatingRepository ratingRepository;
    private final DonaterMessageRepository messageRepository;

    public void migrate() {
        for (MemotypeRarity rarity : MemotypeRarity.values()) {
            for (int i = 1; i <= 6 - rarity.getLvl(); i++) {
                DonaterRating rating = new DonaterRating();

                rating.setRarity(rarity);
                rating.setName(GlobalConstant.DEFAULT_DONATER);
                rating.setAvatar(LinkConstant.NETRAL_AVATAR);
                rating.setTime(LocalDateTime.now());

                ratingRepository.save(rating);
            }
        }

        DonaterMessage message = new DonaterMessage();

        message.setName("Юрий Белоусов");
        message.setMessage("Не хочешь срать? Не мучай жопу!");
        message.setAvatar("https://firebasestorage.googleapis.com/v0/b/memastick-d525f.appspot.com/o/avatars%2Fphoto_2018-10-05_16-12-46.jpg?alt=media&token=9db022b0-c0c8-4f6d-a86c-621bc51348fa");
        message.setNumber(0L);

        messageRepository.save(message);
    }
}
