package com.fragrant.bedrockutil.structure.util;

import com.fragrant.bedrockutil.structure.generator.StructurePiece;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

import java.util.List;

public class StructureBox {

    public static BoundingBox createBoundingBox(List<? extends StructurePiece<?>> pieces) {
        BoundingBox box = BoundingBox.empty();
        for (StructurePiece<?> piece : pieces) {
            box.encompass(piece.getBoundingBox());
        }
        return box;
    }

    public static void moveBelowSeaLevel(List<? extends StructurePiece<?>> pieces, BoundingBox box, ChunkRandom random,
                                         int seaLevel, int minWorldHeight, int offset) {
        int y = box.getYSpan() + minWorldHeight + 1;
        int diff = seaLevel - offset;
        if (y < diff) {
            y += random.nextInt(diff - y);
        }
        int dy = y - box.getMaxY();
        moveBoundingBoxes(pieces, dy);
    }

    public static void moveBoundingBoxes(List<? extends StructurePiece<?>> pieces, int dy) {
        for (StructurePiece<?> piece : pieces) {
            BoundingBox box = piece.getBoundingBox();
            if (box != null) {
                box.move(0, dy, 0);
            }
        }
    }

}