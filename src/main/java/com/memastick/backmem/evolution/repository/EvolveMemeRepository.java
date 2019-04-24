package com.memastick.backmem.evolution.repository;

import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.entity.EvolveMeme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EvolveMemeRepository extends JpaRepository<EvolveMeme, UUID> {

    List<EvolveMeme> findByStep(EvolveStep step);

    @Query("SELECT em FROM EvolveMeme em WHERE em.step IS NOT NULL")
    List<EvolveMeme> findAllEvolve();

}
