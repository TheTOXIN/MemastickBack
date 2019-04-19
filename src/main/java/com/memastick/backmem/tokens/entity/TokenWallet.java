package com.memastick.backmem.tokens.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.main.constant.GlobalConstant;
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

    @Max(GlobalConstant.MAX_TOKEN)
    @Column(nullable = false)
    private Integer tube = 1;

    @Max(GlobalConstant.MAX_TOKEN)
    @Column(nullable = false)
    private Integer scope = 1;

    @Max(GlobalConstant.MAX_TOKEN)
    @Column(nullable = false)
    private Integer mutagen = 1;

    @Max(GlobalConstant.MAX_TOKEN)
    @Column(nullable = false)
    private Integer crossover = 1;

    @Max(GlobalConstant.MAX_TOKEN)
    @Column(nullable = false)
    private Integer antibiotic = 1;

}
