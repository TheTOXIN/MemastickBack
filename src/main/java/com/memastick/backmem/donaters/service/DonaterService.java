package com.memastick.backmem.donaters.service;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.repository.DonaterMessageRepository;
import com.memastick.backmem.donaters.repository.DonaterRatingRepository;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DonaterService {

    private final DonaterMessageRepository messageRepository;
    private final DonaterRatingRepository ratingRepository;

    public void createMessage(DonaterMessage donater) {
        messageRepository.save(donater);
    }

    public void createRating(DonaterRating donater) {
        ratingRepository.save(donater);
    }

    public DonaterMessage readRandom() {
        List<DonaterMessage> messages = new ArrayList<>();

        messageRepository.findAll().forEach(messages::add);
        Collections.shuffle(messages);

        return messages.isEmpty() ? null : messages.get(0);
    }

    public Map<MemotypeRarity, List<DonaterRating>> readRating() {
        return StreamSupport
            .stream(ratingRepository.findAll().spliterator(), false)
            .collect(Collectors.groupingBy(DonaterRating::getRarity));
    }
}
