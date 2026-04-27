package com.fragrant.bedrockutil.structure.generator;

import com.fragrant.bedrockutil.util.math.ChunkPos;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.version.MCVersion;

public abstract class Generator {

    protected MCVersion version;

    public Generator(MCVersion version) {
        this.version = version;
    }

    public abstract boolean generate(long worldSeed, int chunkX, int chunkZ, ChunkRandom random);

    public boolean generate(long worldSeed, int chunkX, int chunkZ) {
        return generate(worldSeed, chunkX, chunkZ, ChunkRandom.bedrock());
    }

    public boolean generate(long worldSeed, ChunkPos chunkPos) {
        return generate(worldSeed, chunkPos.getX(), chunkPos.getZ());
    }
}
