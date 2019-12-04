package com.memastick.backmem.donaters.entity;

import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donater_ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonaterRating extends DonaterAbstract {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MemotypeRarity rarity;

    @Column(nullable = false)
    private LocalDateTime time = LocalDateTime.now();
}
