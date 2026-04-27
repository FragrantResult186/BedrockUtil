package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.math.ChunkPos;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.util.state.Dimension;

import java.util.List;
import java.util.Map;

public class NetherFortress extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_16_0, new Config(30084232, 30, 26, true)),
            Map.entry(MCVersion.v1_0_0, new Config(30084232, 16, 8, true))
    );

    public NetherFortress(MCVersion version) {
        super(version, Dimension.NETHER);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_0_0;
    }

    @Override
    public String getName() {
        return "nether_fortress";
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
        if (getVersion().hasNetherUpdate()) {
            ChunkPos chunkPos = super.getChunkInRegion(seed, regionX, regionZ, random);
            return random.nextInt(6) < 2 ? chunkPos :  null;
        } else {
            int originX = regionX * getSpacing();
            int originZ = regionZ * getSpacing();
            random.setFortressSeed(seed, originX, originZ);
            random.advance(1);
            if (random.nextInt(3) != 0) return null;
            int chunkX = (originX & ~15) + random.nextInt(getSeparation()) + 4;
            int chunkZ = (originZ & ~15) + random.nextInt(getSeparation()) + 4;
            return new ChunkPos(chunkX, chunkZ);
        }
    }
}