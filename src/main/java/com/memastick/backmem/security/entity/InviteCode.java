package com.memastick.backmem.security.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "invite_codes")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InviteCode extends AbstractEntity {

    @Email
    @NotEmpty
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
