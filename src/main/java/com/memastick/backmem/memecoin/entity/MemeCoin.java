package com.memastick.backmem.memecoin.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "meme_coins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemeCoin extends AbstractEntity {

    @Column(nullable = false)
    private Long value;

    @NaturalId
    @Column(nullable = false)
    private UUID memetickId;

    @Column(nullable = false)
    private ZonedDateTime creating;
}
