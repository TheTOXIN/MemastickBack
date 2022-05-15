package com.memastick.backmem.memes.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDateTime;

import static com.memastick.backmem.main.constant.ValidConstant.MAX_CHROMOSOME;


@Entity
@Table(
    name = "meme_likes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"meme_id", "memetick_id"})
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemeLike extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Meme meme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Memetick memetick;

    @Column
    private boolean isLike = false;

    @Column
    @Max(MAX_CHROMOSOME)
    private int chromosome = 0;

    @Column
    private LocalDateTime likeTime;
}
