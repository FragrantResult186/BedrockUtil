package com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces;

import com.fragrant.bedrockutil.structure.generator.PieceWeight;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

public class Start extends SpiralStaircase { //Can only presume SHStart

	public PieceWeight<StrongholdPiece> pieceWeight;
	public PortalRoom portalRoom;

	public Start(ChunkRandom rand, int x, int z) {
		super(0, rand, x, z);
	}

}
