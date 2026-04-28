package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.math.Vec3i;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.Collections;
import java.util.List;

public class RoomCrossing extends StrongholdPiece {

    private final int variants;

    public RoomCrossing(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) {
        super(pieceId);
        this.facing = facing;
        rand.nextInt(5);
        this.boundingBox = boundingBox;
        this.variants = rand.nextInt(5);
    }

    public static RoomCrossing createPiece(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing, int pieceId) {
        BoundingBox box = BoundingBox.rotated(x, y, z, -4, -1, 0, 11, 7, 11, facing.getRotation());
        return StrongholdPiece.isHighEnough(box) && StrongholdPiece.findCollisionPiece(pieces, box) == null ? new RoomCrossing(pieceId, rand, box, facing) : null;
    }

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {
        this.generateChildrenForward(gen, start, pieces, rand, 4, 1);
        this.generateChildrenLeft(gen, start, pieces, rand, 1, 4);
        this.generateChildrenRight(gen, start, pieces, rand, 1, 4);
    }

    @Override
    public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

    }

    public int getVariants() {
        return variants;
    }

    @Override
    protected List<Vec3i> getChestPos() {
        return variants == 2
                ? Collections.singletonList(new Vec3i(3, 1, 8))
                : Collections.emptyList();
    }
}