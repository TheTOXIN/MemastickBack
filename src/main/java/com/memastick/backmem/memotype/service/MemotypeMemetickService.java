package com.memastick.backmem.memotype.service;

import com.memastick.backmem.main.util.StreamUtil;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeMemetick;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import com.memastick.backmem.memotype.mapper.MemotypeMapper;
import com.memastick.backmem.memotype.mapper.MemotypeMemetickMapper;
import com.memastick.backmem.memotype.repository.MemotypeMemetickRepository;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.shop.constant.PriceConst;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemotypeMemetickService {

    private final MemotypeMemetickRepository memotypeMemetickRepository;
    private final MemotypeRepository memotypeRepository;
    private final MemeCoinService memeCoinService;
    private final MemotypeSetService memotypeSetService;
    private final MemotypeMapper memotypeMapper;
    private final MemotypeMemetickMapper memotypeMemetickMapper;
    private final OauthData oauthData;
    private final MemetickRepository memetickRepository;

    @Transactional
    public void buy(UUID memotypeId) {
        Memetick memetick = oauthData.getCurrentMemetick();
        Memotype memotype = memotypeRepository.tryFindById(memotypeId);

        memeCoinService.transaction(
            memetick,
            memotype.getRarity().getLvl() * PriceConst.MEMOTYPE.getPrice()
        );

        memotypeMemetickRepository.save(new MemotypeMemetick(
            memetick,
            memotype
        ));
    }

    public MemotypeMemetickAPI read(UUID memetickId) {
        Memetick memetick = memetickRepository.tryFindById(memetickId);

        List<Memotype> memotypesMy = memotypeMemetickRepository.findMemotypesIdByMemetickId(memetick.getId());
        Map<UUID, MemotypeSet> setById = memotypeSetService.allSetById();

        Map<UUID, Integer> memotypeByCount = StreamUtil.countById(memotypesMy);

        Map<String, List<MemotypeAPI>> memotypesBySet = memotypesMy
            .stream()
            .distinct()
            .sorted(Comparator.comparing(Memotype::getNumber))
            .map(m -> memotypeMapper.toAPI(m, setById.get(m.getSetId()).getName()))
            .peek(m -> m.setCount(memotypeByCount.getOrDefault(m.getId(), 0)))
            .collect(Collectors.groupingBy(MemotypeAPI::getSet));

        return memotypeMemetickMapper.toAPI(setById, memotypesBySet);
    }
}
