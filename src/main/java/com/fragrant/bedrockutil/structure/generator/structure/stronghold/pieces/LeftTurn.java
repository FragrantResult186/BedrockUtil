package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public class LeftTurn extends StrongholdPiece {

	public LeftTurn(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) { //SHLeftTurn
		super(pieceId);
		this.facing = facing;
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
	}

	public static LeftTurn createPiece(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing, int pieceId) {
		BoundingBox box = BoundingBox.rotated(x, y, z, -1, -1, 0, 5, 5, 5, facing.getRotation());
		return StrongholdPiece.isHighEnough(box) && StrongholdPiece.findCollisionPiece(pieces, box) == null ? new LeftTurn(pieceId, rand, box, facing) : null;
	}

	@Override
	public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {
		Direction facing = this.getFacing();

		if(facing != Direction.NORTH && facing != Direction.EAST) {
			this.generateChildrenRight(gen, start, pieces, rand, 1, 1);
		} else {
			this.generateChildrenLeft(gen, start, pieces, rand, 1, 1);
		}
	}

	@Override
	public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

	}

}
