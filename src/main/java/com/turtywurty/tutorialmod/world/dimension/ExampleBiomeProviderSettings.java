package com.turtywurty.tutorialmod.world.dimension;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class ExampleBiomeProviderSettings implements IBiomeProviderSettings {

	private final long seed;
	private final WorldType worldType;
	private ExampleGenSettings generatorSettings = new ExampleGenSettings();

	public ExampleBiomeProviderSettings(WorldInfo info) {
		this.seed = info.getSeed();
		this.worldType = info.getGenerator();
	}

	public ExampleBiomeProviderSettings setGeneratorSettings(ExampleGenSettings settings) {
		this.generatorSettings = settings;
		return this;
	}

	public long getSeed() {
		return this.seed;
	}

	public WorldType getWorldType() {
		return this.worldType;
	}

	public ExampleGenSettings getGeneratorSettings() {
		return this.generatorSettings;
	}
}
