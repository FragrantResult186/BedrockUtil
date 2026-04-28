package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public class FiveWayCrossing extends StrongholdPiece {

	private final boolean lowerLeftExists;
	private final boolean upperLeftExists;
	private final boolean lowerRightExists;
	private final boolean upperRightExists;

	public FiveWayCrossing(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) { //Called SHFiveWayCrossing in bedrock
		super(pieceId);
		this.facing = facing;
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
		this.lowerLeftExists = rand.nextBoolean();
		this.upperLeftExists = rand.nextBoolean();
		this.lowerRightExists = rand.nextBoolean();
		this.upperRightExists = rand.nextInt(3) > 0;
	}

	public static FiveWayCrossing createPiece(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing, int pieceId) {
		BoundingBox box = BoundingBox.rotated(x, y, z, -4, -3, 0, 10, 9, 11, facing.getRotation());
		return StrongholdPiece.isHighEnough(box) && StrongholdPiece.findCollisionPiece(pieces, box) == null ? new FiveWayCrossing(pieceId, rand, box, facing) : null;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {
		int a = 3;
		int b = 5;
		Direction facing = this.getFacing();
		if (facing == Direction.WEST || facing == Direction.NORTH) {
			a = 8 - a;
			b = 8 - b;
		}
		this.generateChildrenForward(gen, start, pieces, rand, 5, 1);
		if (this.lowerLeftExists) {
			this.generateChildrenLeft(gen, start, pieces, rand, a, 1);
		}
		if (this.upperLeftExists) {
			this.generateChildrenLeft(gen, start, pieces, rand, b, 7);
		}
		if (this.lowerRightExists) {
			this.generateChildrenRight(gen, start, pieces, rand, a, 1);
		}
		if (this.upperRightExists) {
			this.generateChildrenRight(gen, start, pieces, rand, b, 7);
		}
	}

	@Override
	public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

	}

}
