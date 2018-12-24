package com.memastick.backmem.invite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "invites")
@AllArgsConstructor
@NoArgsConstructor
public class Invite {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

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

}
