package com.memastick.backmem.donaters.entity;

import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "donater_ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonaterRating extends DonaterAbstract {

    @Column(nullable = false)
    private String avatar;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MemotypeRarity rarity;
}
