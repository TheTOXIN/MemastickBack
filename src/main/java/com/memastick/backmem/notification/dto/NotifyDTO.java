package com.memastick.backmem.notification.dto;

import com.memastick.backmem.memes.constant.MemeType;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.tokens.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyDTO {

    private Meme meme;
    private MemeType type;
    private int dna;
    private TokenType token;
    private Memetick memetick;

    public NotifyDTO(Meme meme, MemeType type) {
        this.meme = meme;
        this.type = type;
    }

    public NotifyDTO(int dna) {
        this.dna = dna;
    }

    public NotifyDTO(Meme meme, TokenType token) {
        this.meme = meme;
        this.token = token;
    }

    public NotifyDTO(Memetick memetick) {
        this.memetick = memetick;
    }
}
