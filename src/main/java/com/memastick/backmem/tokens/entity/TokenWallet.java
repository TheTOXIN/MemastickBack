package com.memastick.backmem.tokens.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.constant.GlobalConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import java.util.UUID;

@Entity
@Table(name = "token_wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TokenWallet extends AbstractEntity {

    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID memetickId;

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
