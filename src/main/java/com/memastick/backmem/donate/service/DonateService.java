package com.memastick.backmem.donate.service;

import com.memastick.backmem.donate.api.DonateAPI;
import com.memastick.backmem.donate.entity.DonateMessage;
import com.memastick.backmem.donate.entity.DonateRating;
import com.memastick.backmem.donate.repository.DonateMessageRepository;
import com.memastick.backmem.donate.repository.DonateRatingRepository;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.memastick.backmem.main.constant.GlobalConstant.DONATE_RATE;
import static com.memastick.backmem.main.constant.LinkConstant.NETRAL_AVATAR;
import static com.memastick.backmem.memotype.constant.MemotypeRarity.INCREDIBLE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DonateService {

    private final DonateMessageRepository messageRepository;
    private final DonateRatingRepository ratingRepository;

    @Transactional(readOnly = true)
    public DonateAPI read() {
        return new DonateAPI(
            readRandom(),
            stateRating()
        );
    }

    public DonateMessage readRandom() {
        List<DonateMessage> messages = messageRepository.findAll();
        int index = MathUtil.rand(messages.size() - 1);
        return messages.isEmpty() ? null : messages.get(index);
    }

    private Map<MemotypeRarity, List<DonateRating>> stateRating() {
        List<DonateRating> ratings = new ArrayList<>(ratingRepository.findAll());
        fillRating(ratings);

        return ratings
            .stream()
            .sorted(Comparator.comparing(DonateRating::getTime).reversed())
            .collect(Collectors.groupingBy(DonateRating::getRarity, LinkedHashMap::new, toList()));
    }

    private void fillRating(List<DonateRating> ratings) {
        if (ratings.size() >= 15) return; // TODO REMOVE

        Map<MemotypeRarity, List<DonateRating>> ratingsByRarity = ratings
            .stream()
            .collect(Collectors.groupingBy(DonateRating::getRarity));

        MemotypeRarity[] rarities = MemotypeRarity.values();

        Arrays.stream(rarities).forEach(rarity -> {
            List<DonateRating> ratingsOfRarity = ratingsByRarity.getOrDefault(rarity, new ArrayList<>());

            int countRarity = rarities.length - rarity.getLvl() + 1;
            int countEmpties = countRarity - ratingsOfRarity.size();

            if (ratingsOfRarity.size() >= countRarity) return;

            IntStream.range(0, countEmpties).forEach(i -> {
                DonateRating rating = new DonateRating();

                rating.setName("СВОБОДНО");
                rating.setAvatar(NETRAL_AVATAR);
                rating.setRarity(rarity);
                rating.setAmount(rarity.getLvl() * 100);

                ratings.add(rating);
            });
        });
    }

    public void createMessage(DonateMessage donate) {
        donate.setNumber(messageRepository.count());
        messageRepository.save(donate);
    }

    public void createRating(DonateRating donate) {
        int lvl = Math.min(donate.getAmount() / DONATE_RATE, INCREDIBLE.getLvl());
        MemotypeRarity rarity = MemotypeRarity.findByLvl(lvl);

        if (rarity != null) {
            donate.setRarity(rarity);
            ratingRepository.save(donate);
        }
    }

    public List<DonateMessage> readMessages() {
        return messageRepository.findAll(
            Sort.by(Sort.Order.desc("number"))
        );
    }

    public List<DonateRating> readRatings() {
        return ratingRepository.findAll(
            Sort.by(Sort.Order.desc("time"))
        );
    }
}
