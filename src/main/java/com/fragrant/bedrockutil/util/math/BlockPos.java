package com.fragrant.bedrockutil.util.math;

@SuppressWarnings("unused")
public class BlockPos {

    public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);

    private final double x, y, z;

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPos(int x, int z) {
        this.x = x;
        this.y = 0;
        this.z = z;
    }

    public BlockPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPos(float x, float z) {
        this.x = x;
        this.y = 0;
        this.z = z;
    }

    public BlockPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPos(double x, double z) {
        this.x = x;
        this.y = 0;
        this.z = z;
    }

    public BlockPos(Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public int getX() {
        return (int) Math.floor(this.x);
    }

    public int getY() {
        return (int) Math.floor(this.y);
    }

    public int getZ() {
        return (int) Math.floor(this.z);
    }

    public double getXDouble() {
        return this.x;
    }

    public double getYDouble() {
        return this.y;
    }

    public double getZDouble() {
        return this.z;
    }

    public BlockPos add(BlockPos pos) {
        return this.add(pos.getXDouble(), pos.getYDouble(), pos.getZDouble());
    }

    public BlockPos subtract(BlockPos pos) {
        return this.subtract(pos.getXDouble(), pos.getYDouble(), pos.getZDouble());
    }

    public BlockPos add(int x, int y, int z) {
        return new BlockPos(this.x + x, this.y + y, this.z + z);
    }

    public BlockPos subtract(int x, int y, int z) {
        return new BlockPos(this.x - x, this.y - y, this.z - z);
    }

    public BlockPos add(double x, double y, double z) {
        return new BlockPos(this.x + x, this.y + y, this.z + z);
    }

    public BlockPos subtract(double x, double y, double z) {
        return new BlockPos(this.x - x, this.y - y, this.z - z);
    }

    public BlockPos multiply(double scalar) {
        return new BlockPos(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public BlockPos multiply(double x, double y, double z) {
        return new BlockPos(this.x * x, this.y * y, this.z * z);
    }

    public BlockPos divide(double scalar) {
        return new BlockPos(this.x / scalar, this.y / scalar, this.z / scalar);
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
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSqTo(BlockPos other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double manhattanDistanceTo(BlockPos other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y) + Math.abs(this.z - other.z);
    }

    public double getMagnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double getMagnitudeSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public BlockPos normalize() {
        double mag = this.getMagnitude();
        return mag == 0 ? ORIGIN : this.divide(mag);
    }

    public double dot(BlockPos other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public BlockPos cross(BlockPos other) {
        return new BlockPos(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    public BlockPos floor() {
        return new BlockPos(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
    }

    public BlockPos ceil() {
        return new BlockPos(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z));
    }

    public BlockPos round() {
        return new BlockPos(Math.round(this.x), Math.round(this.y), Math.round(this.z));
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
                this.x + direction.getVector().getX(),
                this.y + direction.getVector().getY(),
                this.z + direction.getVector().getZ()
        );
    }

    public BlockPos relative(Direction direction, int offset) {
        return offset == 0 ? this : new BlockPos(
                this.x + direction.getVector().getX() * offset,
                this.y + direction.getVector().getY() * offset,
                this.z + direction.getVector().getZ() * offset
        );
    }

    public BlockPos relative(Direction.Axis axis, int offset) {
        if (offset == 0) {
            return this;
        } else {
            int i = axis == Direction.Axis.X ? offset : 0;
            int j = axis == Direction.Axis.Y ? offset : 0;
            int k = axis == Direction.Axis.Z ? offset : 0;
            return new BlockPos(this.x + i, this.y + j, this.z + k);
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
            return "/tp" + " " + (int) this.x + " ~ " + (int) this.z;
        } else {
            return "/tp" + " " + (int) this.x + " " + (int) this.y + " " + (int) this.z;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockPos)) return false;
        BlockPos pos = (BlockPos) o;
        return Double.compare(pos.x, this.x) == 0 &&
                Double.compare(pos.y, this.y) == 0 &&
                Double.compare(pos.z, this.z) == 0;
    }

    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(this.x);
        long yBits = Double.doubleToLongBits(this.y);
        long zBits = Double.doubleToLongBits(this.z);
        return (int) (xBits ^ (xBits >>> 32) ^ yBits ^ (yBits >>> 32) ^ zBits ^ (zBits >>> 32));
    }

    @Override
    public String toString() {
        return "BlockPos{" + "x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}