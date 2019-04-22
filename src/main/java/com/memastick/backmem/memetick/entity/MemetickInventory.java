package com.memastick.backmem.memetick.entity;

import com.memastick.backmem.base.entity.AbstractEntity;
import com.memastick.backmem.main.constant.TimeConstant;
import com.memastick.backmem.tokens.entity.TokenWallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "memetick_inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemetickInventory extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private Memetick memetick;

    @OneToOne
    @JoinColumn(nullable = false)
    private TokenWallet tokenWallet;

    @Column(nullable = false)
    private boolean allowance = false;

    @Column(nullable = false)
    private LocalDateTime cellCreating = TimeConstant.START_LOCAL_TIME;

}
