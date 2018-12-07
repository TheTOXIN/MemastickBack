package com.memastick.backmem.invite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;


@Data
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invite {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String code;

    private ZonedDateTime create;

}
