package com.memastick.backmem.memetick.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "memetick_avatars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemetickAvatar extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private Memetick memetick;

    @Lob
    @Column(nullable = false)
    private byte[] avatar;

}
