package com.memastick.backmem.memetick.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.constant.GlobalConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "memeticks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Memetick extends AbstractEntity {

    @Column(nullable = false)
    private String nick = "";

    @Column(nullable = false)
    private long dna = 0L;

    @Column(nullable = false)
    private int cookies = GlobalConstant.DEFAULT_COOKIES;

    @Column(nullable = false)
    private boolean creed = false;
}
