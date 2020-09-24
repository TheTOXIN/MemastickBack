package com.memastick.backmem.donate.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class DonateAbstract extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    private int amount;
}
