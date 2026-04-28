package com.fragrant.bedrockutil;

import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.MineshaftGenerator;
import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.MineshaftType;
import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.version.MCVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGeneration {

    @Test
    void testMineshaftGenerator() {
        MineshaftGenerator gen = new MineshaftGenerator(MCVersion.v1_18_0, MineshaftType.NORMAL);
        gen.generate(37290L, 1, 0);
        assertTrue(gen.shouldGenerate);
        assertEquals(129, gen.pieces.size());
    }

    @Test
    void testStrongholdGenerator() {
        StrongholdGenerator gen = new StrongholdGenerator(MCVersion.v1_18_0);
        gen.generate(334L, 71, 73);
        assertEquals(187, gen.pieces.size());
    }

    @Test
    void testNetherFortressGenerator() {

    }

    @Test
    void testEndCityGenerator() {

    }

}
