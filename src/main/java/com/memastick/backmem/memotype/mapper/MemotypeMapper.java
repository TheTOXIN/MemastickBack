package com.memastick.backmem.memotype.mapper;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import com.memastick.backmem.memotype.repository.MemotypeSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MemotypeMapper {

    private final MemotypeSetMapper memotypeSetMapper;

    public Memotype toEntity(MemotypeAPI api, MemotypeSet set) {
        return new Memotype(
            api.getRarity(),
            api.getTitle(),
            api.getText(),
            api.getImage(),
            set.getId(),
            set.getSize()
        );
    }

    public MemotypeAPI toAPI(Memotype entity, String set) {
        return new MemotypeAPI(
            entity.getId(),
            entity.getRarity(),
            entity.getTitle(),
            entity.getText(),
            entity.getImage(),
            set
        );
    }

    public List<MemotypeSetAPI> toListAPI(Map<MemotypeSet, List<Memotype>> memotypeBySet) {
        List<MemotypeSetAPI>  result = new ArrayList<>();

        memotypeBySet.forEach((set, memotypes) -> {
            List<MemotypeAPI> memotypesAPI = memotypes
                .stream()
                .sorted(Comparator.comparing(m -> -1 * m.getRarity().getLvl()))
                .map(memotype -> this.toAPI(memotype, set.getName()))
                .collect(Collectors.toList());

            MemotypeSetAPI setAPI = memotypeSetMapper.toAPI(set, memotypesAPI);

            result.add(setAPI);
        });

        return result;
    }
}
