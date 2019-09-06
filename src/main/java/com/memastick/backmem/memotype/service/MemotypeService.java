package com.memastick.backmem.memotype.service;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import com.memastick.backmem.memotype.mapper.MemotypeMapper;
import com.memastick.backmem.memotype.mapper.MemotypeMemetickMapper;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.memotype.repository.MemotypeSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemotypeService {

    private final MemotypeRepository memotypeRepository;
    private final MemotypeMapper memotypeMapper;
    private final MemotypeSetRepository memotypeSetRepository;
    private final MemotypeSetService memotypeSetService;
    private final MemotypeMemetickMapper memotypeMemetickMapper;

    @Transactional
    public void create(MemotypeAPI request) {
        MemotypeSet set = memotypeSetRepository.tryFindByName(request.getSet());
        set.setSize(set.getSize() + 1);
        memotypeSetRepository.save(set);

        Memotype memotype = memotypeMapper.toEntity(request, set);
        memotypeRepository.save(memotype);
    }

    public MemotypeMemetickAPI all() {
        List<Memotype> memotypesAll = memotypeRepository.findAllMemotypes();
        Map<UUID, MemotypeSet> setById = memotypeSetService.allSetById();

        Map<String, List<MemotypeAPI>> memotypesBySet = memotypesAll
            .stream()
            .map(m -> memotypeMapper.toAPI(m, setById.get(m.getSetId()).getName()))
            .collect(Collectors.groupingBy(MemotypeAPI::getSet));

        return memotypeMemetickMapper.toAPI(setById, memotypesBySet);
    }
}
