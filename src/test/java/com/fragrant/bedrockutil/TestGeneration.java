package com.fragrant.bedrockutil;

import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.MineshaftGenerator;
import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.MineshaftType;
import com.fragrant.bedrockutil.version.MCVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGeneration {

    @Test
    void testMineshaftGenerator() {
        MineshaftGenerator gen = new MineshaftGenerator(MineshaftType.NORMAL, MCVersion.v1_18_0);
        gen.generate(37290L, 1, 0);
        assertTrue(gen.shouldGenerate);
        assertEquals(129, gen.pieces.size());
    }

    @Test
    void testStrongholdGenerator() {

    }

    @Test
    void testNetherFortressGenerator() {

    }

    @Test
    void testEndCityGenerator() {

    }

}
