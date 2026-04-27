package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.math.ChunkPos;
import com.fragrant.bedrockutil.util.math.DistanceMetric;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.util.state.Dimension;

import java.util.List;
import java.util.Map;

public class EndCity extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_0_0, new Config(10387313, 20, 9, false))
    );

    public EndCity(MCVersion MCVersion) {
        super(MCVersion, Dimension.END);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_0_0;
    }

    @Override
    public String getName() {
        return "end_city";
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
    public ChunkPos getChunkInRegion(long seed, int regionX, int regionZ, ChunkRandom random) {
        ChunkPos chunkPos = super.getChunkInRegion(seed, regionX, regionZ, random);
        if (chunkPos.distanceTo(ChunkPos.ZERO, DistanceMetric.MANHATTAN) < 64L) return null;
        return chunkPos;
    }
}