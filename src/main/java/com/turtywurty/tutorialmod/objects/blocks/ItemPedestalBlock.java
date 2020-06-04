package com.turtywurty.tutorialmod.objects.blocks;

import java.util.stream.Stream;

import com.turtywurty.tutorialmod.init.ModTileEntityTypes;
import com.turtywurty.tutorialmod.tileentity.ItemPedestalTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemPedestalBlock extends Block {

	public ItemPedestalBlock(Block.Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.ITEM_PEDESTAL.get().create();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			final TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof ItemPedestalTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (ItemPedestalTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof ItemPedestalTileEntity) {
				InventoryHelper.dropItems(worldIn, pos, ((ItemPedestalTileEntity) tile).getItems());
			}
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Stream.of(Block.makeCuboidShape(3, 2, 3, 13, 15, 13), Block.makeCuboidShape(2, 15, 2, 14, 16, 14),
				Block.makeCuboidShape(0, 0, 0, 16, 2, 16)).reduce((v1, v2) -> {
					return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
				}).get();
	}
}
