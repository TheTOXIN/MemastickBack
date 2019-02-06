package com.memastick.backmem.memes.api;

import com.memastick.backmem.memes.dto.MemeDTO;
import com.memastick.backmem.memes.dto.MemeLikeStateDTO;
import com.memastick.backmem.person.dto.MemetickPreviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemePageAPI {

    private MemeDTO meme;
    private MemeLikeStateDTO likes;
    private MemetickPreviewDTO memetick;

}
