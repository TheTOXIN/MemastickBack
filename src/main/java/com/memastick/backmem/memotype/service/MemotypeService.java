package com.memastick.backmem.memotype.service;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import com.memastick.backmem.memotype.mapper.MemotypeMapper;
import com.memastick.backmem.memotype.mapper.MemotypeSetMapper;
import com.memastick.backmem.memotype.repository.MemotypeRepository;
import com.memastick.backmem.memotype.repository.MemotypeSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class MemotypeService {

    private final MemotypeRepository memotypeRepository;
    private final MemotypeMapper memotypeMapper;
    private final MemotypeSetRepository setRepository;
    private final MemotypeSetService memotypeService;

    @Transactional
    public void create(MemotypeAPI request) {
        MemotypeSet set = setRepository.tryFindByName(request.getSet());
        set.setSize(set.getSize() + 1);
        setRepository.save(set);

        Memotype memotype = memotypeMapper.toEntity(request, set);
        memotypeRepository.save(memotype);
    }

    public List<MemotypeSetAPI> all() {
        Map<UUID, MemotypeSet> allSetById = memotypeService.allSetById();
        Iterable<Memotype> memotypesAll = memotypeRepository.findAll();

        Map<MemotypeSet, List<Memotype>> memotypeBySet = StreamSupport
            .stream(memotypesAll.spliterator(), false)
            .collect(Collectors.groupingBy(m -> allSetById.get(m.getSetId())));

        return memotypeMapper.toListAPI(memotypeBySet);
    }
}
