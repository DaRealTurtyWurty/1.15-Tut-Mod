package com.turtywurty.tutorialmod.world.worldtype;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.OverworldChunkGenerator;

public class ExampleWorldType extends WorldType {

	public ExampleWorldType() {
		super("example_world");
	}
	
	@Override
	public ChunkGenerator<?> createChunkGenerator(World world) {
		//SingleBiomeProviderSettings singleSettings = new SingleBiomeProviderSettings(world.getWorldInfo());
		//singleSettings.setBiome(BiomeInit.EXAMPLE_BIOME.get());
		return new OverworldChunkGenerator(world, new ExampleBiomeProvider(world.getWorldInfo(), this), ChunkGeneratorType.SURFACE.createSettings());
	}
}
