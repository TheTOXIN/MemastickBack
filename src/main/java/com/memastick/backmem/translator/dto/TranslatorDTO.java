package com.memastick.backmem.translator.dto;

import com.memastick.backmem.memes.entity.Meme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslatorDTO {

    private Meme meme;
    private File file;
    private String text;
}
