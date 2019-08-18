package com.memastick.backmem.memotype.service;

import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeMemetick;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import com.memastick.backmem.memotype.mapper.MemotypeMapper;
import com.memastick.backmem.memotype.mapper.MemotypeMemetickMapper;
import com.memastick.backmem.memotype.repository.MemotypeMemetickRepository;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.memotype.repository.MemotypeSetRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.shop.constant.PriceConst;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemotypeMemetickService {

    private final MemotypeMemetickRepository memotypeMemetickRepository;
    private final MemotypeRepository memotypeRepository;
    private final MemotypeSetService setService;
    private final MemotypeMemetickMapper memotypeMemetickMapper;
    private final MemeCoinService memeCoinService;
    private final OauthData oauthData;

    @Transactional
    public void buy(UUID memotypeId) {
        Memetick memetick = oauthData.getCurrentMemetick();
        Memotype memotype = memotypeRepository.tryFindById(memotypeId);

        memeCoinService.transaction(
            memetick,
            memotype.getRarity().getLvl() * PriceConst.MEMOTYPE.getValue()
        );

        memotypeMemetickRepository.save(new MemotypeMemetick(
            memetick,
            memotype
        ));
    }

    public MemotypeMemetickAPI read(UUID memetickId) {
        List<Memotype> memotypes = memotypeMemetickRepository.findMemotypesIdByMemetickId(memetickId);
        Map<UUID, MemotypeSet> setById = setService.allSetById();

        Map<UUID, Integer> memotypeByCount = memotypes
            .stream()
            .map(Memotype::getId)
            .collect(Collectors.toMap(id -> id, m -> 1, Math::addExact));

        Map<MemotypeSet, List<Memotype>> memotypeBySet = memotypes
            .stream()
            .distinct()
            .collect(Collectors.groupingBy(m -> setById.get(m.getSetId())));

        return memotypeMemetickMapper.toAPI(
            memotypeBySet,
            memotypeByCount
        );
    }
}
