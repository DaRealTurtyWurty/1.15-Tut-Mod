package com.turtywurty.tutorialmod.util;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.client.entity.render.ExampleEntityRender;
import com.turtywurty.tutorialmod.client.gui.ExampleChestScreen;
import com.turtywurty.tutorialmod.init.BlockInitNew;
import com.turtywurty.tutorialmod.init.ModContainerTypes;
import com.turtywurty.tutorialmod.init.ModEntityTypes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ModContainerTypes.EXAMPLE_CHEST.get(), ExampleChestScreen::new);
		RenderTypeLookup.setRenderLayer(BlockInitNew.JAZZ_SAPLING.get(), RenderType.getCutout());
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.EXAMPLE_ENTITY.get(), ExampleEntityRender::new);
	}
}
