package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.state.Dimension;

import java.util.List;
import java.util.Map;

public class TrailRuins extends Structure {

    private static final List<Map.Entry<MCVersion, Config>> CONFIGS = List.of(
            Map.entry(MCVersion.v1_20_0, new Config(83469867, 34, 26, true))
    );

    public TrailRuins(MCVersion version) {
        super(version, Dimension.OVERWORLD);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_20_0;
    }

    @Override
    public String getName() {
        return "trail_ruins";
    }

    @Override
    protected Config getConfig() {
        return getConfig(CONFIGS);
    }

    @Override
    public ChunkRandom getRandom() {
        return ChunkRandom.java();
    }
}