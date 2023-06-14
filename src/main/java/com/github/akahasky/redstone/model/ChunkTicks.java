package com.github.akahasky.redstone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Setter @Getter
public class ChunkTicks {

    private int ticks;
    private boolean warned;

}
