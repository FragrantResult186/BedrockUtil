package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.math.Vec3i;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.Collections;
import java.util.List;

public class ChestCorridor extends StrongholdPiece {

    public ChestCorridor(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) {
        super(pieceId);
        this.facing = facing;
        rand.nextInt(5);
        this.boundingBox = boundingBox;
    }

    public static ChestCorridor createPiece(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing, int pieceId) {
        BoundingBox box = BoundingBox.rotated(x, y, z, -1, -1, 0, 5, 5, 7, facing.getRotation());
        return StrongholdPiece.isHighEnough(box) && StrongholdPiece.findCollisionPiece(pieces, box) == null ? new ChestCorridor(pieceId, rand, box, facing) : null;
    }

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {
        this.generateChildrenForward(gen, start, pieces, rand, 1, 1);
    }

    @Override
    public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

    }

    @Override
    protected List<Vec3i> getChestPos() {
        setChest(new Vec3i(3, 1, 3));
        return Collections.singletonList(new Vec3i(3, 1, 3));
    }

}