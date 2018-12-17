package com.memastick.backmem.invite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invite {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String code;

    @Column
    private LocalDateTime date;

}
