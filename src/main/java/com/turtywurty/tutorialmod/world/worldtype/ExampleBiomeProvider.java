package com.turtywurty.tutorialmod.world.worldtype;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.turtywurty.tutorialmod.init.BiomeInit;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.storage.WorldInfo;

public class ExampleBiomeProvider extends BiomeProvider {
	private final Layer genBiomes;
	private static final Set<Biome> biomes = ImmutableSet.of(BiomeInit.EXAMPLE_BIOME.get(), Biomes.DEEP_WARM_OCEAN);

	public ExampleBiomeProvider(WorldInfo info, WorldType type) {
		super(biomes);
		this.genBiomes = LayerUtil.func_227474_a_(info.getSeed(), type, new ExampleGenSettings());
	}
	
	public Biome getNoiseBiome(int x, int y, int z) {
		return this.genBiomes.func_215738_a(x, z);
	}
}
