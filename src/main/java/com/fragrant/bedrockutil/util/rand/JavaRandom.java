package com.fragrant.bedrockutil.util.rand;

import java.util.Random;

public class JavaRandom extends Random implements IRandom {
    private long seed; // current seed

    public JavaRandom() {
        this(new Random().nextLong());
    }

    public JavaRandom(long seed) {
        super(seed);
        this.seed = seed;
    }

    @Override
    public void setSeed(long seed) {
        super.setSeed(seed);
        this.seed = seed;
    }

    @Override
    public long getSeed() {
        return seed;
    }
}