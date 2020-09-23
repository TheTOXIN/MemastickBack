package com.memastick.backmem.donate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "donate_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonateMessage extends DonateAbstract {

    @Column(nullable = false, unique = true)
    private Long number;

    @Column(nullable = false)
    private String message;
}
