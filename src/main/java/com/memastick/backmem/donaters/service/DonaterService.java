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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.memastick.backmem.main.constant.GlobalConstant.DEFAULT_DONATER;
import static com.memastick.backmem.main.constant.LinkConstant.NETRAL_AVATAR;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DonaterService {

    private final DonaterMessageRepository messageRepository;
    private final DonaterRatingRepository ratingRepository;

    public void createMessage(DonaterMessage donater) {
        if (donater.getAvatar() == null) donater.setAvatar(NETRAL_AVATAR);

        long count = messageRepository.count();
        donater.setNumber(count);

        messageRepository.save(donater);
    }

    public void createRating(DonaterRating donater) {
        if (donater.getAvatar() == null) donater.setAvatar(NETRAL_AVATAR);

        Optional<DonaterRating> optional = ratingRepository.findFirstByNameAndRarity(DEFAULT_DONATER, donater.getRarity());
        optional.ifPresent(ratingRepository::delete);

        ratingRepository.save(donater);
    }

    public DonaterMessage readRandom() {
        List<DonaterMessage> messages = messageRepository.findAll();
        int index = MathUtil.rand(messages.size() - 1);
        return messages.isEmpty() ? null : messages.get(index);
    }

    public Map<MemotypeRarity, List<DonaterRating>> readRating() {
        return StreamSupport
            .stream(ratingRepository.findAll().spliterator(), false)
            .sorted(Comparator.comparing(DonaterRating::getTime).reversed())
            .collect(Collectors.groupingBy(DonaterRating::getRarity, LinkedHashMap::new, toList()));
    }

    public List<DonaterMessage> readAll() {
        return messageRepository.findAll(
            Sort.by(Sort.Order.desc("number"))
        );
    }
}
