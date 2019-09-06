package com.memastick.backmem.memotype.mapper;

import com.memastick.backmem.memotype.api.MemotypeAPI;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemotypeMapper {

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

    public MemotypeAPI toAPI(Memotype entity, String setName) {
        return new MemotypeAPI(
            entity.getId(),
            entity.getRarity(),
            entity.getTitle(),
            entity.getText(),
            entity.getImage(),
            setName
        );
    }
}
