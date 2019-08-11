package com.memastick.backmem.memecoin.repository;

import com.memastick.backmem.errors.exception.BlockCoinException;
import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.memecoin.entity.BlockCoin;
import com.memastick.backmem.memecoin.entity.Pickaxe;
import com.memastick.backmem.memetick.entity.Memetick;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PickaxeRepository extends CrudRepository<Pickaxe, UUID> {

    Optional<Pickaxe> findByToken(UUID token);

    Optional<Pickaxe> findByMemetickId(UUID memetickId);

    default Pickaxe tryFindByToken(UUID token) {
        Optional<Pickaxe> optional = this.findByToken(token);
        if (optional.isEmpty()) throw new EntityNotFoundException(Pickaxe.class, "by token");
        return optional.get();
    }

    default Pickaxe findByMemetick(Memetick memetick) {
        return this
            .findByMemetickId(memetick.getId())
            .orElse(new Pickaxe(memetick.getId()));
    }
}
