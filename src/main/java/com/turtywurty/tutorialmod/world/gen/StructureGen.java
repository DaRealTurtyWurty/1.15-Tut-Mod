package com.turtywurty.tutorialmod.world.gen;

import com.turtywurty.tutorialmod.init.BiomeInit;
import com.turtywurty.tutorialmod.init.FeatureInit;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureGen {
	public static void generateStructures() {
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (biome == BiomeInit.EXAMPLE_BIOME.get()) {
				biome.addStructure(FeatureInit.HOUSE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
			}

			biome.addFeature(Decoration.SURFACE_STRUCTURES,
					FeatureInit.HOUSE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
							.withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		}
	}
}
