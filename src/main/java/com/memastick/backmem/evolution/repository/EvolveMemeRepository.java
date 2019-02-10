package com.memastick.backmem.evolution.repository;

import com.memastick.backmem.evolution.entity.EvolveMeme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EvolveMemeRepository extends CrudRepository<EvolveMeme, UUID> {



}
