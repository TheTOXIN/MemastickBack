package com.memastick.backmem.tokens.entity;

import com.memastick.backmem.base.AbstractEntity;
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

import static com.memastick.backmem.main.constant.ValidConstant.MAX_TOKEN;

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

    @Max(MAX_TOKEN)
    @Column(nullable = false)
    private Integer tube = 1;

    @Max(MAX_TOKEN)
    @Column(nullable = false)
    private Integer scope = 1;

    @Max(MAX_TOKEN)
    @Column(nullable = false)
    private Integer mutagen = 1;

    @Max(MAX_TOKEN)
    @Column(nullable = false)
    private Integer crossover = 1;

    @Max(MAX_TOKEN)
    @Column(nullable = false)
    private Integer antibiotic = 1;
}
