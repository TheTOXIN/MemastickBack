package com.memastick.backmem.memecoin.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "meme_coins")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemeCoin extends AbstractEntity {

    @Column(nullable = false)
    private Long value;

    @Column(nullable = false)
    private UUID memetickId;

    @Column(nullable = false)
    private ZonedDateTime creating;
}
