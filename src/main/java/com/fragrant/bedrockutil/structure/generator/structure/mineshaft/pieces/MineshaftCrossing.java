package com.fragrant.bedrockutil.structure.generator.structure.mineshaft.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.*;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public class MineshaftCrossing extends MineshaftPiece {

    private final boolean isTwoFloored;

    public MineshaftCrossing(int pieceId, ChunkRandom random, BoundingBox boundingBox, Direction direction) {
        super(pieceId);
        this.boundingBox = boundingBox;
        this.facing = direction;
        this.isTwoFloored = boundingBox.getYSpan() > 3;
    }

    public static BoundingBox findCrossing(List<MineshaftPiece> pieces, ChunkRandom random, int x, int y, int z, Direction direction) {
        int sizeY = 2;
        if (random.nextInt(4) == 0) {
            sizeY += 4;
        }
        BoundingBox box = BoundingBox.createOrientedBox(x, y, z, -1, 0, 0, 5, sizeY + 1, 5, direction);
        if (MineshaftPiece.findCollisionPiece(pieces, box) == null) {
            return box;
        }
        return null;
    }

    @Override
    public void addChildren(MineshaftGenerator gen, ChunkRandom random) {
        int genDepth = this.getGenDepth();
        Direction facing = this.getFacing();

        BoundingBox box = this.getBoundingBox();

        int minX = box.getMinX();
        int minY = box.getMinY();
        int minZ = box.getMinZ();
        int maxX = box.getMaxX();
        int maxY = box.getMaxY();
        int maxZ = box.getMaxZ();

        switch (facing) {
            case NORTH -> {
                gen.generateAndAddPiece(gen.pieces, random, minX + 1, minY, minZ - 1, Direction.NORTH, genDepth);
                gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY, minZ + 1, Direction.WEST, genDepth);
                gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY, minZ + 1, Direction.EAST, genDepth);
            }
            case SOUTH -> {
                gen.generateAndAddPiece(gen.pieces, random, minX + 1, minY, maxZ + 1, Direction.SOUTH, genDepth);
                gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY, minZ + 1, Direction.WEST, genDepth);
                gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY, minZ + 1, Direction.EAST, genDepth);
            }
            case WEST -> {
                gen.generateAndAddPiece(gen.pieces, random, minX + 1, minY, minZ - 1, Direction.NORTH, genDepth);
                gen.generateAndAddPiece(gen.pieces, random, minX + 1, minY, maxZ + 1, Direction.SOUTH, genDepth);
                gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY, minZ + 1, Direction.WEST, genDepth);
            }
            case EAST -> {
                gen.generateAndAddPiece(gen.pieces, random, minX + 1, minY, minZ - 1, Direction.NORTH, genDepth);
                gen.generateAndAddPiece(gen.pieces, random, minX + 1, minY, maxZ + 1, Direction.SOUTH, genDepth);
                gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY, minZ + 1, Direction.EAST, genDepth);
            }
        }

        if (this.isTwoFloored) {
            if (random.nextBoolean()) gen.generateAndAddPiece(gen.pieces, random, minX + 1, minY + 3 + 1, minZ - 1, Direction.NORTH, genDepth);
            if (random.nextBoolean()) gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY + 3 + 1, minZ + 1, Direction.WEST, genDepth);
            if (random.nextBoolean()) gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY + 3 + 1, minZ + 1, Direction.EAST, genDepth);
            if (random.nextBoolean()) gen.generateAndAddPiece(gen.pieces, random, minX + 1, minY + 3 + 1, maxZ + 1, Direction.SOUTH, genDepth);
        }
    }

    @Override
    public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

    }

    public boolean isTwoFloored() {
        return isTwoFloored;
    }
}