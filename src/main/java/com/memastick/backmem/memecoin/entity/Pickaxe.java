package com.memastick.backmem.memecoin.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.constant.TimeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pickaxes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pickaxe extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private UUID token = UUID.randomUUID();

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = false)
    private LocalDateTime creating = TimeConstant.START_LOCAL_TIME;

    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID memetickId;

    public Pickaxe(UUID memetickId) {
        this.memetickId = memetickId;
    }
}
