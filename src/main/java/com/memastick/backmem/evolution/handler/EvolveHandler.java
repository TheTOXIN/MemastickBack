package com.memastick.backmem.evolution.handler;

import com.memastick.backmem.evolution.annotation.Evolve;
import com.memastick.backmem.evolution.constant.EvolveStep;
import com.memastick.backmem.evolution.iface.Evolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EvolveHandler {

    private final Map<EvolveStep, Evolution> handler = new HashMap<>();

    @Autowired
    public EvolveHandler(ApplicationContext context) {
        context
            .getBeansWithAnnotation(Evolve.class)
            .values()
            .forEach((e) -> handler.put(
                e.getClass().getAnnotation(Evolve.class).step(),
                (Evolution) e
            ));
    }

    public Evolution pullEvolve(EvolveStep step) {
        return this.handler.get(step);
    }
}
