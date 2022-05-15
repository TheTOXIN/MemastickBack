package com.memastick.backmem.memetick.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.constant.GlobalConstant;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "memeticks")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
