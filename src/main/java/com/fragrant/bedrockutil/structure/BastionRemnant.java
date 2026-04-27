package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.state.Dimension;
import com.fragrant.bedrockutil.util.math.ChunkPos;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;
import java.util.Map;

public class BastionRemnant extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_16_0, new Config(30084232, 30, 26, true))
    );

    public BastionRemnant(MCVersion version) {
        super(version, Dimension.NETHER);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_16_0;
    }

    @Override
    public String getName() {
        return "bastion_remnant";
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
        return random.nextInt(6) >= 2 ? chunkPos : null;
    }
}