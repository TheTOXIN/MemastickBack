package com.memastick.backmem.person.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.ZonedDateTime;


@Entity
@Table(name = "memeticks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Memetick extends AbstractEntity {

    @Column(nullable = false)
    private String nick;

    @Column(nullable = false)
    private long dna = 0L;

    @Lob
    @Column
    private byte[] avatar;

    @Column(nullable = false)
    private ZonedDateTime nickChanged;

    @Column(nullable = false)
    private ZonedDateTime memeCreated;

}
