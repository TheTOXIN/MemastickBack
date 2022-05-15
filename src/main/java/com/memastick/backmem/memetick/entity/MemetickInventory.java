package com.memastick.backmem.memetick.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.constant.DnaCount;
import com.memastick.backmem.main.constant.TimeConstant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "memetick_inventories")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "joinedInventoryMemetick", includeAllAttributes = true)
public class MemetickInventory extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private Memetick memetick;

    @Column(nullable = false)
    private boolean allowance = true;

    @Column(nullable = false)
    private LocalDateTime cellCreating = TimeConstant.START_LOCAL_TIME;

    @Column(nullable = false)
    private LocalDateTime pickaxeCreating = TimeConstant.START_LOCAL_TIME;

    @Column(nullable = false)
    private boolean cellNotify = false;

    @Min(DnaCount.MIN_CREATE)
    @Max(DnaCount.MAX_CREATE)
    @Column(nullable = false)
    private int cellCombo = DnaCount.MIN_CREATE;

    // TODO MAKE SEPARATE TABLES
}
