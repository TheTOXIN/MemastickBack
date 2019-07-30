package com.memastick.backmem.memecoin.api;

import com.memastick.backmem.memecoin.entity.MemeCoin;
import com.memastick.backmem.memetick.entity.Memetick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeCoinAPI {

    private long value;
    private ZonedDateTime creating;

    public MemeCoinAPI(MemeCoin memeCoin) {
        this.value = memeCoin.getValue();
        this.creating = memeCoin.getCreating();
    }
}
