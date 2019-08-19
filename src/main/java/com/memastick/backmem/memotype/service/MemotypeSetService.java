package com.memastick.backmem.memotype.service;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.main.util.StreamUtil;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import com.memastick.backmem.memotype.mapper.MemotypeMapper;
import com.memastick.backmem.memotype.mapper.MemotypeMemetickMapper;
import com.memastick.backmem.memotype.mapper.MemotypeSetMapper;
import com.memastick.backmem.memotype.repository.MemotypeMemetickRepository;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.memotype.repository.MemotypeSetRepository;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class MemotypeSetService {

    private final MemotypeSetRepository setRepository;
    private final MemotypeRepository memotypeRepository;
    private final MemotypeSetMapper memotypeSetMapper;
    private final MemotypeMemetickRepository memotypeMemetickRepository;
    private final MemotypeMapper memotypeMapper;
    private final MemotypeMemetickMapper memotypeMemetickMapper;
    private final OauthData oauthData;

    public void create(MemotypeSetAPI request) {
        setRepository.save(
            memotypeSetMapper.toEntity(
                request
            )
        );
    }

    public MemotypeMemetickAPI collection() {
        Memetick memetick = oauthData.getCurrentMemetick();

        List<Memotype> memotypesMy = memotypeMemetickRepository.findMemotypesIdByMemetickId(memetick.getId());
        List<Memotype> memotypesAll = memotypeRepository.findAllMemotypes();

        Map<UUID, MemotypeSet> setById = this.allSetById();
        Map<UUID, Integer> memotypeByCount = StreamUtil.countById(memotypesMy);

        Map<String, List<MemotypeAPI>> memotypesBySet = memotypesAll
            .stream()
            .sorted(Comparator.comparing(Memotype::getNumber))
            .map(m -> memotypeMapper.toAPI(m, setById.get(m.getSetId()).getName()))
            .peek(m -> m.setCount(memotypeByCount.getOrDefault(m.getId(), 0)))
            .collect(Collectors.groupingBy(MemotypeAPI::getSet));

        return memotypeMemetickMapper.toAPI(setById, memotypesBySet);
    }

    public Map<UUID, MemotypeSet> allSetById() {
        return StreamSupport
            .stream(setRepository.findAll().spliterator(), false)
            .collect(Collectors.toMap(AbstractEntity::getId, Function.identity()));
    }
}
