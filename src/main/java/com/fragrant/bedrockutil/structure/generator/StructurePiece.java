package com.fragrant.bedrockutil.structure.generator;

import com.fragrant.bedrockutil.util.math.BoundingBox;
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
    public List<Vec3i> chests = new ArrayList<>();

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

    protected void setChest(Vec3i offsetPos) {
        this.chests.add(applyVecTransform(offsetPos));
    }

    protected List<Vec3i> getChestPos() {
        return Collections.emptyList();
    }

    public String toTP() {
        if (this.boundingBox == null) return "";
        return this.boundingBox.getCenter().toBPos().toTP();
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "%s %s".formatted(getName(), getBoundingBox());
    }
}