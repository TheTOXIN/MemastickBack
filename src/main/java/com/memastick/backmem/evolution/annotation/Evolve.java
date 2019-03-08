package com.memastick.backmem.evolution.annotation;

import com.memastick.backmem.evolution.constant.EvolveStep;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Evolve {

    EvolveStep step();

}
