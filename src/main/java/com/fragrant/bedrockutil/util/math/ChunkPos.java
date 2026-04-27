package com.fragrant.bedrockutil.util.math;

@SuppressWarnings("unused")
public class ChunkPos extends Vec3i {

	public ChunkPos(int x, int z) {
		super(x, 0, z);
	}

	public ChunkPos(Vec3i vec3i) {
		super(vec3i.getX(), vec3i.getY(), vec3i.getZ());
	}

	public ChunkPos add(ChunkPos pos) {
		return this.add(pos.getX(), pos.getZ());
	}

	public ChunkPos subtract(ChunkPos pos) {
		return this.subtract(pos.getX(), pos.getZ());
	}

	public ChunkPos shl(int amount) {
		return this.shl(amount, amount);
	}

	public ChunkPos shr(int amount) {
		return this.shr(amount, amount);
	}

	public ChunkPos add(int x, int z) {
		return new ChunkPos(this.getX() + x, this.getZ() + z);
	}

	public ChunkPos subtract(int x, int z) {
		return new ChunkPos(this.getX() - x, this.getZ() - z);
	}

	public ChunkPos shl(int bx, int bz) {
		return new ChunkPos(this.getX() << bx, this.getZ() << bz);
	}

	public ChunkPos shr(int bx, int bz) {
		return new ChunkPos(this.getX() >> bx, this.getZ() >> bz);
	}

	public BlockPos toBlockPos() {
		return this.toBlockPos(0);
	}

	public BlockPos toBlockPos(int y) {
		return new BlockPos(this.getX() << 4, y, this.getZ() << 4);
	}

	public RegionPos toRegionPos(int regionSize) {
		int x = this.getX() < 0 ? this.getX() - regionSize + 1 : this.getX();
		int z = this.getZ() < 0 ? this.getZ() - regionSize + 1 : this.getZ();
		return new RegionPos(x / regionSize, z / regionSize, regionSize);
	}

    public ChunkPos toNetherPos() {
        return new ChunkPos(this.getX() >> 3, this.getZ() >> 3);
    }

    public ChunkPos worldPos() {
        return new ChunkPos(this.getX() << 3, this.getZ() << 3);
    }

    public boolean isInChunk(int minX, int minZ, int maxX, int maxZ) {
        return this.getX() >= minX && this.getX() <= maxX && this.getZ() >= minZ && this.getZ() <= maxZ;
    }

    /**
	 * Interface to create a ChunkPos from (blockX,y,centerZ) by discarding y
	 * call ChunkPos.Builder::create
	 */
	@FunctionalInterface
	public interface Builder {
		ChunkPos create(int x, int z);

		static ChunkPos create(int x, int y, int z) {
			return new ChunkPos(x, z);
		}
	}

}

