package com.memastick.backmem.chat.api;

import com.memastick.backmem.chat.constant.ChatMessageMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageAPI {

    private long number;

    private String text;
    private String nick;
    private String sticker;

    private UUID memetickId;
    private UUID memotypeId;

    private boolean direct;
    private boolean my;

    private ChatMessageMode mode;
}
