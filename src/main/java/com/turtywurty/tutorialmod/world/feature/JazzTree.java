package com.turtywurty.tutorialmod.world.feature;

import java.util.Random;

import com.turtywurty.tutorialmod.init.BlockInit;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.common.IPlantable;

public class JazzTree extends Tree {

	public static final TreeFeatureConfig JAZZ_TREE_CONFIG = (new TreeFeatureConfig.Builder(
			new SimpleBlockStateProvider(BlockInit.JAZZ_LOG.get().getDefaultState()),
			new SimpleBlockStateProvider(BlockInit.JAZZ_LEAVES.get().getDefaultState()),
			new BlobFoliagePlacer(40, 1))).baseHeight(150).heightRandA(5).foliageHeight(125).ignoreVines()
					.setSapling((IPlantable) BlockInit.JAZZ_SAPLING.get()).build();

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean b) {
		return Feature.NORMAL_TREE.withConfiguration(JAZZ_TREE_CONFIG);
	}
}
