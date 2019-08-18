package com.memastick.backmem.memotype.repository;

import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.memotype.entity.MemotypeMemetick;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
public interface MemotypeMemetickRepository extends CrudRepository<MemotypeMemetick, UUID> {

    List<MemotypeMemetick> findAllByMemetickId(UUID memetickId);

    @Query("SELECT mm.memotype FROM MemotypeMemetick mm WHERE mm.memetick.id = :memetickId")
    List<Object> findMemotypesByMemetickId(@Param("memetickId") UUID memetickId);

    default List<Memotype> findMemotypesIdByMemetickId(UUID memetickId) {
        return this
            .findMemotypesByMemetickId(memetickId)
            .stream()
            .map(obj -> (Memotype) obj)
            .collect(Collectors.toList());
    }
}
