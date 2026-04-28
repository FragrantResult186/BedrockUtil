package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BlockPos;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.math.Vec3i;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.ArrayList;
import java.util.List;

public class PortalRoom extends StrongholdPiece {

    //private List<EndPortalFrameData> frames = null;

    public PortalRoom(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) {
        super(pieceId);
        this.facing = facing;
        this.boundingBox = boundingBox;
    }

    public static PortalRoom createPiece(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing, int pieceId) {
        BoundingBox box = BoundingBox.rotated(x, y, z, -4, -1, 0, 11, 8, 16, facing.getRotation());
        return StrongholdPiece.isHighEnough(box) && StrongholdPiece.findCollisionPiece(pieces, box) == null ? new PortalRoom(pieceId, rand, box, facing) : null;
    }

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {
        if (start != null) {
            start.portalRoom = this;
        }
        //generateEyeStates(gen.worldSeed, gen.chunkRngTracker, gen.version);
    }

    @Override
    public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

    }

    public BoundingBox getEndFrameBB() {
        Vec3i mins = this.applyVecTransform(new Vec3i(3,3,8));
        Vec3i maxes = this.applyVecTransform(new Vec3i(7,3,12));
        return new BoundingBox(mins,maxes);
    }

    private List<BlockPos> calculateFramePositions() {
        List<BlockPos> positions = new ArrayList<>(12);

        switch (this.getFacing()) {
            case NORTH:
                positions.add(transformPosition(4, 8));
                positions.add(transformPosition(5, 8));
                positions.add(transformPosition(6, 8));
                positions.add(transformPosition(4, 12));
                positions.add(transformPosition(5, 12));
                positions.add(transformPosition(6, 12));
                positions.add(transformPosition(3, 9));
                positions.add(transformPosition(3, 10));
                positions.add(transformPosition(3, 11));
                positions.add(transformPosition(7, 9));
                positions.add(transformPosition(7, 10));
                positions.add(transformPosition(7, 11));
                break;
            case SOUTH:
                positions.add(transformPosition(6, 8));
                positions.add(transformPosition(5, 8));
                positions.add(transformPosition(4, 8));
                positions.add(transformPosition(6, 12));
                positions.add(transformPosition(5, 12));
                positions.add(transformPosition(4, 12));
                positions.add(transformPosition(3, 9));
                positions.add(transformPosition(3, 10));
                positions.add(transformPosition(3, 11));
                positions.add(transformPosition(7, 9));
                positions.add(transformPosition(7, 10));
                positions.add(transformPosition(7, 11));
                break;
            case WEST:
                positions.add(transformPosition(6, 8));
                positions.add(transformPosition(5, 8));
                positions.add(transformPosition(4, 8));
                positions.add(transformPosition(6, 12));
                positions.add(transformPosition(5, 12));
                positions.add(transformPosition(4, 12));
                positions.add(transformPosition(7, 9));
                positions.add(transformPosition(7, 10));
                positions.add(transformPosition(7, 11));
                positions.add(transformPosition(3, 9));
                positions.add(transformPosition(3, 10));
                positions.add(transformPosition(3, 11));
                break;
            case EAST:
                positions.add(transformPosition(4, 8));
                positions.add(transformPosition(5, 8));
                positions.add(transformPosition(6, 8));
                positions.add(transformPosition(4, 12));
                positions.add(transformPosition(5, 12));
                positions.add(transformPosition(6, 12));
                positions.add(transformPosition(3, 9));
                positions.add(transformPosition(3, 10));
                positions.add(transformPosition(3, 11));
                positions.add(transformPosition(7, 9));
                positions.add(transformPosition(7, 10));
                positions.add(transformPosition(7, 11));
                break;
        }

        return positions;
    }

    private BlockPos transformPosition(int x, int z) {
        int worldX = this.applyXTransform(x, z);
        int worldY = this.applyYTransform(3);
        int worldZ = this.applyZTransform(x, z);
        return new BlockPos(worldX, worldY, worldZ);
    }

//    private void generateEyeStates(long worldSeed, ChunkRngTracker tracker, MCVersion version) {
//        if (frames != null) return;
//        frames = new ArrayList<>(12);
//        ChunkRand rand = new ChunkRand();
//
//        List<BPos> framePositions = calculateFramePositions();
//
//        for (int frameId = 0; frameId < 12; frameId++) {
//            BPos framePos = framePositions.get(frameId);
//            CPos chunkPos = framePos.toChunkPos();
//
//            int rngCount = tracker.getRngCount(chunkPos);
//
//            rand.setPopulationSeed(worldSeed, chunkPos.getX(), chunkPos.getZ(), version);
//            rand.skip(rngCount + frameId);
//
//            boolean hasEye = rand.nextFloat() > 0.9F;
//            frames.add(new EndPortalFrameData(framePos, frameId, hasEye));
//        }
//    }

//    public void updateFramePositions() {
//        if (frames == null) return;
//
//        List<BPos> newPositions = calculateFramePositions();
//
//        for (int i = 0; i < frames.size() && i < newPositions.size(); i++) {
//            EndPortalFrameData oldFrame = frames.get(i);
//            BPos newPos = newPositions.get(i);
//            frames.set(i, new EndPortalFrameData(newPos, oldFrame.getFrameId(), oldFrame.hasEye()));
//        }
//    }
//
//    public List<EndPortalFrameData> getFrames() {
//        return frames;
//    }
//
//    public int getEyeCount() {
//        if (frames == null) return 0;
//        return (int) frames.stream().filter(EndPortalFrameData::hasEye).count();
//    }
}