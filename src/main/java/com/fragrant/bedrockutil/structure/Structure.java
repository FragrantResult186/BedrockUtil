package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.state.Dimension;
import com.fragrant.bedrockutil.util.math.BlockPos;
import com.fragrant.bedrockutil.util.math.ChunkPos;
import com.fragrant.bedrockutil.util.math.RegionPos;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.version.UnsupportedVersion;

import java.util.List;
import java.util.Map;

public abstract class Structure {
    private Dimension dimension;
    private MCVersion version;

    protected record Config(int salt, int spacing, int separation, boolean liner) {}

    public Structure(MCVersion version, Dimension dimension) {
        if (version.isOlderThan(getAvailableVersion())) {
            throw new UnsupportedVersion(version, this.getName());
        }
        this.version = version;
        this.dimension = dimension;
    }

    public abstract MCVersion getAvailableVersion();

    public abstract String getName();

    protected abstract Config getConfig();

    public abstract ChunkRandom getRandom();

    public int getSalt() {
        return getConfig().salt();
    }

    public int getSpacing() {
        return getConfig().spacing();
    }

    public int getSeparation() {
        return getConfig().separation();
    }

    public boolean isLiner() {
        return getConfig().liner();
    }

    protected <T> T getConfig(List<Map.Entry<MCVersion, T>> configs) {
        return configs.stream()
                .filter(e -> getVersion().isNewerOrEqualTo(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();
    }

    public boolean canGenerate(long seed, int chunkX, int chunkZ, ChunkRandom random) {
        RegionPos regionPos = new ChunkPos(chunkX, chunkZ).toRegionPos(getSpacing());
        ChunkPos chunkPos = getChunkInRegion(seed, regionPos.getX(), regionPos.getZ(), random);
        if (chunkPos == null) return false;
        return chunkPos.getX() == chunkX && chunkPos.getZ() == chunkZ;
    }

    public boolean canGenerate(long seed, int chunkX, int chunkZ) {
        return canGenerate(seed, chunkX, chunkZ, getRandom());
    }

    public BlockPos getStructurePos(long seed, int regionX, int regionZ, ChunkRandom random) {
        ChunkPos chunkPos = getChunkInRegion(seed, regionX, regionZ, random);
        if (chunkPos == null) return null;
        return new BlockPos(chunkPos.getX() * 16 + 8, chunkPos.getZ() * 16 + 8);
    }

    public BlockPos getStructurePos(long seed, int regionX, int regionZ) {
        return getStructurePos(seed, regionX, regionZ, getRandom());
    }

    public ChunkPos getRandomOffsetChunk(long seed, int regionX, int regionZ, ChunkRandom random) {
        random.setRegionSeed(seed, regionX, regionZ, getSalt());
        int x, z;
        if (isLiner()) {
            x = random.nextInt(getSeparation());
            z = random.nextInt(getSeparation());
        } else {
            x = (random.nextInt(getSeparation()) + random.nextInt(getSeparation())) / 2;
            z = (random.nextInt(getSeparation()) + random.nextInt(getSeparation())) / 2;
        }
        return new ChunkPos(x, z);
    }

    public ChunkPos getChunkInRegion(long seed, int regionX, int regionZ, ChunkRandom random) {
        ChunkPos chunkPos = getRandomOffsetChunk(seed, regionX, regionZ, random);
        return chunkPos.add(regionX * getSpacing(), regionZ * getSpacing());
    }

    public ChunkPos getChunkInRegion(long seed, int regionX, int regionZ) {
        return getChunkInRegion(seed, regionX, regionZ, getRandom());
    }

    public MCVersion getVersion() {
        return this.version;
    }

    public Dimension getDimension() {
        return this.dimension;
    }
}