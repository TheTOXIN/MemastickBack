package com.memastick.backmem.memetick.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "memetick_avatars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemetickAvatar extends AbstractEntity {

    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID memetickId;

    @Lob
    @Column(nullable = false)
    private byte[] avatar;

}
