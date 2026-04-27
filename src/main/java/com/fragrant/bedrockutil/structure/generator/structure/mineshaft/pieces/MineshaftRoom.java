package com.fragrant.bedrockutil.structure.generator.structure.mineshaft.pieces;

import com.fragrant.bedrockutil.structure.generator.structure.mineshaft.*;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.BedrockRandom;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;

public class MineshaftRoom extends MineshaftPiece {

    public MineshaftRoom(int pieceId, ChunkRandom random, int x, int y, int z) {
        super(pieceId);
        /* Z->Y->X in Bedrock */
        int maxZ = random.nextInt(6) + z + 7;
        int maxY = random.nextInt(6) + y + 4;
        int maxX = random.nextInt(6) + x + 7;
        this.boundingBox = new BoundingBox(x, y, z, maxX, maxY, maxZ);
    }

    @Override
    public void addChildren(MineshaftGenerator gen, ChunkRandom random) {
        //mesa mineshaft has a 50% chance of stopping generation in Bedrock
        if (random.nextFloat() > gen.getType().getGenerationProbability()) {
            return;
        }
        int genDepth = this.getGenDepth();
        BoundingBox box = this.getBoundingBox();

        int xAxisLen = box.getXSpan();
        int yAxisLen = box.getYSpan();
        int zAxisLen = box.getZSpan();

        int k = Math.max(1, yAxisLen - 3 - 1);
        generateMineshaftSides(gen, random, xAxisLen, k, Direction.NORTH, genDepth);
        generateMineshaftSides(gen, random, xAxisLen, k, Direction.SOUTH, genDepth);
        generateMineshaftSides(gen, random, zAxisLen, k, Direction.WEST, genDepth);
        generateMineshaftSides(gen, random, zAxisLen, k, Direction.EAST, genDepth);
    }

    private void generateMineshaftSides(MineshaftGenerator gen, ChunkRandom random,
                                        int axisLen, int k, Direction facing, int genDepth) {
        BoundingBox box = this.getBoundingBox();

        int minX = box.getMinX();
        int minY = box.getMinY();
        int minZ = box.getMinZ();
        int maxX = box.getMaxX();
        int maxY = box.getMaxY();
        int maxZ = box.getMaxZ();

        int j = 0;
        while (j < axisLen) {
            j += random.nextInt(axisLen);
            if (j + 3 > axisLen) break;

            int x = 0, z = 0;
            switch (facing) {
                case NORTH -> { x = minX + j; z = minZ - 1; }
                case SOUTH -> { x = minX + j; z = maxZ + 1; }
                case WEST  -> { x = minX - 1; z = minZ + j; }
                case EAST  -> { x = maxX + 1; z = minZ + j; }
            }

            int y = minY + random.nextInt(k) + 1;
            gen.generateAndAddPiece(gen.pieces, random, x, y, z, facing, genDepth);
            j += 4;
        }
    }

    @Override
    public void postProcess(BoundingBox chunkBox, int chunkX, int chunkZ, BedrockRandom random) {

    }
}