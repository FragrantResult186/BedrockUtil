package com.fragrant.bedrockutil.util.rand;

public interface IRandom {
    void setSeed(long seed);

    int nextInt();

    int nextInt(int bound);

    float nextFloat();

    boolean nextBoolean();

    long getSeed();

    default void advance(int n) {
        for (int i = 0; i < n; i++) nextInt();
    }

}