package com.memastick.backmem.memotype.service;

import com.memastick.backmem.base.AbstractEntity;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import com.memastick.backmem.memotype.mapper.MemotypeMapper;
import com.memastick.backmem.memotype.mapper.MemotypeSetMapper;
import com.memastick.backmem.memotype.repository.MemotypeMemetickRepository;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.memotype.repository.MemotypeSetRepository;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class MemotypeSetService {

    private final MemotypeSetRepository setRepository;
    private final MemotypeRepository memotypeRepository;
    private final MemotypeSetMapper setMapper;
    private final MemotypeMemetickRepository memotypeMemetickRepository;
    private final MemotypeMapper memotypeMapper;
    private final OauthData oauthData;

    public void create(MemotypeSetAPI request) {
        setRepository.save(
            setMapper.toEntity(
                request
            )
        );
    }

    //TODO REFACTOR
    public List<MemotypeSetAPI> collection() {
        Memetick memetick = oauthData.getCurrentMemetick();

        Map<UUID, MemotypeSet> setById = this.allSetById();

        List<Memotype> memotypesMy = memotypeMemetickRepository.findMemotypesIdByMemetickId(memetick.getId());

        Map<UUID, Memotype> memotypesMyById = memotypesMy
            .stream()
            .distinct()
            .collect(Collectors.toMap(Memotype::getId, Function.identity()));

        List<Memotype> memotypesAll = StreamSupport
            .stream(memotypeRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());

        Map<UUID, Integer> memotypeByCount = memotypesMy
            .stream()
            .map(Memotype::getId)
            .collect(Collectors.toMap(id -> id, m -> 1, Math::addExact));

        Map<String, List<MemotypeAPI>> memotypesBySet = memotypesAll
            .stream()
            .map(m -> memotypeMapper.toAPI(m, setById.get(m.getSetId()).getName()))
            .peek(m -> {
                if (memotypesMyById.containsKey(m.getId())) {
                    m.setHave(true);
                    m.setCount(memotypeByCount.get(m.getId()));
                } else {
                    m.setHave(false); //TODO only count
                    m.setCount(0);
                }
            })
            .collect(Collectors.groupingBy(MemotypeAPI::getSet));

        return setById
            .values()
            .stream()
            .filter(set -> memotypesBySet.containsKey(set.getName()))
            .map(set -> setMapper.toAPI(set, memotypesBySet.get(set.getName())))
            .collect(Collectors.toList());
    }

    public Map<UUID, MemotypeSet> allSetById() {
        return StreamSupport
            .stream(setRepository.findAll().spliterator(), false)
            .collect(Collectors.toMap(AbstractEntity::getId, Function.identity()));
    }
}
