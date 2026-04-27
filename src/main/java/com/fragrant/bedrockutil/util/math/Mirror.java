package com.fragrant.bedrockutil.util.math;

@SuppressWarnings("unused")
public enum Mirror {

	NONE(new Vec3i(0, 0, 0)),
	LEFT_RIGHT(new Vec3i(0, 0, 1)),
	FRONT_BACK(new Vec3i(1, 0, 0));

	private final Vec3i orientation;

	Mirror(Vec3i orientation) {
		this.orientation = orientation;
	}

	public int mirror(int anchor, int referent) {
		int middlePoint = referent / 2;
		int offset = anchor > middlePoint ? anchor - referent : anchor;
		switch (this) {
			case FRONT_BACK:
				return (referent - offset) % referent;
			case LEFT_RIGHT:
				return (middlePoint - offset + referent) % referent;
			default:
				return anchor;
		}
	}

	public Rotation getRotation(Direction direction) {
        Direction.Axis axis = direction.getAxis();
		return (this != LEFT_RIGHT || axis != Direction.Axis.Z) && (this != FRONT_BACK || axis != Direction.Axis.X) ? Rotation.NONE : Rotation.CLOCKWISE_180;
	}

	public Direction mirror(Direction direction) {
		if (this == FRONT_BACK && direction.getAxis() == Direction.Axis.X) {
			return direction.getOpposite();
		} else {
			return this == LEFT_RIGHT && direction.getAxis() == Direction.Axis.Z ? direction.getOpposite() : direction;
		}
	}

	public BlockPos mirror(BlockPos pos) {
		switch (this) {
			case LEFT_RIGHT:
				return new BlockPos(pos.getX(), pos.getY(), -pos.getZ());
			case FRONT_BACK:
				return new BlockPos(-pos.getX(), pos.getY(), pos.getZ());
			default:
				return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
		}
	}

	public Vec3i getOrientation() {
		return orientation;
	}

	@Override
	public String toString() {
		return "Mirror{" +
				"rotation=" + orientation +
				'}';
	}
}
