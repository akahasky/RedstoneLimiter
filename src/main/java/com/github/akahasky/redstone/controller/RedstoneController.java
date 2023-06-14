package com.github.akahasky.redstone.controller;

import com.github.akahasky.redstone.model.ChunkTicks;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RedstoneController {

    @Getter private static final short maxTicks = 1880;
    private final static Cache<Long, ChunkTicks> REDSTONE_TICKS = CacheBuilder.newBuilder().initialCapacity(80).expireAfterWrite(1, TimeUnit.SECONDS).build();

    public static void init() {

        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(REDSTONE_TICKS::cleanUp, 5500, 5500, TimeUnit.MILLISECONDS);

    }

    public static ChunkTicks get(int chunkX, int chunkZ) {

        long chunkKey = ((long) chunkZ << 32) | (chunkX & 0xFFFFFFFFL);
        ChunkTicks chunkTicks = REDSTONE_TICKS.getIfPresent(chunkKey);

        if (chunkTicks == null) {

            chunkTicks = new ChunkTicks(0, false);
            REDSTONE_TICKS.put(chunkKey, chunkTicks);

        }

        return chunkTicks;

    }

}
