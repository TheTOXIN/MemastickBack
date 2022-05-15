package com.memastick.backmem.security.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PasswordReset extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String login;

    @Column
    private String code;

    @Column(nullable = false)
    private LocalDateTime time;
}
