package com.memastick.backmem.donaters.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "donater_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonaterMessage extends DonaterAbstract {

    @Column(nullable = false, unique = true)
    private Long number;

    @Column(nullable = false)
    private String message;
}
