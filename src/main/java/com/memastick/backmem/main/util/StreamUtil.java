package com.memastick.backmem.main.util;

import com.memastick.backmem.base.AbstractEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class StreamUtil {

    public static <E extends AbstractEntity> Map<UUID, Integer>countById(List<E> entities) {
        return entities
            .stream()
            .map(AbstractEntity::getId)
            .collect(Collectors.toMap(id -> id, m -> 1, Math::addExact));
    }
}
