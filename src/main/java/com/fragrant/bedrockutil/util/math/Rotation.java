package com.fragrant.bedrockutil.util.math;

import com.fragrant.bedrockutil.util.rand.BedrockRandom;

@SuppressWarnings("unused")
public enum Rotation {

    NONE(Direction.NORTH),
    CLOCKWISE_90(Direction.EAST),
    CLOCKWISE_180(Direction.SOUTH),
    COUNTERCLOCKWISE_90(Direction.WEST);

    private final Direction direction;

    Rotation(Direction direction) {
        this.direction = direction;
    }

    public static Rotation getRandom(BedrockRandom rand) {
        return values()[rand.nextInt(values().length)];
    }

    public Rotation rotateBy(int steps) {
        return Rotation.values()[(this.ordinal() + steps) & 3];
    }

    public Rotation getOpposite() {
        return this.rotateBy(2);
    }

    public Direction getDirection() {
        return direction;
    }

    public Rotation getRotated(Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180:
                return this.direction.getOpposite().getRotation();
            case COUNTERCLOCKWISE_90:
                return this.direction.getCounterClockWise().getRotation();
            case CLOCKWISE_90:
                return this.direction.getClockWise().getRotation();
            default:
                return this;
        }
    }

    public Direction rotate(Direction direction) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return direction;
        } else {
            switch (this) {
                case CLOCKWISE_90:
                    return direction.getClockWise();
                case CLOCKWISE_180:
                    return direction.getOpposite();
                case COUNTERCLOCKWISE_90:
                    return direction.getCounterClockWise();
                default:
                    return direction;
            }
        }
    }

    public BlockPos rotate(BlockPos origin, BlockPos pivot) {
        int px = pivot.getX();
        int pz = pivot.getZ();
        switch (this) {
            case CLOCKWISE_90:
                return new BlockPos(px + pz - origin.getZ(), origin.getY(), pz - px + origin.getX());
            case CLOCKWISE_180:
                return new BlockPos(px + px - origin.getX(), origin.getY(), pz + pz - origin.getZ());
            case COUNTERCLOCKWISE_90:
                return new BlockPos(px - pz + origin.getZ(), origin.getY(), px + pz - origin.getX());
            default:
                return origin;
        }
    }

    public BlockPos getSize(BlockPos size) {
        switch (this) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                return new BlockPos(size.getZ(), size.getY(), size.getX());
            default:
                return size;
        }
    }

    public int rotate(int anchor, int referent) {
        switch (this) {
            case CLOCKWISE_90:
                return (anchor + referent / 4) % referent;
            case CLOCKWISE_180:
                return (anchor + referent / 2) % referent;
            case COUNTERCLOCKWISE_90:
                return (anchor + referent * 3 / 4) % referent;
            default:
                return anchor;
        }
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "direction=" + direction +
                '}';
    }

}