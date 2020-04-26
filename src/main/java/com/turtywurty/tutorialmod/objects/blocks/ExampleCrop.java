package com.turtywurty.tutorialmod.objects.blocks;

import com.turtywurty.tutorialmod.init.ItemInitNew;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExampleCrop extends CropsBlock {

	private static final VoxelShape[] SHAPES = new VoxelShape[] {
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 2.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 3.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 4.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 5.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 6.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 7.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 8.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 9.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 10.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 11.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 12.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 13.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 14.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 15.0d, 16.0d),
			Block.makeCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 16.0d, 16.0d) };

	public ExampleCrop(Properties builderIn) {
		super(builderIn);
	}

	@Override
	public IItemProvider getSeedsItem() {
		return ItemInitNew.DEF_ITEM.get();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.get(this.getAgeProperty())];
	}
}
