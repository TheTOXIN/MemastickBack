package com.memastick.backmem.chat.enitity;

import com.memastick.backmem.chat.constant.ChatMessageMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TEXT_LEN;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false)
    private Long number;

    @Column
    @Length(max = MAX_TEXT_LEN)
    private String text;

    @Column(nullable = false)
    private String nick;

    @Column
    private String sticker;

    @Column(nullable = false)
    private UUID memetickId;

    @Column
    private UUID memotypeId;

    @Column(nullable = false)
    private ChatMessageMode mode;

    @Column(nullable = false)
    private ZonedDateTime creating;
}
