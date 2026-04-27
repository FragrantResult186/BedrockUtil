package com.fragrant.bedrockutil.structure.generator;

import com.fragrant.bedrockutil.util.math.BoundingBox;
import com.fragrant.bedrockutil.util.math.BlockPos;
import com.fragrant.bedrockutil.util.math.Direction;
import com.fragrant.bedrockutil.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class StructurePiece<T extends StructurePiece<T>> {
    public int pieceId;
    public List<T> children = new ArrayList<>();

    public T previousPiece = null;
    public T nextPiece = null;

    public BoundingBox boundingBox;
    protected Direction facing;

    protected int applyXTransform(int x, int z) {
        if (this.facing == null) {
            return x;
        } else {
            switch (this.facing) {
                case NORTH:
                case SOUTH:
                    return this.boundingBox.getMinX() + x;
                case WEST:
                    return this.boundingBox.getMaxX() - z;
                case EAST:
                    return this.boundingBox.getMinX() + z;
                default:
                    return x;
            }
        }
    }

    public int applyYTransform(int y) {
        return this.getFacing() == null ? y : y + this.boundingBox.getMinY();
    }

    protected int applyZTransform(int x, int z) {
        if (this.facing == null) {
            return z;
        } else {
            switch (this.facing) {
                case NORTH:
                    return this.boundingBox.getMaxZ() - z;
                case SOUTH:
                    return this.boundingBox.getMinZ() + z;
                case WEST:
                case EAST:
                    return this.boundingBox.getMinZ() + x;
                default:
                    return z;
            }
        }
    }

    public Vec3i applyVecTransform(Vec3i vector) {
        int x = vector.getX();
        int y = vector.getY();
        int z = vector.getZ();
        return new Vec3i(applyXTransform(x, z), applyYTransform(y), applyZTransform(x, z));
    }

    public StructurePiece(int pieceId) {
        this.pieceId = pieceId;
    }

    public Direction getFacing() {
        return this.facing;
    }

    protected int getGenDepth() {
        return this.pieceId;
    }

    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public void setNextPiece(T next) {
        this.nextPiece = next;
        if (next != null) {
            next.previousPiece = (T) this;
        }
    }

    public void setChest() {

    }

    protected List<Vec3i> getChestOffsets() {
        return Collections.emptyList();
    }

    public List<Vec3i> getChestPositions() {
        if (this.boundingBox == null) return Collections.emptyList();
        List<Vec3i> offsets = this.getChestOffsets();
        if (offsets.isEmpty()) return Collections.emptyList();

        List<Vec3i> positions = new ArrayList<>(offsets.size());
        for (Vec3i offset : offsets) {
            positions.add(this.applyVecTransform(offset));
        }
        return positions;
    }

    public String toTP() {
        if (boundingBox == null) return "";
        return boundingBox.getCenter().toBPos().toTP();
    }
}