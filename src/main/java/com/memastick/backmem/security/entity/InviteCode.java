package com.memastick.backmem.security.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "invite_codes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InviteCode extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime dateSend;

    @Column(nullable = false)
    private LocalDateTime dateCreate;

    @Column(nullable = false)
    private boolean isSend = false;

    @Column(nullable = false)
    private boolean isTake = false;
}
