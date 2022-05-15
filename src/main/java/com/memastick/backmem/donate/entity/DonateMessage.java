package com.memastick.backmem.donate.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "donate_messages")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DonateMessage extends DonateAbstract {

    @Column(nullable = false, unique = true)
    private Long number;

    @Column(nullable = false)
    private String message;
}
