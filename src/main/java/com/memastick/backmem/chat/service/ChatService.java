package com.memastick.backmem.chat.service;

import com.memastick.backmem.chat.constant.ChatMessageMode;
import com.memastick.backmem.chat.enitity.ChatMessage;
import com.memastick.backmem.chat.repository.ChatMessageRepository;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.security.component.OauthData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.memastick.backmem.chat.validation.ChatMessageValidation.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MemetickRepository memetickRepository;
    private final MemotypeRepository memotypeRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public void save(ChatMessage message) {
        if (!valid(message)) return;

        if (message.getMemetickId() != null) {
            Optional<String> optionalNick = memetickRepository.findNickByMemetickId(message.getMemetickId());
            if (optionalNick.isEmpty()) return;
            message.setNick(optionalNick.get());
        }

        if (message.getMode().equals(ChatMessageMode.STICKER)) {
            Optional<String> optionalMemotype = memotypeRepository.findImageByMemotypeId(message.getMemotypeId());
            if (optionalMemotype.isEmpty()) return;
            message.setSticker(optionalMemotype.get());
        }

        message.setCreating(ZonedDateTime.now());
        message.setNumber(null);

        chatMessageRepository.save(message);
    }

    public List<ChatMessage> read(Pageable pageable) {
        return chatMessageRepository.findAll(pageable).getContent();
    }

    public void delete(Long number) {
        chatMessageRepository.deleteById(number);
    }

    @Transactional
    public void clearing() {
        chatMessageRepository.deleteAll(
            chatMessageRepository.findBeforeCreating(
                ZonedDateTime.now().minusMonths(1)
            )
        );
    }
}
