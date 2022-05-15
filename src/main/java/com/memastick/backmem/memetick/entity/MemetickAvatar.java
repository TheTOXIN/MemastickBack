package com.memastick.backmem.memetick.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "memetick_avatars")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemetickAvatar extends AbstractEntity {

    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID memetickId;

    @Lob
    @Column(nullable = false)
    private byte[] avatar;
}
