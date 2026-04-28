package com.fragrant.bedrockutil.util.math;

@SuppressWarnings("unused")
public class BlockPos extends Vec3i {

    public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);

    public BlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockPos(int x, int z) {
        super(x, 0, z);
    }

    public BlockPos(Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public BlockPos add(BlockPos pos) {
        return this.add(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockPos subtract(BlockPos pos) {
        return this.subtract(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockPos add(int x, int y, int z) {
        return new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    public BlockPos subtract(int x, int y, int z) {
        return new BlockPos(this.getX() - x, this.getY() - y, this.getZ() - z);
    }

    public BlockPos divide(double scalar) {
        return new BlockPos((int) (this.getX() / scalar), (int) (this.getY() / scalar), (int) (this.getZ() / scalar));
    }

    public BlockPos shl(int amount) {
        return this.shl(amount, amount, amount);
    }

    public BlockPos shr(int amount) {
        return this.shr(amount, amount, amount);
    }

    public BlockPos shl(int bx, int by, int bz) {
        return new BlockPos(this.getX() << bx, this.getY() << by, this.getZ() << bz);
    }

    public BlockPos shr(int bx, int by, int bz) {
        return new BlockPos(this.getX() >> bx, this.getY() >> by, this.getZ() >> bz);
    }

    public double distanceTo(BlockPos other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSqTo(BlockPos other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    public double manhattanDistanceTo(BlockPos other) {
        return Math.abs(this.getX() - other.getX()) + Math.abs(this.getY() - other.getY()) + Math.abs(this.getZ() - other.getZ());
    }

    public double getMagnitude() {
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
    }

    public double getMagnitudeSq() {
        return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ();
    }

    public BlockPos normalize() {
        double mag = this.getMagnitude();
        return mag == 0 ? ORIGIN : this.divide(mag);
    }

    public double dot(BlockPos other) {
        return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
    }

    public BlockPos cross(BlockPos other) {
        return new BlockPos(
                this.getY() * other.getZ() - this.getZ() * other.getY(),
                this.getZ() * other.getX() - this.getX() * other.getZ(),
                this.getX() * other.getY() - this.getY() * other.getX()
        );
    }

    public BlockPos floor() {
        return new BlockPos((int) (double) this.getX(), (int) (double) this.getY(), (int) (double) this.getZ());
    }

    public BlockPos ceil() {
        return new BlockPos((int) (double) this.getX(), (int) (double) this.getY(), (int) (double) this.getZ());
    }

    public BlockPos round() {
        return new BlockPos(Math.round(this.getX()), Math.round(this.getY()), Math.round(this.getZ()));
    }

    public BlockPos toChunkCorner() {
        return new BlockPos(this.getX() & -16, this.getY(), this.getZ() & -16);
    }

    public ChunkPos toChunkPos() {
        return new ChunkPos(this.getX() >> 4, this.getZ() >> 4);
    }

    public BlockPos toNetherPos() {
        return new BlockPos(this.getX() / 8, this.getZ() / 8);
    }

    public BlockPos relative(Direction direction) {
        return new BlockPos(
                this.getX() + direction.getVector().getX(),
                this.getY() + direction.getVector().getY(),
                this.getZ() + direction.getVector().getZ()
        );
    }

    public BlockPos relative(Direction direction, int offset) {
        return offset == 0 ? this : new BlockPos(
                this.getX() + direction.getVector().getX() * offset,
                this.getY() + direction.getVector().getY() * offset,
                this.getZ() + direction.getVector().getZ() * offset
        );
    }

    public BlockPos relative(Direction.Axis axis, int offset) {
        if (offset == 0) {
            return this;
        } else {
            int i = axis == Direction.Axis.X ? offset : 0;
            int j = axis == Direction.Axis.Y ? offset : 0;
            int k = axis == Direction.Axis.Z ? offset : 0;
            return new BlockPos(this.getX() + i, this.getY() + j, this.getZ() + k);
        }
    }

    public RegionPos toRegionPos(int regionSize) {
        int x = this.getX() < 0 ? this.getX() - regionSize + 1 : this.getX();
        int z = this.getZ() < 0 ? this.getZ() - regionSize + 1 : this.getZ();
        return new RegionPos(x / regionSize, z / regionSize, regionSize);
    }

    public BlockPos transform(Mirror mirror, Rotation rotation, BlockPos pivot) {
        return rotation.rotate(mirror.mirror(this), pivot);
    }

    public Vec3i toVec3i() {
        return new Vec3i(this.getX(), this.getY(), this.getZ());
    }

    public String toTP() {
        return toTP(false);
    }

    public String toTP(boolean relativeY) {
        if (relativeY) {
            return "/tp" + " " + this.getX() + " ~ " + this.getZ();
        } else {
            return "/tp" + " " + this.getX() + " " + this.getY() + " " + this.getZ();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockPos)) return false;
        BlockPos pos = (BlockPos) o;
        return Double.compare(pos.getX(), this.getX()) == 0 &&
                Double.compare(pos.getY(), this.getY()) == 0 &&
                Double.compare(pos.getZ(), this.getZ()) == 0;
    }

    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(this.getX());
        long yBits = Double.doubleToLongBits(this.getY());
        long zBits = Double.doubleToLongBits(this.getZ());
        return (int) (xBits ^ (xBits >>> 32) ^ yBits ^ (yBits >>> 32) ^ zBits ^ (zBits >>> 32));
    }

    @Override
    public String toString() {
        return "BlockPos{" + "x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + '}';
    }
}