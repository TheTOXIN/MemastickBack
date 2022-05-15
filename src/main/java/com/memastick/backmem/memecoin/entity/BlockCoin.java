package com.memastick.backmem.memecoin.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "block_coins")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlockCoin extends AbstractEntity {

    @Column(nullable = false)
    private long cache = 0;

    @Column(nullable = false)
    private int nonce = 0;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private Timestamp timestamp;

    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID memetickId;

    public BlockCoin(UUID memetickId) {
        this.memetickId = memetickId;
    }
}
