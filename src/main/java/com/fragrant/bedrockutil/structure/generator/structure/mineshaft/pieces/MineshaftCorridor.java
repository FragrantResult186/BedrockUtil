package com.fragrant.bedrockutil.structure.generator.structure.mineshaft.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.*;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public class MineshaftCorridor extends MineshaftPiece {

    private final boolean hasRails;
    private final boolean spiderCorridor;
    private final int numSections;

    public MineshaftCorridor(int pieceId, ChunkRandom random, BoundingBox boundingBox, Direction direction) {
        super(pieceId);
        this.boundingBox = boundingBox;
        this.facing = direction;
        this.hasRails = random.nextInt(3) == 0;
        this.spiderCorridor = !this.hasRails && random.nextInt(23) == 0;
        this.numSections = getNumSections(boundingBox, direction);
    }

    private int getNumSections(BoundingBox boundingBox, Direction direction) {
        if (direction.getAxis() == Direction.Axis.Z) {
            return boundingBox.getZSpan() / 5;
        } else {
            return boundingBox.getXSpan() / 5;
        }
    }

    public static BoundingBox findCorridor(
            List<MineshaftPiece> pieces,
            ChunkRandom random,
            int x, int y, int z,
            Direction direction
    ) {
        int maxAttempts = random.nextInt(3) + 2;
        for (int attempts = maxAttempts; attempts > 0; attempts--) {
            int length = attempts * 5;
            BoundingBox box = BoundingBox.createOrientedBox(x, y, z, 0, 0, 0, 3, 3, length, direction);
            if (MineshaftPiece.findCollisionPiece(pieces, box) == null) {
                return box;
            }
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

        generateMainBranch(gen, random, facing, minX, minY, minZ, maxX, maxY, maxZ, genDepth);
        if (genDepth < 8) {
            generateSideBranch(gen, random, facing, minX, minY, minZ, maxX, maxY, maxZ, genDepth);
        }
    }

    private void generateMainBranch(MineshaftGenerator gen, ChunkRandom random, Direction facing,
                                    int minX, int minY, int minZ,
                                    int maxX, int maxY, int maxZ,
                                    int genDepth) {
        int branchType = random.nextInt(4);
        int yOffset = random.nextInt(3);
        switch (branchType) {
            // Front
            case 0, 1 -> {
                switch (facing) {
                    case NORTH -> gen.generateAndAddPiece(gen.pieces, random, minX, minY - 1 + yOffset, minZ - 1, facing, genDepth);
                    case SOUTH -> gen.generateAndAddPiece(gen.pieces, random, minX, minY - 1 + yOffset, maxZ + 1, facing, genDepth);
                    case WEST  -> gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY - 1 + yOffset, minZ, facing, genDepth);
                    case EAST  -> gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY - 1 + yOffset, minZ, facing, genDepth);
                }
            }
            // Left
            case 2 -> {
                switch (facing) {
                    case NORTH -> gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY - 1 + yOffset, minZ, Direction.WEST, genDepth);
                    case SOUTH -> gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY - 1 + yOffset, maxZ - 3, Direction.WEST, genDepth);
                    case WEST  -> gen.generateAndAddPiece(gen.pieces, random, minX, minY - 1 + yOffset, minZ - 1, Direction.NORTH, genDepth);
                    case EAST  -> gen.generateAndAddPiece(gen.pieces, random, maxX - 3, minY - 1 + yOffset, minZ - 1, Direction.NORTH, genDepth);
                }
            }
            // Right
            case 3 -> {
                switch (facing) {
                    case NORTH -> gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY - 1 + yOffset, minZ, Direction.EAST, genDepth);
                    case SOUTH -> gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY - 1 + yOffset, maxZ - 3, Direction.EAST, genDepth);
                    case WEST  -> gen.generateAndAddPiece(gen.pieces, random, minX, minY - 1 + yOffset, maxZ + 1, Direction.SOUTH, genDepth);
                    case EAST  -> gen.generateAndAddPiece(gen.pieces, random, maxX - 3, minY - 1 + yOffset, maxZ + 1, Direction.SOUTH, genDepth);
                }
            }
        }
    }

    private void generateSideBranch(MineshaftGenerator gen, ChunkRandom random, Direction facing,
                                    int minX, int minY, int minZ,
                                    int maxX, int maxY, int maxZ,
                                    int genDepth) {
        if (facing.getAxis() == Direction.Axis.Z) {
            for (int z = minZ + 3; z + 3 <= maxZ; z += 5) {
                int branchType = random.nextInt(5);
                switch (branchType) {
                    case 0 -> gen.generateAndAddPiece(gen.pieces, random, minX - 1, minY, z, Direction.WEST, genDepth + 1);
                    case 1 -> gen.generateAndAddPiece(gen.pieces, random, maxX + 1, minY, z, Direction.EAST, genDepth + 1);
                }
            }
        } else { // Direction.Axis.X
            for (int x = minX + 3; x + 3 <= maxX; x += 5) {
                int branchType = random.nextInt(5);
                switch (branchType) {
                    case 0 -> gen.generateAndAddPiece(gen.pieces, random, x, minY, minZ - 1, Direction.NORTH, genDepth + 1);
                    case 1 -> gen.generateAndAddPiece(gen.pieces, random, x, minY, maxZ + 1, Direction.SOUTH, genDepth + 1);
                }
            }
        }
    }

    @Override
    public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

    }

    public int getNumSections() {
        return numSections;
    }

    public boolean hasRails() {
        return hasRails;
    }

    public boolean isSpiderCorridor() {
        return spiderCorridor;
    }
}