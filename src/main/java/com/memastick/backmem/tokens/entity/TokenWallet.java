package com.memastick.backmem.tokens.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "token_wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TokenWallet extends AbstractEntity {

    @Max(10)
    @Column(nullable = false)
    private Integer creating = 0;

    @Max(10)
    @Column(nullable = false)
    private Integer fitness = 0;

    @Max(10)
    @Column(nullable = false)
    private Integer mutation = 0;

    @Max(10)
    @Column(nullable = false)
    private Integer crossover = 0;

    @Max(10)
    @Column(nullable = false)
    private Integer selection = 0;

}
