package com.memastick.backmem.memotype.api;

import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemotypeAPI {

    private UUID id;
    private MemotypeRarity rarity;

    private String title;
    private String text;
    private String image;
    private String set;

    private int level;
    private int count;

    public MemotypeAPI(UUID id, MemotypeRarity rarity, String title, String text, String image, String set) {
        this.id = id;
        this.rarity = rarity;
        this.title = title;
        this.text = text;
        this.image = image;
        this.set = set;
        this.level = rarity.getLvl();
    }
}
