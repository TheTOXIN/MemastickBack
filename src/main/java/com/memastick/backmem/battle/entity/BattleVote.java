package com.memastick.backmem.battle.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "battle_vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BattleVote extends AbstractEntity {

    @NaturalId
    @Column(nullable = false)
    private UUID battleId;

    @NaturalId
    @Column(nullable = false)
    private UUID memetickId;
}
