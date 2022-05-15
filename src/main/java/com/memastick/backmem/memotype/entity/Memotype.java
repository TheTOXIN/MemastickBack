package com.memastick.backmem.memotype.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "memotype")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Memotype extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemotypeRarity rarity;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private UUID setId;

    @Column(nullable = false)
    private int number;
}
