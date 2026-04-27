package com.fragrant.bedrockutil.structure.generator.structure.mineshaft;

import com.fragrant.bedrockutil.structure.Mineshaft;
import com.fragrant.bedrockutil.structure.generator.Generator;
import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.pieces.*;
import com.fragrant.bedrockutil.structure.util.StructureBox;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.version.MCVersion;

import java.util.ArrayList;
import java.util.List;

public class MineshaftGenerator extends Generator {

    private final MineshaftType type;
    private BoundingBox boundingBox;
    public final List<MineshaftPiece> pieces = new ArrayList<>();
    public boolean shouldGenerate;
    protected MineshaftPiece start;

    public MineshaftGenerator(MineshaftType type, MCVersion version) {
        super(version);
        this.type = type;
    }

    @Override
    public boolean generate(long worldSeed, int chunkX, int chunkZ, ChunkRandom random) {
        Mineshaft mineshaft = new Mineshaft(this.version);
        this.shouldGenerate = mineshaft.canGenerate(worldSeed, chunkX, chunkZ, random);
        if (this.shouldGenerate) {
            build(worldSeed, chunkX, chunkZ, random);
        }
        return this.shouldGenerate;
    }

    public void build(long worldSeed, int chunkX, int chunkZ, ChunkRandom random) {
        clear();
        int x = (chunkX << 4) + 2;
        int z = (chunkZ << 4) + 2;

        this.start = new MineshaftRoom(0, random, x, 50, z);
        this.pieces.add(this.start);
        this.start.addChildren(this, random);

        BoundingBox box = StructureBox.createBoundingBox(this.pieces);
        int seaLevel = 63;

        if (this.type == MineshaftType.MESA) {
            int yOffset;
            if (this.version.isNewerOrEqualTo(MCVersion.v1_18_0)) {
                //TODO 1.18~ probably uses surfaceY
                yOffset = 0;//placeholder
            } else {
                //~1.17
                yOffset = box.getYSpan() / 2 - box.getMaxY() + seaLevel + 5;
            }
            StructureBox.moveBoundingBoxes(this.pieces, yOffset);
        } else {//NORMAL
            int minWorldHeight = this.version.isNewerOrEqualTo(MCVersion.v1_18_0) ? -64 : 0;
            StructureBox.moveBelowSeaLevel(this.pieces, box, random, seaLevel, minWorldHeight, 10);
        }
        //update box
        this.boundingBox = StructureBox.createBoundingBox(this.pieces);
    }

    public MineshaftPiece generateAndAddPiece(List<MineshaftPiece> pieces, ChunkRandom random, int x, int y, int z, Direction direction, int genDepth) {
        if (genDepth > 8 || Math.abs(x - this.start.getBoundingBox().getMinX()) > 80 || Math.abs(z - this.start.getBoundingBox().getMinZ()) > 80) {
            return null;
        } else {
            MineshaftPiece piece = createRandomPiece(pieces, random, x, y, z, direction, genDepth + 1);
            if (piece != null) {
                pieces.add(piece);
                piece.addChildren(this, random);
            }
            return piece;
        }
    }

    private MineshaftPiece createRandomPiece(List<MineshaftPiece> pieces, ChunkRandom random, int x, int y, int z, Direction direction, int genDepth) {
        int i = random.nextInt(100);

        if (i >= 80) {
            //Crossing
            BoundingBox box = MineshaftCrossing.findCrossing(pieces, random, x, y, z, direction);
            if (box != null) {
                return new MineshaftCrossing(genDepth, random, box, direction);
            }
        } else if (i >= 70) {
            //Stairs
            BoundingBox box = MineshaftStairs.findStairs(pieces, random, x, y, z, direction);
            if (box != null) {
                return new MineshaftStairs(genDepth, random, box, direction);
            }
        } else {
            //Corridor
            BoundingBox box = MineshaftCorridor.findCorridor(pieces, random, x, y, z, direction);
            if (box != null) {
                return new MineshaftCorridor(genDepth, random, box, direction);
            }
        }

        return null;
    }

    public MineshaftType getType() {
        return type;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void clear() {
        this.pieces.clear();
    }

}