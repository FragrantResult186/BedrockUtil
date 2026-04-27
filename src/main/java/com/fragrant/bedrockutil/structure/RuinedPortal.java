package com.fragrant.bedrockutil.structure;

import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.util.state.Dimension;

import java.util.List;
import java.util.Map;

public class RuinedPortal extends Structure {

    private static final Map<Dimension, List<Map.Entry<MCVersion, Config>>> CONFIGS = Map.of(
            Dimension.OVERWORLD, List.of(Map.entry(MCVersion.v1_16_0, new Config(40552231, 40, 25, true))),
            Dimension.NETHER, List.of(Map.entry(MCVersion.v1_16_0, new Config(40552231, 25, 15, true)))
    );

    public RuinedPortal(MCVersion version, Dimension dimension) {
        super(version, dimension);
    }

    @Override
    public MCVersion getAvailableVersion() {
        return MCVersion.v1_16_0;
    }

    @Override
    public String getName() {
        return "ruined_portal";
    }

    @Override
    protected Config getConfig() {
        List<Map.Entry<MCVersion, Config>> configs = CONFIGS.get(getDimension());
        if (configs == null) throw new RuntimeException("Invalid dimension");
        return getConfig(configs);
    }

    @Override
    public ChunkRandom getRandom() {
        return ChunkRandom.bedrock();
    }
}