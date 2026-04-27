package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.math.ChunkPos;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.util.state.Dimension;

import java.util.List;
import java.util.Map;

public class GridStronghold extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_0_0, new Config(97858791, 200, 100, true))
    );

    public GridStronghold(MCVersion version) {
        super(version, Dimension.OVERWORLD);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_0_0;
    }

    @Override
    public String getName() {
        return "stronghold";
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
        int a = regionX * getSpacing() + 100;
        int b = regionZ * getSpacing() + 100;
        random.setStrongholdSeed(seed, a, b);
        int x = random.nextInt(getSpacing()) - 50 + a;
        int z = random.nextInt(getSpacing()) - 50 + b;
        if (random.nextFloat() >= 0.25F) return null;
        return new ChunkPos(x + regionX * getSpacing(), z + regionZ * getSpacing());
    }
}