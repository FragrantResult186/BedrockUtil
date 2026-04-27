package com.fragrant.bedrockutil.structure.generator.structure.mineshaft.pieces;


import com.fragrant.bedrockutil.structure.generator.StructurePiece;
import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.MineshaftGenerator;
import com.fragrant.bedrockutil.util.math.BlockPos;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public abstract class MineshaftPiece extends StructurePiece<MineshaftPiece> {

    public BoundingBox boundingBox;
    protected Direction facing;

    public MineshaftPiece(int genDepth) {
        super(genDepth);
    }

    public abstract void addChildren(MineshaftGenerator gen, ChunkRandom random);

    public abstract void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random);

    protected static MineshaftPiece findCollisionPiece(List<MineshaftPiece> pieces, BoundingBox box) {
        for (MineshaftPiece piece : pieces) {
            if (piece.getBoundingBox() != null && piece.getBoundingBox().intersects(box)) {
                return piece;
            }
        }
        return null;
    }

    public Direction getFacing() {
        return facing;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "%s %s".formatted(getName(), getBoundingBox());
    }
}