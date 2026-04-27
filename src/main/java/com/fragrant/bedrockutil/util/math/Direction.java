package com.fragrant.bedrockutil.util.math;

import com.fragrant.bedrockutil.util.rand.IRandom;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public enum Direction {

    DOWN(Axis.Y, new Vec3i(0, -1, 0)),
    UP(Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(Axis.Z, new Vec3i(0, 0, -1)), // NONE
    SOUTH(Axis.Z, new Vec3i(0, 0, 1)),  // CLOCKWISE_180
    WEST(Axis.X, new Vec3i(-1, 0, 0)),  // COUNTERCLOCKWISE_90
    EAST(Axis.X, new Vec3i(1, 0, 0));   // CLOCKWISE_90

    private static final Direction[] HORIZONTALS = {SOUTH, WEST, NORTH, EAST};
    private static final Direction[] BY_2D_DATA = {NORTH, EAST, SOUTH, WEST};
    private static final Map<String, Direction> STRING_TO_BLOCK_DIRECTION =
            Arrays.stream(values()).collect(Collectors.toMap(Direction::name, o -> o));
    private static final Map<Integer, Direction> INTEGER_TO_BLOCK_DIRECTION =
            IntStream.range(0, values().length).boxed().collect(Collectors.toMap(i -> i, i -> values()[i]));

    private final Axis axis;
    private final Vec3i vec;

    Direction(Axis axis, Vec3i vec) {
        this.axis = axis;
        this.vec = vec;
    }

    public static Direction fromString(String name) {
        return STRING_TO_BLOCK_DIRECTION.get(name.toUpperCase());
    }

    public static Direction fromValue(int value) {
        return INTEGER_TO_BLOCK_DIRECTION.get(value);
    }

    public static Direction randomHorizontal(IRandom rand) {
        return HORIZONTALS[rand.nextInt(HORIZONTALS.length)];
    }

    public static Direction getRandom(IRandom rand) {
        return values()[rand.nextInt(values().length)];
    }

    public static Direction random2D(IRandom rand) {
        return BY_2D_DATA[rand.nextInt(BY_2D_DATA.length)];
    }

    public static Direction[] getHorizontal() {
        return HORIZONTALS;
    }

    public static Direction[] get2d() {
        return BY_2D_DATA;
    }

    public Direction getClockWise() {
        return getDirection(EAST, WEST, NORTH, SOUTH);
    }

    public Direction getCounterClockWise() {
        return getDirection(WEST, EAST, SOUTH, NORTH);
    }

    public Direction getOpposite() {
        return getDirection(SOUTH, NORTH, EAST, WEST);
    }

    private Direction getDirection(Direction dir1, Direction dir2, Direction dir3, Direction dir4) {
        switch (this) {
            case NORTH: return dir1;
            case SOUTH: return dir2;
            case WEST:  return dir3;
            case EAST:  return dir4;
            default: throw new IllegalStateException("Unable to get facing of " + this);
        }
    }

    public Axis getAxis() {
        return this.axis;
    }

    public Vec3i getVector() {
        return this.vec;
    }

    public Rotation getRotation() {
        switch (this) {
            case NORTH: return Rotation.NONE;
            case SOUTH: return Rotation.CLOCKWISE_180;
            case WEST:  return Rotation.COUNTERCLOCKWISE_90;
            case EAST:  return Rotation.CLOCKWISE_90;
            default: throw new IllegalStateException("Unable to get direction of " + this);
        }
    }

    @Override
    public String toString() {
        return "Direction{" +
                "axis=" + axis +
                ", vec=" + vec +
                '}';
    }

    public enum Axis {
        X, Y, Z;

        public Axis get2DRotated() {
            switch (this) {
                case X: return Z;
                case Z: return X;
                default: return Y;
            }
        }
    }
}
