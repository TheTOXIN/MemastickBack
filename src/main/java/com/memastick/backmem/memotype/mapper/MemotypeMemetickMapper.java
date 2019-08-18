package com.memastick.backmem.memotype.mapper;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MemotypeMemetickMapper {

    private final MemotypeMapper memotypeMapper;

    public MemotypeMemetickAPI toAPI(
        Map<MemotypeSet, List<Memotype>> memotypeBySet,
        Map<UUID, Integer> memotypeByCount
    ) {
        List<MemotypeSetAPI> result = memotypeMapper.toListAPI(memotypeBySet);

        result
            .stream()
            .map(MemotypeSetAPI::getMemotypes)
            .flatMap(List::stream)
            .forEach(m -> m.setCount(memotypeByCount.get(m.getId())));

        return new MemotypeMemetickAPI(result);
    }
}
