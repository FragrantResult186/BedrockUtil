package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.state.Dimension;

import java.util.List;
import java.util.Map;

public class SwampHut extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_0_0, new Config(14357617, 32, 24, true))
    );

    public SwampHut(MCVersion version) {
        super(version, Dimension.OVERWORLD);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_0_0;
    }

    @Override
    public String getName() {
        return "swamp_hut";
    }

    @Override
    protected Config getConfig() {
        return getConfig(CONFIGS);
    }

    @Override
    public ChunkRandom getRandom() {
        return ChunkRandom.bedrock();
    }
}