package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.StructurePiece;
import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public abstract class StrongholdPiece extends StructurePiece<StrongholdPiece> {

    public StrongholdPiece(int genDepth) {
        super(genDepth);
    }

    public abstract void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand);

    public abstract void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random);

    protected static StrongholdPiece findCollisionPiece(List<StrongholdPiece> pieces, BoundingBox box) {
        for (StrongholdPiece piece : pieces) {
            if (piece.getBoundingBox() != null && piece.getBoundingBox().intersects(box)) {
                return piece;
            }
        }
        return null;
    }

    protected static boolean isHighEnough(BoundingBox box) {
        return box != null && box.getMinY() > 10;
    }

    protected StructurePiece<StrongholdPiece> generateChildrenForward(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand, int a, int b) {
        Direction facing = this.getFacing();

        if (facing == null) {
            return null;
        }

        StrongholdPiece newPiece = null;

        if (facing == Direction.NORTH) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() + a, this.boundingBox.getMinY() + b, this.boundingBox.getMinZ() - 1, facing, this.pieceId);
        } else if (facing == Direction.SOUTH) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() + a, this.boundingBox.getMinY() + b, this.boundingBox.getMaxZ() + 1, facing, this.pieceId);
        } else if (facing == Direction.WEST) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() - 1, this.boundingBox.getMinY() + b, this.boundingBox.getMinZ() + a, facing, this.pieceId);
        } else if (facing == Direction.EAST) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMaxX() + 1, this.boundingBox.getMinY() + b, this.boundingBox.getMinZ() + a, facing, this.pieceId);
        }

        if (newPiece != null) {
            this.setNextPiece(newPiece);
        }

        return newPiece;
    }

    protected StructurePiece<StrongholdPiece> generateChildrenLeft(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand, int a, int b) {
        Direction facing = this.getFacing();

        if (facing == null) {
            return null;
        }

        StrongholdPiece newPiece = null;

        if (facing == Direction.NORTH) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() - 1, this.boundingBox.getMinY() + a, this.boundingBox.getMinZ() + b, Direction.WEST, this.pieceId);
        } else if (facing == Direction.SOUTH) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() - 1, this.boundingBox.getMinY() + a, this.boundingBox.getMinZ() + b, Direction.WEST, this.pieceId);
        } else if (facing == Direction.WEST) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() + b, this.boundingBox.getMinY() + a, this.boundingBox.getMinZ() - 1, Direction.NORTH, this.pieceId);
        } else if (facing == Direction.EAST) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() + b, this.boundingBox.getMinY() + a, this.boundingBox.getMinZ() - 1, Direction.NORTH, this.pieceId);
        }

        if (newPiece != null) {
            this.setNextPiece(newPiece);
        }

        return newPiece;
    }

    protected StructurePiece<StrongholdPiece> generateChildrenRight(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand, int a, int b) {
        Direction facing = this.getFacing();

        if (facing == null) {
            return null;
        }

        StrongholdPiece newPiece = null;

        if (facing == Direction.NORTH) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMaxX() + 1, this.boundingBox.getMinY() + a, this.boundingBox.getMinZ() + b, Direction.EAST, this.pieceId);
        } else if (facing == Direction.SOUTH) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMaxX() + 1, this.boundingBox.getMinY() + a, this.boundingBox.getMinZ() + b, Direction.EAST, this.pieceId);
        } else if (facing == Direction.WEST) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() + b, this.boundingBox.getMinY() + a, this.boundingBox.getMaxZ() + 1, Direction.SOUTH, this.pieceId);
        } else if (facing == Direction.EAST) {
            newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.getMinX() + b, this.boundingBox.getMinY() + a, this.boundingBox.getMaxZ() + 1, Direction.SOUTH, this.pieceId);
        }

        if (newPiece != null) {
            this.setNextPiece(newPiece);
        }

        return newPiece;
    }

}