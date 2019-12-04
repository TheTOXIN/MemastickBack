package com.memastick.backmem.donaters.service;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import com.memastick.backmem.donaters.entity.DonaterRating;
import com.memastick.backmem.donaters.repository.DonaterMessageRepository;
import com.memastick.backmem.donaters.repository.DonaterRatingRepository;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DonaterService {

    private final DonaterMessageRepository messageRepository;
    private final DonaterRatingRepository ratingRepository;

    public void createMessage(DonaterMessage donater) {
        if (donater.getAvatar() == null) donater.setAvatar(LinkConstant.NETRAL_AVATAR);

        long count = messageRepository.count();
        donater.setNumber(count + 1);

        messageRepository.save(donater);
    }

    public void createRating(DonaterRating donater) {
        if (donater.getAvatar() == null) donater.setAvatar(LinkConstant.NETRAL_AVATAR);

        Optional<DonaterRating> optional = ratingRepository.findFirstByName(GlobalConstant.DEFAULT_DONATER);
        optional.ifPresent(ratingRepository::delete);

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
            .sorted(Comparator.comparing(DonaterRating::getTime).reversed())
            .collect(Collectors.groupingBy(DonaterRating::getRarity, LinkedHashMap::new, toList()));
    }
}
