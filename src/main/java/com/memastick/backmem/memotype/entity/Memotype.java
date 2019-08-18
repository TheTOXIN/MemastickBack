package com.memastick.backmem.memotype.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "memotype")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
