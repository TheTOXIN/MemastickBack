package com.memastick.backmem.donaters.service;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.repository.DonaterMessageRepository;
import com.memastick.backmem.donaters.repository.DonaterRatingRepository;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static com.memastick.backmem.main.constant.LinkConstant.NETRAL_AVATAR;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DonaterService {

    private final DonaterMessageRepository messageRepository;
    private final DonaterRatingRepository ratingRepository;

    public void createMessage(DonaterMessage donater) {
        donater.setNumber(messageRepository.count());
        messageRepository.save(donater);
    }

    public List<DonaterMessage> readAll() {
        return messageRepository.findAll(
            Sort.by(Sort.Order.desc("number"))
        );
    }

    public void createRating(DonaterRating donater) {
        ratingRepository.save(donater);
    }

    public DonaterMessage readRandom() {
        List<DonaterMessage> messages = messageRepository.findAll();
        int index = MathUtil.rand(messages.size() - 1);
        return messages.isEmpty() ? null : messages.get(index);
    }

    public Map<MemotypeRarity, List<DonaterRating>> readRating() {
        List<DonaterRating> ratings = new ArrayList<>();
        ratingRepository.findAll().forEach(ratings::add);
        fillRating(ratings);

        return ratings
            .stream()
            .sorted(Comparator.comparing(DonaterRating::getTime).reversed())
            .collect(Collectors.groupingBy(DonaterRating::getRarity, LinkedHashMap::new, toList()));
    }

    private void fillRating(List<DonaterRating> ratings) {
        if (ratings.size() >= 15) return; // TODO REMOVE

        Map<MemotypeRarity, List<DonaterRating>> ratingsByRarity = ratings
            .stream()
            .collect(Collectors.groupingBy(DonaterRating::getRarity));

        MemotypeRarity[] rarities = MemotypeRarity.values();

        Arrays.stream(rarities).forEach(rarity -> {
            List<DonaterRating> ratingsOfRarity = ratingsByRarity.getOrDefault(rarity, new ArrayList<>());

            int countRarity = rarities.length - rarity.getLvl() + 1;
            int countEmpties = countRarity - ratingsOfRarity.size();

            if (ratingsOfRarity.size() >= countRarity) return;

            IntStream.range(0, countEmpties).forEach(i -> {
                DonaterRating rating = new DonaterRating();

                rating.setName("ПРИМЕР ДОНАТА");
                rating.setAvatar(NETRAL_AVATAR);
                rating.setRarity(rarity);

                ratings.add(rating);
            });
        });
    }
}
