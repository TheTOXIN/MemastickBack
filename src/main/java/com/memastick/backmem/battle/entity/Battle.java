package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.battle.constant.BattleStatus;
import com.memastick.backmem.memes.constant.MemeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "battles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraph(name = "joinedMembers", includeAllAttributes = true)
public class Battle extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private BattleMember forward;

    @OneToOne
    @JoinColumn(nullable = false)
    private BattleMember defender;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private BattleStatus status;

    @Column(nullable = false)
    private ZonedDateTime creating;
}
