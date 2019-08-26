package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.battle.constant.BattleConst;
import com.memastick.backmem.battle.constant.BattleRole;
import com.memastick.backmem.battle.constant.BattleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.ZonedDateTime;

@Entity
@Table(name = "battle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraph(name = "joinedMembers", includeAllAttributes = true)
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
    @Max(BattleConst.MAX_PVP)
    private Integer pvp;

    public Battle(BattleMember forward, BattleMember defender) {
        this.forward = forward;
        this.defender = defender;
    }

    public BattleMember getMember(BattleRole role) {
        switch (role) {
            case FORWARD: return forward;
            case DEFENDER: return defender;
            default: return null;
        }
    }

    public BattleMember getLeader() {
        if (forward.getVoices() > defender.getVoices()) return forward;
        else if (defender.getVoices() > forward.getVoices()) return defender;
        else return null;
    }

    public BattleMember getLooser() {
        if (forward.getVoices() < defender.getVoices()) return forward;
        else if (defender.getVoices() < forward.getVoices()) return defender;
        else return null;
    }
}
