package com.memastick.backmem.evolution.iface;

import com.memastick.backmem.evolution.entity.EvolveMeme;

import java.util.List;

@FunctionalInterface
public interface Evolution {

    void evolution(List<EvolveMeme> evolveMemes);

}
