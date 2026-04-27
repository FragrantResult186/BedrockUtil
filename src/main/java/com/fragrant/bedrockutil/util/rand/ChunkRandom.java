package com.fragrant.bedrockutil.util.rand;

import com.fragrant.bedrockutil.version.MCVersion;

public class ChunkRandom implements IRandom {
    private final IRandom random;
    private int a; // 1st nextInt
    private int b; // 2nd nextInt

    public static ChunkRandom bedrock() {
        return new ChunkRandom(new BedrockRandom());
    }

    public static ChunkRandom java() {
        return new ChunkRandom(new JavaRandom());
    }

    public ChunkRandom(IRandom random) {
        this.random = random;
    }

    public int getPopulationSeed(long worldSeed, int chunkX, int chunkZ) {
        updateNextAB(worldSeed);
        return (int) (((this.a | 1) * chunkX + (this.b | 1) * chunkZ) ^ worldSeed);
    }

    public int setPopulationSeed(long worldSeed, int chunkX, int chunkZ) {
        int seed = getPopulationSeed(worldSeed, chunkX, chunkZ);
        this.random.setSeed(seed);
        return seed;
    }

    public int getDecorationSeed(long worldSeed, int chunkX, int chunkZ, int salt) {
        int seed = getPopulationSeed(worldSeed, chunkX, chunkZ);
        return ((seed >> 2) + (seed << 6) + salt - 1640531527) ^ seed;
    }

    public int setDecorationSeed(long worldSeed, int chunkX, int chunkZ, int salt) {
        int seed = getDecorationSeed(worldSeed, chunkX, chunkZ, salt);
        this.random.setSeed(seed);
        return seed;
    }

    public int getRegionSeed(long worldSeed, int regionX, int regionZ, int salt) {
        return (int) ((regionX * 341873128712L) + (regionZ * 132897987541L) + worldSeed + salt);
    }

    public int setRegionSeed(long worldSeed, int regionX, int regionZ, int salt) {
        int seed = getRegionSeed(worldSeed, regionX, regionZ, salt);
        this.random.setSeed(seed);
        return seed;
    }

    public int getCarverSeed(long worldSeed, int chunkX, int chunkZ) {
        updateNextAB(worldSeed);
        return (int) ((this.a * chunkX) ^ (this.b * chunkZ) ^ worldSeed);
    }

    public int setCarverSeed(long worldSeed, int chunkX, int chunkZ) {
        int seed = getCarverSeed(worldSeed, chunkX, chunkZ);
        this.random.setSeed(seed);
        return seed;
    }

    public int getFortressSeed(long worldSeed, int chunkX, int chunkZ) {
        return (int) (((chunkX >> 4) ^ ((chunkZ >> 4) << 4)) ^ worldSeed);
    }

    public int setFortressSeed(long worldSeed, int chunkX, int chunkZ) {
        int seed = getFortressSeed(worldSeed, chunkX, chunkZ);
        this.random.setSeed(seed);
        return seed;
    }

    public int getStrongholdSeed(long worldSeed, int regionX, int regionZ) {
        return (int) ((regionX * 784295783249L) + (regionZ * 827828252345L) + worldSeed + 97858791);
    }

    public int setStrongholdSeed(long worldSeed, int regionX, int regionZ) {
        int seed = getStrongholdSeed(worldSeed, regionX, regionZ);
        this.random.setSeed(seed);
        return seed;
    }

    public int getEndCitySeed(int chunkX, int chunkZ) {
        return chunkX + chunkZ * 10387313;
    }

    public int setEndCitySeed(int chunkX, int chunkZ) {
        int seed = getEndCitySeed(chunkX, chunkZ);
        this.random.setSeed(seed);
        return seed;
    }

    public int getMineshaftSeed(long worldSeed, int chunkX, int chunkZ, MCVersion MCVersion) {
        if (MCVersion.hasVillageAndPillage()) {
            return getCarverSeed(worldSeed, chunkX, chunkZ);
        } else {
            return (int) (worldSeed ^ chunkZ ^ chunkX);
        }
    }

    public int setMineshaftSeed(long worldSeed, int chunkX, int chunkZ, MCVersion MCVersion) {
        int seed = getMineshaftSeed(worldSeed, chunkX, chunkZ, MCVersion);
        this.random.setSeed(seed);
        return seed;
    }

    public int getSlimeSeed(int chunkX, int chunkZ) {
        return (chunkX * 522133279) ^ chunkZ;
    }

    public int setSlimeSeed(int chunkX, int chunkZ) {
        int seed = getSlimeSeed(chunkX, chunkZ);
        this.random.setSeed(seed);
        return seed;
    }

    private void updateNextAB(long worldSeed) {
        if ((int) worldSeed == (int) this.getSeed()) return;
        this.random.setSeed(worldSeed);
        this.a = random.nextInt();
        this.b = random.nextInt();
    }

    @Override
    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public long getSeed() {
        return random.getSeed();
    }
}