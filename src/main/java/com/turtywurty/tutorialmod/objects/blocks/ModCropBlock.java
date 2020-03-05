package com.turtywurty.tutorialmod.objects.blocks;

import net.minecraft.block.CropsBlock;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

public class ModCropBlock extends CropsBlock {
	private Item seeds;
	private int maxAge;

	public ModCropBlock(Properties builderIn, Item seedsIn, int maxAgeIn) {
		super(builderIn);
		this.seeds = seedsIn;
		this.maxAge = maxAgeIn;
	}
	
	@Override
	public IItemProvider getSeedsItem() {
		return this.seeds;
	}
	
	@Override
	public int getMaxAge() {
		return this.maxAge;
	}
}
