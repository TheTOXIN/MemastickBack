package com.memastick.backmem.chat.service;

import com.memastick.backmem.chat.enitity.ChatMessage;
import com.memastick.backmem.chat.repository.ChatMessageRepository;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.memastick.backmem.chat.validation.ChatMessageValidation.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final OauthData oauthData;

    @Transactional
    public void save(ChatMessage message) {
        if (!valid(message)) return;

        message.setCreating(ZonedDateTime.now());
        message.setNumber(null);

        chatMessageRepository.save(message);
    }

    public List<ChatMessage> read(Pageable pageable) {
        Memetick memetick = oauthData.getCurrentMemetick();

        return chatMessageRepository.findAll(pageable).getContent()
            .stream()
            .peek(message -> message.setMy(memetick.getId().equals(message.getMemetickId())))
            .collect(Collectors.toList());
    }

    public void delete(Long number) {
        chatMessageRepository.deleteById(number);
    }
}
