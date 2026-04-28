package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import java.util.List;

public class SpiralStaircase extends StrongholdPiece { //SHStairsDown

	private final boolean isStructureStart;

	public SpiralStaircase(int pieceType, ChunkRandom rand, int x, int z) {
		super(pieceType);
		this.isStructureStart = true;
        this.facing = Direction.randomHorizontal(rand);
        this.boundingBox = new BoundingBox(x, 64, z, x + 5 - 1, 64 + 11 - 1, z + 5 - 1);
	}

	public SpiralStaircase(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) {
		super(pieceId);
		this.isStructureStart = false;
		this.facing = facing;
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
	}

	public static SpiralStaircase createPiece(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing, int pieceId) {
		BoundingBox box = BoundingBox.rotated(x, y, z, -1, -7, 0, 5, 11, 5, facing.getRotation());
		return StrongholdPiece.isHighEnough(box) && StrongholdPiece.findCollisionPiece(pieces, box) == null ? new SpiralStaircase(pieceId, rand, box, facing) : null;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {
		if (this.isStructureStart) {
            gen.currentPiece = FiveWayCrossing.class;
        }
		this.generateChildrenForward(gen, start, pieces, rand, 1, 1);
	}

	@Override
	public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

	}

}
