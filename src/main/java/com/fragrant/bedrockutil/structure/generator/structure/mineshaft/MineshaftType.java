package com.fragrant.bedrockutil.structure.generator.structure.mineshaft;

public enum MineshaftType {
    NORMAL(1.0F),
    MESA(0.5F);

    private final float generationProbability;

    MineshaftType(float generationProbability) {
        this.generationProbability = generationProbability;
    }

    public float getGenerationProbability() {
        return generationProbability;
    }
}
