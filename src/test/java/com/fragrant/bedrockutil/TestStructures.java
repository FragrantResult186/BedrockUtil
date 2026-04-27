package com.fragrant.bedrockutil;

import com.fragrant.bedrockutil.util.state.Dimension;
import com.fragrant.bedrockutil.version.MCVersion;
import com.fragrant.bedrockutil.structure.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStructures {

    @Test
    void ancientCity1_19_0() {
        AncientCity ancientCity = new AncientCity(MCVersion.v1_19_0);
        assertTrue(ancientCity.canGenerate(88291L, 0, 0));
    }

    @Test
    void BastionRemnant1_16_0() {
        BastionRemnant bastionRemnant = new BastionRemnant(MCVersion.v1_16_0);
        assertTrue(bastionRemnant.canGenerate(187L, 0, 0));
    }

    @Test
    void BuriedTreasure1_2_0() {
        BuriedTreasure buriedTreasure = new BuriedTreasure(MCVersion.v1_16_0);
        assertTrue(buriedTreasure.canGenerate(59L, 0, 0));
    }

    @Test
    void DesertPyramid1_0_0() {
        DesertPyramid desertPyramid = new DesertPyramid(MCVersion.v1_0_0);
        assertTrue(desertPyramid.canGenerate(3871L, 0, 0));
    }

    @Test
    void EndCity1_0_0() {
        EndCity endCity = new EndCity(MCVersion.v1_0_0);
        assertTrue(endCity.canGenerate(123L, -60, -36));
    }

    @Test
    void GridStronghold1_0_0() {
        GridStronghold gridStronghold = new GridStronghold(MCVersion.v1_0_0);
        assertTrue(gridStronghold.canGenerate(334L, 129, 53));
    }

    @Test
    void Igloo1_0_0() {
        Igloo igloo = new Igloo(MCVersion.v1_0_0);
        assertTrue(igloo.canGenerate(3871L, 0, 0));
    }

    @Test
    void JungleTemple1_0_0() {
        JungleTemple jungleTemple = new JungleTemple(MCVersion.v1_0_0);
        assertTrue(jungleTemple.canGenerate(3871L, 0, 0));
    }

    @Test
    void Mineshaft1_0_0() {
        Mineshaft mineshaft = new Mineshaft(MCVersion.v1_0_0);
        assertTrue(mineshaft.canGenerate(92877L, 0, 2));
    }

    @Test
    void Mineshaft1_11_0() {
        Mineshaft mineshaft = new Mineshaft(MCVersion.v1_11_0);
        assertTrue(mineshaft.canGenerate(4159L, 1, 2));
    }

    @Test
    void OceanMonument1_0_0() {
        OceanMonument oceanMonument = new OceanMonument(MCVersion.v1_0_0);
        assertTrue(oceanMonument.canGenerate(3796256L, 0, 0));
    }

    @Test
    void NetherFortress1_0_0() {
        NetherFortress netherFortress = new NetherFortress(MCVersion.v1_0_0);
        assertTrue(netherFortress.canGenerate(2L, 9, 4));
    }

    @Test
    void NetherFortress1_16_0() {
        NetherFortress netherFortress = new NetherFortress(MCVersion.v1_16_0);
        assertTrue(netherFortress.canGenerate(2050L, 0, 0));
    }

    @Test
    void OceanRuin1_4_0() {
        OceanRuin oceanRuin = new OceanRuin(MCVersion.v1_4_0);
        assertTrue(oceanRuin.canGenerate(419L, 0, 0));
    }

    @Test
    void OceanRuin1_18_0() {
        OceanRuin oceanRuin = new OceanRuin(MCVersion.v1_18_0);
        assertTrue(oceanRuin.canGenerate(159L, 0, 0));
    }

    @Test
    void PillagerOutpost1_14_0() {
        PillagerOutpost pillagerOutpost = new PillagerOutpost(MCVersion.v1_14_0);
        pillagerOutpost.canGenerate(4492217L, 0, 0);
    }

    @Test
    void RuinedPortalOverworld1_16_0() {
        RuinedPortal ruinedPortal = new RuinedPortal(MCVersion.v1_16_0, Dimension.OVERWORLD);
        assertTrue(ruinedPortal.canGenerate(313L, 0, 0));
    }

    @Test
    void RuinedPortalNether1_16_0() {
        RuinedPortal ruinedPortal = new RuinedPortal(MCVersion.v1_16_0, Dimension.NETHER);
        assertTrue(ruinedPortal.canGenerate(49L, 0, 0));
    }

    @Test
    void Shipwreck1_4_0() {
        Shipwreck shipwreck = new Shipwreck(MCVersion.v1_4_0);
        assertTrue(shipwreck.canGenerate(186L, 0, 0));
    }

    @Test
    void Shipwreck1_18_0() {
        Shipwreck shipwreck = new Shipwreck(MCVersion.v1_18_0);
        assertTrue(shipwreck.canGenerate(2679L, 0, 0));
    }

    @Test
    void SwampHut1_0_0() {
        SwampHut swampHut = new SwampHut(MCVersion.v1_0_0);
        assertTrue(swampHut.canGenerate(3871L, 0, 0));
    }

    @Test
    void TrailRuins1_20_0() {
        TrailRuins trailRuins = new TrailRuins(MCVersion.v1_20_0);
        assertTrue(trailRuins.canGenerate(1681L, 0, 0));
    }

    @Test
    void TrialChamber1_21_0() {
        TrialChambers trialChambers = new TrialChambers(MCVersion.v1_21_0);
        assertTrue(trialChambers.canGenerate(438L, 0, 0));
    }

    @Test
    void village1_0_0() {
        Village village = new Village(MCVersion.v1_10_0);
        assertTrue(village.canGenerate(817L, 0, 0));
    }

    @Test
    void village1_11_0() {
        Village village = new Village(MCVersion.v1_11_0);
        assertTrue(village.canGenerate(1930L, 0, 0));
    }

    @Test
    void village1_18_0() {
        Village village = new Village(MCVersion.v1_18_0);
        assertTrue(village.canGenerate(58987L, 0, 0));
    }

    @Test
    void WoodlandMansion1_0_0() {
        WoodlandMansion woodlandMansion = new WoodlandMansion(MCVersion.v1_0_0);
        assertTrue(woodlandMansion.canGenerate(134611678L, 0, 0));
    }
}
