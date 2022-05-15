package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.battle.constant.BattleRole;
import com.memastick.backmem.memes.entity.Meme;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "battle_member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BattleMember extends AbstractEntity {

    @Column(nullable = false)
    private UUID memetickId;

    @Column(nullable = false)
    private UUID memeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BattleRole role;

    @Column(nullable = false)
    private int votes = 0;

    public BattleMember(Meme meme, BattleRole role) {
        this.memeId = meme.getId();
        this.memetickId = meme.getMemetick().getId();
        this.role = role;
    }
}
