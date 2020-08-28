package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;

import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureInit {

	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<Feature<?>>(
			ForgeRegistries.FEATURES, TutorialMod.MOD_ID);
}
