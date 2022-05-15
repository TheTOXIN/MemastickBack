package com.memastick.backmem.memotype.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "memotype_set")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemotypeSet extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int size = 0;

    public MemotypeSet(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
