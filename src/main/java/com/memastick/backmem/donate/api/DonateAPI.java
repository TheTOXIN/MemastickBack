package com.memastick.backmem.donate.api;

import com.memastick.backmem.donate.entity.DonateMessage;
import com.memastick.backmem.donate.entity.DonateRating;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonateAPI {

    private DonateMessage message;
    private Map<MemotypeRarity, List<DonateRating>> rating;
}
