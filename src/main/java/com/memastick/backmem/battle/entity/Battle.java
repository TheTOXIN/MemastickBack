package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.constant.BattleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "battle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraph(name = "joinedBattle", includeAllAttributes = true)
public class Battle extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private BattleMember forward;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private BattleMember defender;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private BattleStatus status;

    @Column(nullable = false)
    private ZonedDateTime updating;

    @Column
    @Min(BattleConst.MIN_PVP)
    @Max(BattleConst.MAX_PVP)
    private Integer pvp;

    public Battle(BattleMember forward, BattleMember defender) {
        this.forward = forward;
        this.defender = defender;
    }

    public BattleMember getMember(UUID memberId) {
        if (forward.getId().equals(memberId)) return forward;
        else if (defender.getId().equals(memberId)) return defender;
        else return null;
    }

    public BattleMember getLeader() {
        if (forward.getVotes() > defender.getVotes()) return forward;
        else if (defender.getVotes() > forward.getVotes()) return defender;
        else return null;
    }

    public BattleMember getLooser() {
        if (forward.getVotes() < defender.getVotes()) return forward;
        else if (defender.getVotes() < forward.getVotes()) return defender;
        else return null;
    }
}
