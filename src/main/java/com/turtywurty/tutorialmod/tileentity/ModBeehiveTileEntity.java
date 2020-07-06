package com.turtywurty.tutorialmod.tileentity;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.turtywurty.tutorialmod.init.ModTileEntityTypes;
import com.turtywurty.tutorialmod.objects.blocks.ModBeehiveBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class ModBeehiveTileEntity extends TileEntity implements ITickableTileEntity {
	private final List<ModBeehiveTileEntity.Bee> bees = Lists.newArrayList();
	@Nullable
	private BlockPos flowerPos = null;

	public ModBeehiveTileEntity() {
		super(ModTileEntityTypes.MOD_BEEHIVE.get());
	}
	public void markDirty() {
		if (this.isNearFire()) {
			this.angerBees((PlayerEntity) null, this.world.getBlockState(this.getPos()),
					ModBeehiveTileEntity.State.EMERGENCY);
		}

		super.markDirty();
	}

	public boolean isNearFire() {
		if (this.world == null) {
			return false;
		} else {
			for (BlockPos blockpos : BlockPos.getAllInBoxMutable(this.pos.add(-1, -1, -1), this.pos.add(1, 1, 1))) {
				if (this.world.getBlockState(blockpos).getBlock() instanceof FireBlock) {
					return true;
				}
			}

			return false;
		}
	}

	public boolean hasNoBees() {
		return this.bees.isEmpty();
	}

	public boolean isFullOfBees() {
		return this.bees.size() == 3;
	}

	public void angerBees(@Nullable PlayerEntity playerIn, BlockState state,
			ModBeehiveTileEntity.State hiveState) {
		List<Entity> list = this.tryReleaseBee(state, hiveState);
		if (playerIn != null) {
			for (Entity entity : list) {
				if (entity instanceof BeeEntity) {
					BeeEntity beeentity = (BeeEntity) entity;
					if (playerIn.getPositionVec().squareDistanceTo(entity.getPositionVec()) <= 16.0D) {
						if (!this.isSmoked()) {
							beeentity.setBeeAttacker(playerIn);
						} else {
							beeentity.setStayOutOfHiveCountdown(400);
						}
					}
				}
			}
		}

	}

	private List<Entity> tryReleaseBee(BlockState state, ModBeehiveTileEntity.State hiveState) {
		List<Entity> list = Lists.newArrayList();
		this.bees.removeIf((p_226966_4_) -> {
			return this.releaseBee(state, p_226966_4_.entityData, list, hiveState);
		});
		return list;
	}

	public void tryEnterHive(Entity bee, boolean p_226961_2_) {
		this.tryEnterHive(bee, p_226961_2_, 0);
	}

	public int getBeeCount() {
		return this.bees.size();
	}

	public static int getHoneyLevel(BlockState state) {
		return state.get(ModBeehiveBlock.HONEY_LEVEL);
	}

	public boolean isSmoked() {
		return CampfireBlock.isLitCampfireInRange(this.world, this.getPos(), 5);
	}

	public void tryEnterHive(Entity bee, boolean p_226962_2_, int ticks) {
		if (this.bees.size() < 3) {
			bee.stopRiding();
			bee.removePassengers();
			CompoundNBT compoundnbt = new CompoundNBT();
			bee.writeUnlessPassenger(compoundnbt);
			this.bees.add(new ModBeehiveTileEntity.Bee(compoundnbt, ticks, p_226962_2_ ? 2400 : 600));
			if (this.world != null) {
				if (bee instanceof BeeEntity) {
					BeeEntity beeentity = (BeeEntity) bee;
					if (beeentity.hasFlower() && (!this.hasFlowerPos() || this.world.rand.nextBoolean())) {
						this.flowerPos = beeentity.getFlowerPos();
					}
				}

				BlockPos blockpos = this.getPos();
				this.world.playSound((PlayerEntity) null, (double) blockpos.getX(), (double) blockpos.getY(),
						(double) blockpos.getZ(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			bee.remove();
		}
	}

	private boolean releaseBee(BlockState state, CompoundNBT nbt, @Nullable List<Entity> bees,
			ModBeehiveTileEntity.State hiveState) {
		BlockPos blockpos = this.getPos();
		if ((this.world.isNightTime() || this.world.isRaining()) && hiveState != ModBeehiveTileEntity.State.EMERGENCY) {
			return false;
		} else {
			nbt.remove("Passengers");
			nbt.remove("Leash");
			nbt.removeUniqueId("UUID");
			Direction direction = state.get(ModBeehiveBlock.HORIZONTAL_FACING);
			BlockPos blockpos1 = blockpos.offset(direction);
			boolean flag = !this.world.getBlockState(blockpos1).getCollisionShape(this.world, blockpos1).isEmpty();
			if (flag && hiveState != ModBeehiveTileEntity.State.EMERGENCY) {
				return false;
			} else {
				Entity entity = EntityType.loadEntityAndExecute(nbt, this.world, (bee) -> bee);
				if (entity != null) {
					float width = entity.getWidth();
					double d0 = flag ? 0.0D : 0.55D + (double) (width / 2.0F);
					double x = (double) blockpos.getX() + 0.5D + d0 * (double) direction.getXOffset();
					double y = (double) blockpos.getY() + 0.5D - (double) (entity.getHeight() / 2.0F);
					double z = (double) blockpos.getZ() + 0.5D + d0 * (double) direction.getZOffset();
					entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
					if (!entity.getType().isContained(EntityTypeTags.BEEHIVE_INHABITORS)) {
						return false;
					} else {
						if (entity instanceof BeeEntity) {
							BeeEntity beeentity = (BeeEntity) entity;
							if (this.hasFlowerPos() && !beeentity.hasFlower() && this.world.rand.nextFloat() < 0.9F) {
								beeentity.setFlowerPos(this.flowerPos);
							}

							if (hiveState == ModBeehiveTileEntity.State.HONEY_DELIVERED) {
								beeentity.onHoneyDelivered();
								if (state.getBlock().isIn(BlockTags.BEEHIVES)) {
									int i = getHoneyLevel(state);
									if (i < 5) {
										int j = this.world.rand.nextInt(100) == 0 ? 2 : 1;
										if (i + j > 5) {
											--j;
										}

										this.world.setBlockState(this.getPos(),
												state.with(ModBeehiveBlock.HONEY_LEVEL, Integer.valueOf(i + j)));
									}
								}
							}

							beeentity.resetTicksWithoutNectar();
							if (bees != null) {
								bees.add(beeentity);
							}
						}

						BlockPos blockpos2 = this.getPos();
						this.world.playSound((PlayerEntity) null, (double) blockpos2.getX(), (double) blockpos2.getY(),
								(double) blockpos2.getZ(), SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F,
								1.0F);
						return this.world.addEntity(entity);
					}
				} else {
					return false;
				}
			}
		}
	}

	private boolean hasFlowerPos() {
		return this.flowerPos != null;
	}

	private void tickBees() {
		Iterator<ModBeehiveTileEntity.Bee> iterator = this.bees.iterator();
		BlockState blockstate = this.getBlockState();

		while (iterator.hasNext()) {
			ModBeehiveTileEntity.Bee beehivetileentity$bee = iterator.next();
			if (beehivetileentity$bee.ticksInHive > beehivetileentity$bee.minOccupationTicks) {
				CompoundNBT compoundnbt = beehivetileentity$bee.entityData;
				ModBeehiveTileEntity.State beehivetileentity$state = compoundnbt.getBoolean("HasNectar")
						? ModBeehiveTileEntity.State.HONEY_DELIVERED
						: ModBeehiveTileEntity.State.BEE_RELEASED;
				if (this.releaseBee(blockstate, compoundnbt, null, beehivetileentity$state)) {
					iterator.remove();
				}
			} else {
				beehivetileentity$bee.ticksInHive++;
			}
		}

	}

	public void tick() {
		if (!this.world.isRemote) {
			this.tickBees();
			BlockPos blockpos = this.getPos();
			if (this.bees.size() > 0 && this.world.getRandom().nextDouble() < 0.005D) {
				double d0 = (double) blockpos.getX() + 0.5D;
				double d1 = (double) blockpos.getY();
				double d2 = (double) blockpos.getZ() + 0.5D;
				this.world.playSound((PlayerEntity) null, d0, d1, d2, SoundEvents.BLOCK_BEEHIVE_WORK,
						SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	public void read(CompoundNBT compound) {
		super.read(compound);
		this.bees.clear();
		ListNBT listnbt = compound.getList("Bees", 10);

		for (int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			ModBeehiveTileEntity.Bee beehivetileentity$bee = new ModBeehiveTileEntity.Bee(
					compoundnbt.getCompound("EntityData"), compoundnbt.getInt("TicksInHive"),
					compoundnbt.getInt("MinOccupationTicks"));
			this.bees.add(beehivetileentity$bee);
		}

		this.flowerPos = null;
		if (compound.contains("FlowerPos")) {
			this.flowerPos = NBTUtil.readBlockPos(compound.getCompound("FlowerPos"));
		}

	}

	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.put("Bees", this.getBees());
		if (this.hasFlowerPos()) {
			compound.put("FlowerPos", NBTUtil.writeBlockPos(this.flowerPos));
		}

		return compound;
	}

	public ListNBT getBees() {
		ListNBT listnbt = new ListNBT();

		for (ModBeehiveTileEntity.Bee beehivetileentity$bee : this.bees) {
			beehivetileentity$bee.entityData.removeUniqueId("UUID");
			CompoundNBT compoundnbt = new CompoundNBT();
			compoundnbt.put("EntityData", beehivetileentity$bee.entityData);
			compoundnbt.putInt("TicksInHive", beehivetileentity$bee.ticksInHive);
			compoundnbt.putInt("MinOccupationTicks", beehivetileentity$bee.minOccupationTicks);
			listnbt.add(compoundnbt);
		}

		return listnbt;
	}

	static class Bee {
		private final CompoundNBT entityData;
		private int ticksInHive;
		private final int minOccupationTicks;

		private Bee(CompoundNBT p_i225767_1_, int p_i225767_2_, int p_i225767_3_) {
			p_i225767_1_.removeUniqueId("UUID");
			this.entityData = p_i225767_1_;
			this.ticksInHive = p_i225767_2_;
			this.minOccupationTicks = p_i225767_3_;
		}
	}

	public static enum State {
		HONEY_DELIVERED, BEE_RELEASED, EMERGENCY;
	}
}