package com.memastick.backmem.memotype.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "memotype_memetick")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemotypeMemetick extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Memetick memetick;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Memotype memotype;
}
