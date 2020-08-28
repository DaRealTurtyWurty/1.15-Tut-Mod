package com.turtywurty.tutorialmod.portal;

import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.cache.LoadingCache;
import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.init.SoundInit;
import com.turtywurty.tutorialmod.particles.ColouredParticle.ColouredParticleData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PortalBlockInit {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<Block>(ForgeRegistries.BLOCKS,
			TutorialMod.MOD_ID);

	public static final RegistryObject<PortalBlock> PORTAL = BLOCKS.register("portal",
			() -> new PortalBlock(Block.Properties.create(Material.PORTAL)));

	public static class PortalBlock extends Block {

		public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
		protected static final VoxelShape X_AABB = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
		protected static final VoxelShape Z_AABB = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

		public PortalBlock(Properties properties) {
			super(properties);
			this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.X));
		}

		@Override
		public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
			switch ((Direction.Axis) state.get(AXIS)) {
			case Z:
				return Z_AABB;
			case X:
			default:
				return X_AABB;
			}
		}

		public boolean trySpawnPortal(IWorld worldIn, BlockPos pos) {
			PortalBlock.Size modportalblock$size = this.isPortal(worldIn, pos);
			if (modportalblock$size != null) {
				modportalblock$size.placePortalBlocks();
				return true;
			} else {
				return false;
			}
		}

		@Nullable
		public PortalBlock.Size isPortal(IWorld worldIn, BlockPos pos) {
			PortalBlock.Size modportalblock$size = new PortalBlock.Size(worldIn, pos, Direction.Axis.X);
			if (modportalblock$size.isValid() && modportalblock$size.portalBlockCount == 0) {
				return modportalblock$size;
			} else {
				PortalBlock.Size modportalblock$size1 = new PortalBlock.Size(worldIn, pos, Direction.Axis.Z);
				return modportalblock$size1.isValid() && modportalblock$size1.portalBlockCount == 0
						? modportalblock$size1
						: null;
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState,
				IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
			Direction.Axis direction$axis = facing.getAxis();
			Direction.Axis direction$axis1 = stateIn.get(AXIS);
			boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
			return !flag && facingState.getBlock() != this
					&& !(new PortalBlock.Size(worldIn, currentPos, direction$axis1)).func_208508_f()
							? Blocks.AIR.getDefaultState()
							: super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}

		@Override
		public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
			if (!entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss()) {
				if (entityIn.dimension != DimensionType.byName(TutorialMod.EXAMPLE_DIM_TYPE)) {
					teleportToDimension(entityIn, DimensionType.byName(TutorialMod.EXAMPLE_DIM_TYPE), pos);
				} else {
					teleportToDimension(entityIn, DimensionType.byName(new ResourceLocation("minecraft", "overworld")),
							pos);
				}

				new PortalBlock.Size(worldIn, pos, Axis.X).placePortalBlocks();

				if (entityIn.timeUntilPortal > 0) {
					entityIn.timeUntilPortal = entityIn.getPortalCooldown();
				} else {
					if (!entityIn.world.isRemote && !pos.equals(entityIn.lastPortalPos)) {
						entityIn.lastPortalPos = new BlockPos(pos);
						BlockPattern.PatternHelper blockpattern$patternhelper = NetherPortalBlock
								.createPatternHelper(entityIn.world, entityIn.lastPortalPos);
						double d0 = blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X
								? (double) blockpattern$patternhelper.getFrontTopLeft().getZ()
								: (double) blockpattern$patternhelper.getFrontTopLeft().getX();
						double d1 = Math.abs(MathHelper.pct(
								(blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X
										? entityIn.getPosZ()
										: entityIn.getPosX())
										- (double) (blockpattern$patternhelper.getForwards().rotateY()
												.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? 1 : 0),
								d0, d0 - (double) blockpattern$patternhelper.getWidth()));
						double d2 = MathHelper.pct(entityIn.getPosY() - 1.0D,
								(double) blockpattern$patternhelper.getFrontTopLeft().getY(),
								(double) (blockpattern$patternhelper.getFrontTopLeft().getY()
										- blockpattern$patternhelper.getHeight()));
						entityIn.lastPortalVec = new Vec3d(d1, d2, 0.0D);
						entityIn.teleportDirection = blockpattern$patternhelper.getForwards();
					}

					entityIn.inPortal = true;
				}
			}
		}

		private void teleportToDimension(Entity entityIn, DimensionType dimension, BlockPos pos) {
			entityIn.changeDimension(dimension, new ITeleporter() {
				@Override
				public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw,
						Function<Boolean, Entity> repositionEntity) {
					entity = repositionEntity.apply(false);
					entity.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
					return entity;
				}
			});
		}

		@Override
		@Deprecated
		public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
			if (worldIn.dimension.isSurfaceWorld() && worldIn.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)
					&& rand.nextInt(2000) < worldIn.getDifficulty().getId()) {
				while (worldIn.getBlockState(pos).getBlock() == this) {
					pos = pos.down();
				}

				if (worldIn.getBlockState(pos).canEntitySpawn(worldIn, pos, EntityType.ZOMBIE_PIGMAN)) {
					Entity entity = EntityType.ZOMBIE_PIGMAN.spawn(worldIn, (CompoundNBT) null, (ITextComponent) null,
							(PlayerEntity) null, pos.up(), SpawnReason.STRUCTURE, false, false);
					if (entity != null) {
						entity.timeUntilPortal = entity.getPortalCooldown();
					}
				}
			}
		}

		@OnlyIn(Dist.CLIENT)
		public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
			if (rand.nextInt(100) == 0) {
				worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D,
						SoundInit.AMBIENT.get(), SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
			}

			for (int i = 0; i < 4; ++i) {
				double d0 = (double) pos.getX() + (double) rand.nextFloat();
				double d1 = (double) pos.getY() + (double) rand.nextFloat();
				double d2 = (double) pos.getZ() + (double) rand.nextFloat();
				double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
				double d4 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
				double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
				int j = rand.nextInt(2) * 2 - 1;
				if (worldIn.getBlockState(pos.west()).getBlock() != this
						&& worldIn.getBlockState(pos.east()).getBlock() != this) {
					d0 = (double) pos.getX() + 0.5D + 0.25D * (double) j;
					d3 = (double) (rand.nextFloat() * 2.0F * (float) j);
				} else {
					d2 = (double) pos.getZ() + 0.5D + 0.25D * (double) j;
					d5 = (double) (rand.nextFloat() * 2.0F * (float) j);
				}

				worldIn.addParticle(new ColouredParticleData((float) d0, (float) d1, (float) d2, 1.0F), d0, d1, d2, d3,
						d4, d5);
			}

		}

		public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
			return ItemStack.EMPTY;
		}

		@Deprecated
		public BlockState rotate(BlockState state, Rotation rot) {
			switch (rot) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch ((Direction.Axis) state.get(AXIS)) {
				case Z:
					return state.with(AXIS, Direction.Axis.X);
				case X:
					return state.with(AXIS, Direction.Axis.Z);
				default:
					return state;
				}
			default:
				return state;
			}
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(AXIS);
		}

		@SuppressWarnings("deprecation")
		public static BlockPattern.PatternHelper createPatternHelper(IWorld world, BlockPos pos) {
			Direction.Axis direction$axis = Direction.Axis.Z;
			PortalBlock.Size modportalblock$size = new PortalBlock.Size(world, pos, Direction.Axis.X);
			LoadingCache<BlockPos, CachedBlockInfo> loadingcache = BlockPattern.createLoadingCache(world, true);
			if (!modportalblock$size.isValid()) {
				direction$axis = Direction.Axis.X;
				modportalblock$size = new PortalBlock.Size(world, pos, Direction.Axis.Z);
			}

			if (!modportalblock$size.isValid()) {
				return new BlockPattern.PatternHelper(pos, Direction.NORTH, Direction.UP, loadingcache, 1, 1, 1);
			} else {
				int[] aint = new int[Direction.AxisDirection.values().length];
				Direction direction = modportalblock$size.rightDir.rotateYCCW();
				BlockPos blockpos = modportalblock$size.bottomLeft.up(modportalblock$size.getHeight() - 1);

				for (Direction.AxisDirection direction$axisdirection : Direction.AxisDirection.values()) {
					BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(
							direction.getAxisDirection() == direction$axisdirection ? blockpos
									: blockpos.offset(modportalblock$size.rightDir, modportalblock$size.getWidth() - 1),
							Direction.getFacingFromAxis(direction$axisdirection, direction$axis), Direction.UP,
							loadingcache, modportalblock$size.getWidth(), modportalblock$size.getHeight(), 1);

					for (int i = 0; i < modportalblock$size.getWidth(); ++i) {
						for (int j = 0; j < modportalblock$size.getHeight(); ++j) {
							CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.translateOffset(i, j, 1);
							if (!cachedblockinfo.getBlockState().isAir()) {
								++aint[direction$axisdirection.ordinal()];
							}
						}
					}
				}

				Direction.AxisDirection direction$axisdirection1 = Direction.AxisDirection.POSITIVE;

				for (Direction.AxisDirection direction$axisdirection2 : Direction.AxisDirection.values()) {
					if (aint[direction$axisdirection2.ordinal()] < aint[direction$axisdirection1.ordinal()]) {
						direction$axisdirection1 = direction$axisdirection2;
					}
				}

				return new BlockPattern.PatternHelper(
						direction.getAxisDirection() == direction$axisdirection1 ? blockpos
								: blockpos.offset(modportalblock$size.rightDir, modportalblock$size.getWidth() - 1),
						Direction.getFacingFromAxis(direction$axisdirection1, direction$axis), Direction.UP,
						loadingcache, modportalblock$size.getWidth(), modportalblock$size.getHeight(), 1);
			}
		}

		public static class Size {
			private final IWorld world;
			private final Direction.Axis axis;
			private final Direction rightDir;
			private final Direction leftDir;
			private int portalBlockCount;
			@Nullable
			private BlockPos bottomLeft;
			private int height;
			private int width;

			public Size(IWorld worldIn, BlockPos pos, Direction.Axis axisIn) {
				this.world = worldIn;
				this.axis = axisIn;
				if (axisIn == Direction.Axis.X) {
					this.leftDir = Direction.EAST;
					this.rightDir = Direction.WEST;
				} else {
					this.leftDir = Direction.NORTH;
					this.rightDir = Direction.SOUTH;
				}

				for (BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > 0
						&& this.func_196900_a(worldIn.getBlockState(pos.down())); pos = pos.down()) {
					;
				}

				int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;
				if (i >= 0) {
					this.bottomLeft = pos.offset(this.leftDir, i);
					this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);
					if (this.width < 2 || this.width > 21) {
						this.bottomLeft = null;
						this.width = 0;
					}
				}

				if (this.bottomLeft != null) {
					this.height = this.calculatePortalHeight();
				}

			}

			protected int getDistanceUntilEdge(BlockPos pos, Direction directionIn) {
				int i;
				for (i = 0; i < 22; ++i) {
					BlockPos blockpos = pos.offset(directionIn, i);
					if (!this.func_196900_a(this.world.getBlockState(blockpos))
							|| !this.world.getBlockState(blockpos.down()).equals(Blocks.BEDROCK.getDefaultState())) {
						break;
					}
				}

				BlockPos framePos = pos.offset(directionIn, i);
				return this.world.getBlockState(framePos).equals(Blocks.BEDROCK.getDefaultState()) ? i : 0;
			}

			public int getHeight() {
				return this.height;
			}

			public int getWidth() {
				return this.width;
			}

			protected int calculatePortalHeight() {
				label56: for (this.height = 0; this.height < 21; ++this.height) {
					for (int i = 0; i < this.width; ++i) {
						BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
						BlockState blockstate = this.world.getBlockState(blockpos);
						if (!this.func_196900_a(blockstate)) {
							break label56;
						}

						Block block = blockstate.getBlock();
						if (block == PortalBlockInit.PORTAL.get()) {
							++this.portalBlockCount;
						}

						if (i == 0) {
							BlockPos framePos = blockpos.offset(this.leftDir);
							if (!this.world.getBlockState(framePos).equals(Blocks.BEDROCK.getDefaultState())) {
								break label56;
							}
						} else if (i == this.width - 1) {
							BlockPos framePos = blockpos.offset(this.rightDir);
							if (!this.world.getBlockState(framePos).equals(Blocks.BEDROCK.getDefaultState())) {
								break label56;
							}
						}
					}
				}

				for (int j = 0; j < this.width; ++j) {
					BlockPos framePos = this.bottomLeft.offset(this.rightDir, j).up(this.height);
					if (!this.world.getBlockState(framePos).equals(Blocks.BEDROCK.getDefaultState())) {
						this.height = 0;
						break;
					}
				}

				if (this.height <= 21 && this.height >= 3) {
					return this.height;
				} else {
					this.bottomLeft = null;
					this.width = 0;
					this.height = 0;
					return 0;
				}
			}

			@SuppressWarnings("deprecation")
			protected boolean func_196900_a(BlockState pos) {
				Block block = pos.getBlock();
				return pos.isAir() || block == Blocks.FIRE || block == PortalBlockInit.PORTAL.get();
			}

			public boolean isValid() {
				return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3
						&& this.height <= 21;
			}

			public void placePortalBlocks() {
				for (int i = 0; i < this.width; ++i) {
					BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

					for (int j = 0; j < this.height; ++j) {
						this.world.setBlockState(blockpos.up(j),
								PortalBlockInit.PORTAL.get().getDefaultState().with(PortalBlock.AXIS, this.axis), 18);
					}
				}

			}

			private boolean func_196899_f() {
				return this.portalBlockCount >= this.width * this.height;
			}

			public boolean func_208508_f() {
				return this.isValid() && this.func_196899_f();
			}
		}
	}
}
