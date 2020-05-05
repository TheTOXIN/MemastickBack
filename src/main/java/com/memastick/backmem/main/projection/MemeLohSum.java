package com.memastick.backmem.main.projection;

public interface MemeLohSum {

    Integer getLol();
    Integer getOmg();
    Integer getHmm();

    default Integer computeAvg() {
        int lol = this.getLol() != null ? this.getLol() : 0;
        int omg = this.getOmg() != null ? this.getOmg() : 0;
        int hmm = this.getHmm() != null ? this.getHmm() : 0;

        return (lol + omg + hmm) / 3;
    }
}
