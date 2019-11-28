package com.memastick.backmem.donaters.service;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.repository.DonaterMessageRepository;
import com.memastick.backmem.donaters.repository.DonaterRatingRepository;
import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.main.util.MathUtil;
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
        donater.setNumber(messageRepository.count() + 1);
        messageRepository.save(donater);
    }

    public void createRating(DonaterRating donater) {
        if (donater.getAvatar() == null) donater.setAvatar(LinkConstant.NETRAL_AVATAR);
        ratingRepository.save(donater);
    }

    public DonaterMessage readRandom() {
        List<DonaterMessage> messages = new ArrayList<>();
        messageRepository.findAll().forEach(messages::add);
        return messages.isEmpty() ? null : messages.get(MathUtil.rand(messages.size() - 1));
    }

    public Map<MemotypeRarity, List<DonaterRating>> readRating() {
        return StreamSupport
            .stream(ratingRepository.findAll().spliterator(), false)
            .collect(Collectors.groupingBy(DonaterRating::getRarity));
    }
}
