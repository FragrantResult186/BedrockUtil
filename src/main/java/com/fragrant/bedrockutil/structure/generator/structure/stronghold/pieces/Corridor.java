package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public class Corridor extends StrongholdPiece { //Called SHStraight in bedrock

	private final boolean leftExitExists;
	private final boolean rightExitExists;

	public Corridor(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) {
		super(pieceId);
		this.facing = facing;
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
		this.leftExitExists = rand.nextInt(2) == 0;
		this.rightExitExists = rand.nextInt(2) == 0;
	}

	public static Corridor createPiece(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing, int pieceId) {
		BoundingBox box = BoundingBox.rotated(x, y, z, -1, -1, 0, 5, 5, 7, facing.getRotation());
		return StrongholdPiece.isHighEnough(box) && StrongholdPiece.findCollisionPiece(pieces, box) == null ? new Corridor(pieceId, rand, box, facing) : null;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {
		this.generateChildrenForward(gen, start, pieces, rand, 1, 1);
		if (this.leftExitExists) {
			this.generateChildrenLeft(gen, start, pieces, rand, 1, 2);
		}
		if (this.rightExitExists) {
			this.generateChildrenRight(gen, start, pieces, rand, 1, 2);
		}
	}

	@Override
	public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

	}

}
