package com.turtywurty.tutorialmod.util;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.init.DimensionInit;

import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.FORGE)
public class ForgeEventBusSubscriber {

	@SubscribeEvent
	public static void registerDimensions(final RegisterDimensionsEvent event) {
		if (DimensionType.byName(TutorialMod.EXAMPLE_DIM_TYPE) == null) {
			DimensionManager.registerDimension(TutorialMod.EXAMPLE_DIM_TYPE, DimensionInit.EXAMPLE_DIM.get(), null,
					true);
		}
		TutorialMod.LOGGER.info("Dimensions Registered!");
	}
}
