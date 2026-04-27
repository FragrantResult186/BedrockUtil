package com.fragrant.bedrockutil.util.math;

@SuppressWarnings("unused")
public class RegionPos extends Vec3i {

	private final int regionSize;

	public RegionPos(int x, int z, int regionSize) {
		super(x, 0, z);
		this.regionSize = regionSize;
	}

	public RegionPos add(ChunkPos pos) {
		return this.add(pos.getX(), pos.getZ());
	}

	public RegionPos subtract(ChunkPos pos) {
		return this.subtract(pos.getX(), pos.getZ());
	}

	public RegionPos shl(int amount) {
		return this.shl(amount, amount);
	}

	public RegionPos shr(int amount) {
		return this.shr(amount, amount);
	}

	public RegionPos add(int x, int z) {
		return new RegionPos(this.getX() + x, this.getZ() + z, this.regionSize);
	}

	public RegionPos subtract(int x, int z) {
		return new RegionPos(this.getX() - x, this.getZ() - z, this.regionSize);
	}

	public RegionPos shl(int bx, int bz) {
		return new RegionPos(this.getX() << bx, this.getZ() << bz, this.regionSize);
	}

	public RegionPos shr(int bx, int bz) {
		return new RegionPos(this.getX() >> bx, this.getZ() >> bz, this.regionSize);
	}

	/**
	 * Recalculate the region based on the new size
	 *
	 * @param regionSize the size to calulcate the new base in
	 * @return a new RegionPos
	 */
	public RegionPos changeRegionSize(int regionSize) {
		int x = this.getX() * this.regionSize;
		x = x < 0 ? x - regionSize + 1 : x;
		int z = this.getZ() * this.regionSize;
		z = z < 0 ? z - regionSize + 1 : z;
		return new RegionPos(x / regionSize, z / regionSize, regionSize);
	}

	public int getRegionSize() {
		return this.regionSize;
	}

	public BlockPos toBlockPos() {
		return new BlockPos(this.getX() * this.getRegionSize(), 0, this.getZ() * this.getRegionSize());
	}

	public ChunkPos toChunkPos() {
		return new ChunkPos(this.getX() * this.getRegionSize(), this.getZ() * this.getRegionSize());
	}

}
