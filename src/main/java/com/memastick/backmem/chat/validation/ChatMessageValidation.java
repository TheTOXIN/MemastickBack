package com.memastick.backmem.chat.validation;

import com.memastick.backmem.chat.enitity.ChatMessage;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TEXT_LEN;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ChatMessageValidation {

    public static boolean valid(ChatMessage msg) {
        switch (msg.getMode()) {
            case TEXT: return !isBlank(msg.getText()) && msg.getText().length() < MAX_TEXT_LEN;
            case STICKER: return !isBlank(msg.getSticker()) && msg.getMemotypeId() != null;
            case CONNECT:
            case DISCONNECT:
            default: return false;
        }
    }
}
