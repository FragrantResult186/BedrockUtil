package com.fragrant.bedrockutil.structure.generator.structure.stronghold;

import com.fragrant.bedrockutil.structure.generator.Generator;
import com.fragrant.bedrockutil.structure.generator.PieceWeight;
import com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces.*;
import com.fragrant.bedrockutil.structure.generator.structure.stronghold.pieces.StrongholdPiece;
import com.fragrant.bedrockutil.structure.util.StructureBox;
import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.rand.ChunkRandom;
import com.fragrant.bedrockutil.version.MCVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StrongholdGenerator extends Generator {

    private BoundingBox boundingBox;
    public final List<StrongholdPiece> pieces = new ArrayList<>();
    public boolean shouldGenerate;
    protected StrongholdPiece start;

    protected List<PieceWeight<StrongholdPiece>> pieceWeights = null;
    public Class<? extends StrongholdPiece> currentPiece = null;
    protected int totalWeight;

    private final List<PieceWeight<StrongholdPiece>> PIECE_WEIGHTS = Arrays.asList(
            new PieceWeight<>(Corridor.class, 40, 0),
            new PieceWeight<>(PrisonHall.class, 5, 5),
            new PieceWeight<>(LeftTurn.class, 20, 0),
            new PieceWeight<>(RightTurn.class, 20, 0),
            new PieceWeight<>(RoomCrossing.class, 10, 6),
            new PieceWeight<>(Stairs.class, 5, 5),
            new PieceWeight<>(SpiralStaircase.class, 5, 5),
            new PieceWeight<>(FiveWayCrossing.class, 5, 4),
            new PieceWeight<>(ChestCorridor.class, 5, 4),
            new PieceWeight<>(Library.class, 10, 2) {
                @Override
                public boolean canSpawnMoreStructuresOfType(int placedPieces) {
                    return super.canSpawnMoreStructuresOfType(placedPieces) && placedPieces > 4;
                }
            },
            new PieceWeight<>(PortalRoom.class, 10, 1) {
                @Override
                public boolean canSpawnMoreStructuresOfType(int placedPieces) {
                    return super.canSpawnMoreStructuresOfType(placedPieces) && placedPieces > 5;
                }
            }
    );

    public StrongholdGenerator(MCVersion version) {
        super(version);
    }

    @Override
    public boolean generate(long worldSeed, int chunkX, int chunkZ, ChunkRandom random) {
        this.shouldGenerate = true;
        generatePieces(worldSeed, chunkX, chunkZ, random);
        return this.shouldGenerate;
    }

    public void generatePieces(long worldSeed, int chunkX, int chunkZ, ChunkRandom random) {
        this.pieces.clear();
        this.totalWeight = 0;
        this.pieceWeights = new ArrayList<>(PIECE_WEIGHTS);

        random.setPopulationSeed(worldSeed, chunkX, chunkZ);
        random.advance(1); // burn a one call

        int x = (chunkX << 4) + 2;
        int z = (chunkZ << 4) + 2;
        Start startPiece = new Start(random, x, z);
        this.pieces.add(startPiece);
        startPiece.addChildren(this, startPiece, this.pieces, random);
        List<StrongholdPiece> pieces = startPiece.children;

        while (!pieces.isEmpty()) {
            int i = random.nextInt(pieces.size());
            StrongholdPiece piece = pieces.remove(i);
            piece.addChildren(this, startPiece, this.pieces, random);
        }

        int offset = this.version.isNewerOrEqualTo(MCVersion.v1_18_0) ? 10 : 5;
        int minWorldHeight = this.version.isNewerOrEqualTo(MCVersion.v1_18_0) ? -64 : 0;

        this.boundingBox = StructureBox.createBoundingBox(this.pieces);
        StructureBox.moveBelowSeaLevel(this.pieces, boundingBox, random, 63, minWorldHeight, offset);
    }

    public StrongholdPiece generateAndAddPiece(Start startPiece, List<StrongholdPiece> pieces, ChunkRandom rand,
                                               int x, int y, int z, Direction facing, int pieceId) {
        if (pieceId > 50) {
            return null;
        } else if (Math.abs(x - startPiece.getBoundingBox().getMinX()) <= 112 && Math.abs(z - startPiece.getBoundingBox().getMinZ()) <= 112) {
            ChunkRandom newRand = new ChunkRandom(rand);
            StrongholdPiece piece = this.getNextStructurePiece(startPiece, pieces, newRand, x, y, z, facing, pieceId + 1);
            if (piece != null) {
                pieces.add(piece);
                startPiece.children.add(piece);
            }
            return piece;
        } else {
            return null;
        }
    }

    private StrongholdPiece getNextStructurePiece(Start startPiece, List<StrongholdPiece> pieceList, ChunkRandom rand,
                                                  int x, int y, int z, Direction facing, int pieceId) {
        if (!this.canAddStructurePieces()) {
            return null;
        } else {
            if (this.currentPiece != null) {
                StrongholdPiece piece = classToPiece(this.currentPiece, pieceList, rand, x, y, z, facing, pieceId);
                this.currentPiece = null;

                if (piece != null) {
                    return piece;
                }
            }

            int int_5 = 0;

            while (int_5 < 5) {
                ++int_5;
                int int_6 = rand.nextInt(this.totalWeight);
                Iterator<PieceWeight<StrongholdPiece>> pieceWeightsIterator = this.pieceWeights.iterator();

                while (pieceWeightsIterator.hasNext()) {
                    PieceWeight<StrongholdPiece> pieceWeight = pieceWeightsIterator.next();
                    int_6 -= pieceWeight.pieceWeight;

                    if (int_6 < 0) {
                        if (!pieceWeight.canSpawnMoreStructuresOfType(pieceId) || pieceWeight == startPiece.pieceWeight) {
                            break;
                        }

                        StrongholdPiece piece = classToPiece(pieceWeight.pieceClass, pieceList, rand, x, y, z, facing, pieceId);

                        if (piece != null) {
                            ++pieceWeight.instancesSpawned;
                            startPiece.pieceWeight = pieceWeight;

                            if (!pieceWeight.canSpawnMoreStructures()) {
                                pieceWeightsIterator.remove();
                            }

                            return piece;
                        }
                    }
                }
            }

            BoundingBox boundingBox = SmallCorridor.createBox(pieceList, rand, x, y, z, facing);

            if (boundingBox != null && boundingBox.getMinY() > 0) {
                return new SmallCorridor(pieceId, rand, boundingBox, facing);
            } else {
                return null;
            }
        }
    }

    private boolean canAddStructurePieces() {
        boolean flag = false;
        this.totalWeight = 0;
        for (PieceWeight<StrongholdPiece> pieceWeight : this.pieceWeights) {
            if (pieceWeight.instancesLimit > 0 && pieceWeight.instancesSpawned < pieceWeight.instancesLimit) {
                flag = true;
            }
            totalWeight += pieceWeight.pieceWeight;
        }
        return flag;
    }

    private static StrongholdPiece classToPiece(Class<? extends StrongholdPiece> pieceClass,
                                                List<StrongholdPiece> pieceList, ChunkRandom rand,
                                                int x, int y, int z, Direction facing, int pieceId) {
        StrongholdPiece piece = null;

        if (pieceClass == Corridor.class) {
            piece = Corridor.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == PrisonHall.class) {
            piece = PrisonHall.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == LeftTurn.class) {
            piece = LeftTurn.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == RightTurn.class) {
            piece = RightTurn.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == RoomCrossing.class) {
            piece = RoomCrossing.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == Stairs.class) {
            piece = Stairs.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == SpiralStaircase.class) {
            piece = SpiralStaircase.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == FiveWayCrossing.class) {
            piece = FiveWayCrossing.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == ChestCorridor.class) {
            piece = ChestCorridor.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == Library.class) {
            piece = Library.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == PortalRoom.class) {
            piece = PortalRoom.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        }

        return piece;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}