package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.state.Dimension;
import com.fragrant.bedrockutil.util.math.BlockPos;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;
import java.util.Map;

public class BuriedTreasure extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_4_0, new Config(16842397, 4, 2, true))
    );

    public BuriedTreasure(MCVersion MCVersion) {
        super(MCVersion, Dimension.OVERWORLD);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_4_0;
    }

    @Override
    public String getName() {
        return "buried_treasure";
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
    public BlockPos getStructurePos(long seed, int regionX, int regionZ, ChunkRandom random) {
        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_5_0)) {
            return super.getStructurePos(seed, regionX, regionZ, random);
        } else {
            // hmm, is this a bug?
            BlockPos blockPos = super.getStructurePos(seed, regionX, regionZ, random);
            return blockPos.add(-2, 0, -2);
        }
    }
}