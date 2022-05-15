package com.memastick.backmem.donate.entity;

import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donate_ratings")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DonateRating extends DonateAbstract {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MemotypeRarity rarity;

    @Column(nullable = false)
    private LocalDateTime time = LocalDateTime.now();
}
