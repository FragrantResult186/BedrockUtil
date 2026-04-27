package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.state.Dimension;
import com.fragrant.bedrockutil.util.math.ChunkPos;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;
import java.util.Map;

public class Village extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_18_0, new Config(10387312, 34, 26, false)),
            Map.entry(MCVersion.v1_11_0, new Config(10387312, 27, 17, false)),
            Map.entry(MCVersion.v1_0_0, new Config(10387312, 40, 28, true))
    );

    public Village(MCVersion version) {
        super(version, Dimension.OVERWORLD);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_0_0;
    }

    @Override
    public String getName() {
        return "village";
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
        if (getVersion().hasVillageAndPillage()) {
            return super.getChunkInRegion(seed, regionX, regionZ, random);
        } else {
            return getLegacyVillage(seed, regionX, regionZ, random);
        }
    }

    private ChunkPos getLegacyVillage(long seed, int chunkX, int chunkZ, ChunkRandom random) {
        int regionX = Math.floorDiv(chunkX, getSpacing());
        int regionZ = Math.floorDiv(chunkZ, getSpacing());
        ChunkPos chunkPos = getRandomOffsetChunk(seed, regionX, regionZ, random);
        if (chunkPos.getX() != Math.floorMod(chunkX, getSpacing())) return null;
        if (chunkPos.getZ() != Math.floorMod(chunkZ, getSpacing())) return null;
        return new ChunkPos(chunkX, chunkZ);
    }
}