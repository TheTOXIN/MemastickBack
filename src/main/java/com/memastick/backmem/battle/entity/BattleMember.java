package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.battle.constant.BattleRole;
import com.memastick.backmem.memes.entity.Meme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "battle_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BattleMember extends AbstractEntity {

    @Column(nullable = false)
    private UUID memetickId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Meme meme;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BattleRole role;

    @Column(nullable = false)
    private int votes = 0;

    public BattleMember(Meme meme, BattleRole role) {
        this.meme = meme;
        this.memetickId = meme.getMemetick().getId();
        this.role = role;
    }
}