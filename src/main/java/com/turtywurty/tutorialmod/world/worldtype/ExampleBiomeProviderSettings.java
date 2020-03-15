package com.turtywurty.tutorialmod.world.worldtype;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class ExampleBiomeProviderSettings extends OverworldBiomeProviderSettings {
	private final long seed;
	private final WorldType worldType;
	private ExampleGenSettings generatorSettings = new ExampleGenSettings();

	public ExampleBiomeProviderSettings(WorldInfo info) {
		super(info);
		this.seed = info.getSeed();
		this.worldType = info.getGenerator();
	}

	public ExampleBiomeProviderSettings setGeneratorSettings(ExampleGenSettings genSettings) {
		this.generatorSettings = genSettings;
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
