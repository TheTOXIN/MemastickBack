package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDateTime;

import static com.memastick.backmem.main.constant.GlobalConstant.MAX_CHROMOSOME;

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
    @Max(MAX_CHROMOSOME)
    private int chromosome = 0;

    @Column
    private LocalDateTime likeTime;

}
