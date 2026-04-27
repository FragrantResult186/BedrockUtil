package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.math.ChunkPos;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.util.state.Dimension;

import java.util.List;
import java.util.Map;

public class Mineshaft extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_0_0, new Config(0, 1, 1, true))
    );

    public Mineshaft(MCVersion version) {
        super(version, Dimension.OVERWORLD);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_0_0;
    }

    @Override
    public String getName() {
        return "mineshaft";
    }

    @Override
    protected Config getConfig() {
        return getConfig(CONFIGS);
    }

    @Override
    public ChunkRandom getRandom() {
        return ChunkRandom.bedrock();
    }

    @Override
    public ChunkPos getChunkInRegion(long seed, int chunkX, int chunkZ, ChunkRandom random) {
        random.setMineshaftSeed(seed, chunkX, chunkZ, getVersion());
        random.advance(1);
        if (random.nextFloat() >= 0.004F) return null;
        if (random.nextInt(80) >= Math.max(Math.abs(chunkX), Math.abs(chunkZ))) return null;
        return new ChunkPos(chunkX, chunkZ);
    }
}