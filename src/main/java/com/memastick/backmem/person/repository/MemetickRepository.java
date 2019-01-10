package com.memastick.backmem.person.repository;

import com.memastick.backmem.person.entity.Memetick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemetickRepository extends CrudRepository<Memetick, UUID> {

}
