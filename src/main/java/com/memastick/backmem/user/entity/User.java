package com.memastick.backmem.user.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.constant.RoleType;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "joinedMemetick", includeAllAttributes = true)
public class User extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @OneToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @Column(nullable = false)
    private boolean ban = false;
}

