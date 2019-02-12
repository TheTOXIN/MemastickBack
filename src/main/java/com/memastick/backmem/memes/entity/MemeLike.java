package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Entity
@Table(
    name = "meme_likes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"meme_id", "memetick_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemeLike extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Meme meme;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column
    private boolean isLike = false;

    @Column
    @Max(value = 30)
    private int chromosome = 0;

}
