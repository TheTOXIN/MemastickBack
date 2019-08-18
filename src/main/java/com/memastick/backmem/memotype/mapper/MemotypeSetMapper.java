package com.memastick.backmem.memotype.mapper;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@AllArgsConstructor
public class MemotypeSetMapper {

    public MemotypeSet toEntity(MemotypeSetAPI api) {
        return new MemotypeSet(
            api.getName(),
            api.getDescription()
        );
    }

    public MemotypeSetAPI toAPI(MemotypeSet entity, List<MemotypeAPI> memotypesApi) {
        return new MemotypeSetAPI(
            entity.getName(),
            entity.getDescription(),
            entity.getSize(),
            memotypesApi,
            memotypesApi
                .stream()
                .filter(MemotypeAPI::isHave)
                .count()
        );
    }
}
