package com.fragrant.bedrockutil.structure.generator.structure.mineshaft.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.*;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public class MineshaftStairs extends MineshaftPiece {

    public MineshaftStairs(int pieceId, ChunkRandom random, BoundingBox boundingBox, Direction direction) {
        super(pieceId);
        this.boundingBox = boundingBox;
        this.facing = direction;
    }

    public static BoundingBox findStairs(List<MineshaftPiece> pieces, ChunkRandom random, int x, int y, int z, Direction direction) {
        BoundingBox box = BoundingBox.createOrientedBox(x, y, z, 0, -5, 0, 3, 8, 9, direction);
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
            case NORTH -> gen.generateAndAddPiece(gen.pieces, random, minX, minY, minZ - 1, Direction.NORTH, genDepth);
            case SOUTH -> gen.generateAndAddPiece(gen.pieces, random, minX, minY, maxZ + 1, Direction.SOUTH, genDepth);
            case WEST  -> gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY, minZ, Direction.WEST, genDepth);
            case EAST  -> gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY, minZ, Direction.EAST, genDepth);
        }
    }

    @Override
    public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

    }
}