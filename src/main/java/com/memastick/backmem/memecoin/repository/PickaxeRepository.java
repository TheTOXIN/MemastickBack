package com.memastick.backmem.memecoin.repository;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memecoin.entity.Pickaxe;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PickaxeRepository extends CrudRepository<Pickaxe, UUID> {

    Optional<Pickaxe> findByToken(UUID token);
    Optional<Pickaxe> findByMemetickId(UUID memetickId);

    default Pickaxe tryFindByToken(UUID token) {
        return this
            .findByToken(token)
            .orElseThrow(() -> new EntityNotFoundException(Pickaxe.class, "by token"));
    }

    default Pickaxe generateFindByMemetick(Memetick memetick) {
        return this
            .findByMemetickId(memetick.getId())
            .orElse(new Pickaxe(memetick.getId()));
    }
}
