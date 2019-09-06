package com.memastick.backmem.memotype.mapper;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeMemetickAPI;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MemotypeMemetickMapper {

    private final MemotypeSetMapper memotypeSetMapper;

    public MemotypeMemetickAPI toAPI(
        Map<UUID, MemotypeSet> setById,
        Map<String, List<MemotypeAPI>> memotypesBySet
    ) {
        var result = new MemotypeMemetickAPI(
            setById
                .values()
                .stream()
                .map(set -> memotypeSetMapper.toAPI(
                    set,
                    memotypesBySet.getOrDefault(set.getName(), Collections.emptyList()))
                ).collect(Collectors.toList())
        );

        result.getContent().removeIf(set -> set.getMemotypes().isEmpty());

        return result;
    }
}
