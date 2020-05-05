package com.memastick.backmem.tokens.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.tokens.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.management.openmbean.TabularType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "token_accepts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TokenAccept extends AbstractEntity {

    @NaturalId
    @Column(nullable = false)
    private UUID memetickId;

    @NaturalId
    @Column(nullable = false)
    private UUID memeId;

    @Column(nullable = false)
    private TokenType token;
}
