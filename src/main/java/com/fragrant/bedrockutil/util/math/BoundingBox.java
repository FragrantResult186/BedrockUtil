package com.fragrant.bedrockutil.util.math;

import java.util.Objects;

@SuppressWarnings("unused")
public class BoundingBox {

	private int minX;
	private int minY;
	private int minZ;
	private int maxX;
	private int maxY;
	private int maxZ;

	public BoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public BoundingBox(int xMin, int zMin, int xMax, int zMax) {
		this.minX = xMin;
		this.minZ = zMin;
		this.maxX = xMax;
		this.maxZ = zMax;
		this.minY = 1;
		this.maxY = 512;
	}

	public BoundingBox(Vec3i v1, Vec3i v2) {
		this.minX = Math.min(v1.getX(), v2.getX());
		this.minY = Math.min(v1.getY(), v2.getY());
		this.minZ = Math.min(v1.getZ(), v2.getZ());
		this.maxX = Math.max(v1.getX(), v2.getX());
		this.maxY = Math.max(v1.getY(), v2.getY());
		this.maxZ = Math.max(v1.getZ(), v2.getZ());
	}

	public static BoundingBox empty() {
		return new BoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMinZ() {
		return minZ;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMaxZ() {
		return maxZ;
	}

	public static BoundingBox createOrientedBox(
			int x, int y, int z,
			int offsetX, int offsetY, int offsetZ,
			int sizeX, int sizeY, int sizeZ,
			Direction dir
	) {
		return switch (dir) {
			case NORTH -> new BoundingBox(
					x + offsetX,
					y + offsetY,
					z - sizeZ + 1 + offsetZ,
					x + sizeX - 1 + offsetX,
					y + sizeY - 1 + offsetY,
					z + offsetZ
			);
			case SOUTH -> new BoundingBox(
					x + offsetX,
					y + offsetY,
					z + offsetZ,
					x + sizeX - 1 + offsetX,
					y + sizeY - 1 + offsetY,
					z + sizeZ - 1 + offsetZ
			);
			case WEST -> new BoundingBox(
					x - sizeZ + 1 + offsetZ,
					y + offsetY,
					z + offsetX,
					x + offsetZ,
					y + sizeY - 1 + offsetY,
					z + sizeX - 1 + offsetX
			);
			case EAST -> new BoundingBox(
					x + offsetZ,
					y + offsetY,
					z + offsetX,
					x + sizeZ - 1 + offsetZ,
					y + sizeY - 1 + offsetY,
					z + sizeX - 1 + offsetX
			);
			default -> throw new IllegalArgumentException("Invalid direction: " + dir);
		};
	}

	public static BoundingBox rotated(int x, int y, int z, int offsetX, int offsetY, int offsetZ, int sizeX, int sizeY, int sizeZ, Rotation rotation) {
		switch (rotation) {
			case COUNTERCLOCKWISE_90: // WEST
				return new BoundingBox(x - sizeZ + 1 + offsetZ, y + offsetY, z + offsetX, x + offsetZ, y + sizeY - 1 + offsetY, z + sizeX - 1 + offsetX);
			case CLOCKWISE_90: // EAST
				return new BoundingBox(x + offsetZ, y + offsetY, z + offsetX, x + sizeZ - 1 + offsetZ, y + sizeY - 1 + offsetY, z + sizeX - 1 + offsetX);
			case CLOCKWISE_180: // SOUTH
				return new BoundingBox(x + offsetX, y + offsetY, z + offsetZ, x + sizeX - 1 + offsetX, y + sizeY - 1 + offsetY, z + sizeZ - 1 + offsetZ);
			case NONE: // NORTH
				return new BoundingBox(x + offsetX, y + offsetY, z - sizeZ + 1 + offsetZ, x + sizeX - 1 + offsetX, y + sizeY - 1 + offsetY, z + offsetZ);
		}
		return null;
	}

	public static BoundingBox rotated(int x, int y, int z, int offsetX, int offsetY, int offsetZ, int sizeX, int sizeY, int sizeZ, Direction rotation) {
		switch (rotation) {
			case WEST: // COUNTERCLOCKWISE_90
				return new BoundingBox(x - sizeZ + 1 + offsetZ, y + offsetY, z + offsetX, x + offsetZ, y + sizeY - 1 + offsetY, z + sizeX - 1 + offsetX);
			case EAST: // CLOCKWISE_90
				return new BoundingBox(x + offsetZ, y + offsetY, z + offsetX, x + sizeZ - 1 + offsetZ, y + sizeY - 1 + offsetY, z + sizeX - 1 + offsetX);
			case SOUTH: // CLOCKWISE_180
				return new BoundingBox(x + offsetX, y + offsetY, z + offsetZ, x + sizeX - 1 + offsetX, y + sizeY - 1 + offsetY, z + sizeZ - 1 + offsetZ);
			case NORTH: // NONE
				return new BoundingBox(x + offsetX, y + offsetY, z - sizeZ + 1 + offsetZ, x + sizeX - 1 + offsetX, y + sizeY - 1 + offsetY, z + offsetZ);
		}
		return null;
	}

	public static BoundingBox getBoundingBox(BlockPos anchor, Rotation rotation, BlockPos pivot, Mirror mirror, BlockPos size) {
		BlockPos rotationSize = rotation.getSize(size);
		int pivotX = pivot.getX();
		int pivotZ = pivot.getZ();
		int sizedRotationX = rotationSize.getX() - 1;
		int sizedRotationY = rotationSize.getY() - 1;
		int sizedRotationZ = rotationSize.getZ() - 1;
		BoundingBox boundingBox = new BoundingBox(0, 0, 0, 0, 0, 0);
		switch (rotation) {
			case COUNTERCLOCKWISE_90:
				boundingBox = new BoundingBox(pivotX - pivotZ, 0, pivotX + pivotZ - sizedRotationZ, pivotX - pivotZ + sizedRotationX, sizedRotationY, pivotX + pivotZ);
				break;
			case CLOCKWISE_90:
				boundingBox = new BoundingBox(pivotX + pivotZ - sizedRotationX, 0, pivotZ - pivotX, pivotX + pivotZ, sizedRotationY, pivotZ - pivotX + sizedRotationZ);
				break;
			case CLOCKWISE_180:
				boundingBox = new BoundingBox(pivotX + pivotX - sizedRotationX, 0, pivotZ + pivotZ - sizedRotationZ, pivotX + pivotX, sizedRotationY, pivotZ + pivotZ);
				break;
			case NONE:
				boundingBox = new BoundingBox(0, 0, 0, sizedRotationX, sizedRotationY, sizedRotationZ);
		}

		switch (mirror) {
			case LEFT_RIGHT:
				boundingBox = mirrorAABB(rotation, sizedRotationZ, sizedRotationX, boundingBox, Direction.NORTH, Direction.SOUTH);
				break;
			case FRONT_BACK:
				boundingBox = mirrorAABB(rotation, sizedRotationX, sizedRotationZ, boundingBox, Direction.WEST, Direction.EAST);
			case NONE:
		}
		return boundingBox.offset(anchor.getX(), anchor.getY(), anchor.getZ());
	}

	private static BoundingBox mirrorAABB(Rotation rotation, int x, int z, BoundingBox boundingBox, Direction direction, Direction direction1) {
		BlockPos moveAmount = BlockPos.ORIGIN;
		if (rotation != Rotation.CLOCKWISE_90 && rotation != Rotation.COUNTERCLOCKWISE_90) {
			if (rotation == Rotation.CLOCKWISE_180) {
				moveAmount = moveAmount.relative(direction1, x);
			} else {
				moveAmount = moveAmount.relative(direction, x);
			}
		} else {
			moveAmount = moveAmount.relative(rotation.rotate(direction), z);
		}

		return boundingBox.offset(moveAmount.getX(), 0, moveAmount.getZ());
	}

	public BoundingBox offset(int x, int y, int z) {
		return new BoundingBox(
				this.minX + x, this.minY + y, this.minZ + z,
				this.maxX + x, this.maxY + y, this.maxZ + z
		);
	}

	public void move(int x, int y, int z) {
		this.minX += x;
		this.minY += y;
		this.minZ += z;
		this.maxX += x;
		this.maxY += y;
		this.maxZ += z;
	}

	public BlockPos getInside(BlockPos offset, Rotation rotation) {
		switch (rotation) {
			case NONE:
				return new BlockPos(this.minX + offset.getX(), this.minY + offset.getY(), this.minZ + offset.getZ());
			case CLOCKWISE_90:
				return new BlockPos(this.minX - offset.getZ(), this.minY + offset.getY(), this.minZ + offset.getX());
			case CLOCKWISE_180:
				return new BlockPos(this.minX - offset.getX(), this.minY + offset.getY(), this.minZ - offset.getZ());
			case COUNTERCLOCKWISE_90:
				return new BlockPos(this.minX + offset.getZ(), this.minY + offset.getY(), this.minZ - offset.getX());
		}
		return null;
	}

	public BoundingBox getRotated(Rotation rotation) {
		switch (rotation) {
			case COUNTERCLOCKWISE_90: // WEST
				return new BoundingBox(this.minX, this.minY, this.maxZ, this.maxX, this.maxY, this.minZ);
			case CLOCKWISE_90: // EAST
				return new BoundingBox(this.maxX, this.minY, this.minZ, this.minX, this.maxY, this.maxZ);
			case CLOCKWISE_180: // SOUTH
				return new BoundingBox(this.maxX, this.minY, this.maxZ, this.minX, this.maxY, this.minZ);
			case NONE: // NORTH
				return this;
		}
		return null;
	}

	public boolean intersects(BoundingBox box) {
		return this.maxX >= box.minX && this.minX <= box.maxX && this.maxZ >= box.minZ && this.minZ <= box.maxZ && this.maxY >= box.minY && this.minY <= box.maxY;
	}

	public boolean intersectsXZ(int minX, int minZ, int maxX, int maxZ) {
		return this.maxX >= minX && this.minX <= maxX && this.maxZ >= minZ && this.minZ <= maxZ;
	}

	public boolean contains(Vec3i v) {
		return v.getX() >= this.minX && v.getX() <= this.maxX && v.getZ() >= this.minZ && v.getZ() <= this.maxZ && v.getY() >= this.minY && v.getY() <= this.maxY;
	}

	public Vec3i getDimensions() {
		return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
	}

	public void encompass(BoundingBox box) {
		this.minX = Math.min(this.minX, box.minX);
		this.minY = Math.min(this.minY, box.minY);
		this.minZ = Math.min(this.minZ, box.minZ);
		this.maxX = Math.max(this.maxX, box.maxX);
		this.maxY = Math.max(this.maxY, box.maxY);
		this.maxZ = Math.max(this.maxZ, box.maxZ);
	}

	public int getXSpan() {
		return this.maxX - this.minX + 1;
	}

	public int getYSpan() {
		return this.maxY - this.minY + 1;
	}

	public int getZSpan() {
		return this.maxZ - this.minZ + 1;
	}

	public Vec3i getCenter() {
		return new Vec3i(this.minX + this.getXSpan() / 2, this.minY + this.getYSpan() / 2, this.minZ + this.getZSpan() / 2);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BoundingBox)) return false;
		BoundingBox boundingBox = (BoundingBox) o;
		return minX == boundingBox.minX && minY == boundingBox.minY && minZ == boundingBox.minZ && maxX == boundingBox.maxX && maxY == boundingBox.maxY && maxZ == boundingBox.maxZ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public String toString() {
		return "BoundingBox{" +
				"minX=" + minX +
				", minY=" + minY +
				", minZ=" + minZ +
				", maxX=" + maxX +
				", maxY=" + maxY +
				", maxZ=" + maxZ +
				'}';
	}
}
