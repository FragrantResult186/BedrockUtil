package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.stronghold.StrongholdGenerator;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import java.util.List;

public class SmallCorridor extends StrongholdPiece {

    public SmallCorridor(int pieceId, ChunkRandom rand, BoundingBox boundingBox, Direction facing) { //SHFillerCorridor
		super(pieceId);
		this.facing = facing;
		this.boundingBox = boundingBox;
	}

	public static BoundingBox createBox(List<StrongholdPiece> pieces, ChunkRandom rand, int x, int y, int z, Direction facing) {
		BoundingBox box = BoundingBox.rotated(x, y, z, -1, -1, 0, 5, 5, 4, facing.getRotation());
		StrongholdPiece piece = StrongholdPiece.findCollisionPiece(pieces, box);

		if (piece != null && piece.getBoundingBox().getMinY() == box.getMinY()) {
			for (int int_5 = 3; int_5 >= 1; --int_5) {
				box = BoundingBox.rotated(x, y, z, -1, -1, 0, 5, 5, int_5 - 1, facing.getRotation());
				if (!piece.getBoundingBox().intersects(box)) {
					return BoundingBox.rotated(x, y, z, -1, -1, 0, 5, 5, int_5, facing.getRotation());
				}
			}
		}

		return null;
	}

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<StrongholdPiece> pieces, ChunkRandom rand) {

    }

	@Override
	public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

	}

}
