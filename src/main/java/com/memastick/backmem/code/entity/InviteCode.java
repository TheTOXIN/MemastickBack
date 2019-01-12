package com.memastick.backmem.code.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @NotEmpty
    @Size(min = 4, max = 24)
    @Column(nullable = false)
    private String nick;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private boolean isSend = false;

    @Column(nullable = false)
    private boolean isTake = false;

}
