package com.memastick.backmem.main.facade;

import com.memastick.backmem.base.AbstractFacade;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class StatisticsFacade extends AbstractFacade {

    @SuppressWarnings("unchecked")
    public List<BigDecimal> global() {
        return em.createNativeQuery(
            "SELECT COUNT(*) FROM memes WHERE type = 'INDV' UNION ALL\n" +
                "SELECT SUM(chromosomes) FROM memes UNION ALL\n" +
                "SELECT SUM(dna) FROM memeticks"
        ).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BigDecimal> memetick(UUID memetickId) {
        return em.createNativeQuery(
                "SELECT COUNT(*) FROM memes WHERE type = 'INDV' AND memetick_id = ?1 UNION ALL\n" +
                "SELECT SUM(chromosomes) FROM memes WHERE memetick_id = ?1 UNION ALL\n" +
                "SELECT SUM(dna) FROM memeticks WHERE id = ?1"
        ).setParameter(1, memetickId).getResultList();
    }
}
