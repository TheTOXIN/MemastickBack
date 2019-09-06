package com.memastick.backmem.security.entity;

import com.memastick.backmem.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "password_reset")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PasswordReset extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String login;

    @Column
    private String code;

    @Column(nullable = false)
    private LocalDateTime time;

}
