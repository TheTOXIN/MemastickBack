package com.memastick.backmem.chat.service;

import com.memastick.backmem.chat.api.ChatConnectAPI;
import com.memastick.backmem.chat.component.ChatListener;
import com.memastick.backmem.chat.constant.ChatMessageMode;
import com.memastick.backmem.chat.enitity.ChatMessage;
import com.memastick.backmem.chat.repository.ChatMessageRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.memotype.service.MemotypeMemetickService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.memastick.backmem.chat.validation.ChatMessageValidation.valid;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final OauthData oauthData;
    private final ChatListener chatListener;
    private final MemetickRepository memetickRepository;
    private final MemotypeRepository memotypeRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemotypeMemetickService memotypeMemetickService;

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

    public List<ChatMessage> readHome() {
        return chatMessageRepository.finFirstByCount(10);
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

    @Transactional(readOnly = true)
    public ChatConnectAPI connect() {
        User user = oauthData.getCurrentUser();

        Memetick memetick = user.getMemetick();
        MemotypeMemetickAPI memotypeAPI = memotypeMemetickService.read(memetick.getId());

        List<UUID> onlineMemetickIds = chatListener.online();
        onlineMemetickIds.add(memetick.getId());

        return new ChatConnectAPI(
            memetick.getId(),
            user.getRole(),
            onlineMemetickIds,
            memotypeAPI.getContent()
        );
    }
}
