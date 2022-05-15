package com.memastick.backmem.tokens.entity;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.tokens.constant.TokenType;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "token_accepts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenAccept extends AbstractEntity {

    @NaturalId
    @Column(nullable = false)
    private UUID memetickId;

    @NaturalId
    @Column(nullable = false)
    private UUID memeId;

    @NaturalId
    @Column(nullable = false)
    private TokenType token;
}
