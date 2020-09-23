package com.memastick.backmem.donate.entity;

import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donate_ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonateRating extends DonateAbstract {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MemotypeRarity rarity;

    @Column(nullable = false)
    private LocalDateTime time = LocalDateTime.now();
}
