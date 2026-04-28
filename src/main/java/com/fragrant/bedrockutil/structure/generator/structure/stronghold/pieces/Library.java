package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.math.Vec3i;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.ArrayList;
import java.util.List;

public class Library extends StrongholdPiece {

    private final boolean isTall;

    public Library(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) {
        super(pieceId);
        this.facing = facing;
        rand.nextInt(5);
        this.boundingBox = boundingBox;
        this.isTall = this.boundingBox.getYSpan() > 6;
    }

    public static Library createPiece(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing, int pieceId) {
        // Tall
        BoundingBox box = BoundingBox.rotated(x, y, z, -4, -1, 0, 14, 11, 15, facing.getRotation());
        if (!StrongholdPiece.isHighEnough(box) || StrongholdPiece.findCollisionPiece(pieces, box) != null) {
            // Short
            box = BoundingBox.rotated(x, y, z, -4, -1, 0, 14, 6, 15, facing.getRotation());
            if (!StrongholdPiece.isHighEnough(box) || StrongholdPiece.findCollisionPiece(pieces, box) != null) {
                return null;
            }
        }
        return new Library(pieceId, rand, box, facing);
    }

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {

    }

    @Override
    public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

    }

    public boolean isTall() {
        return isTall;
    }

    @Override
    protected List<Vec3i> getChestPos() {
        setChest(new Vec3i(3, 1, 5));
        if (this.isTall) {
            setChest(new Vec3i(12, 5, 1));
        }
        return chests;
    }

}