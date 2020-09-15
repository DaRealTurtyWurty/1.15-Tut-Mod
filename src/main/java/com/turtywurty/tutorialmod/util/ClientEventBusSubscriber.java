package com.turtywurty.tutorialmod.util;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.client.entity.render.ExampleEntityRender;
import com.turtywurty.tutorialmod.client.gui.ExampleChestScreen;
import com.turtywurty.tutorialmod.client.gui.ExampleFurnaceScreen;
import com.turtywurty.tutorialmod.client.gui.ItemPedestalScreen;
import com.turtywurty.tutorialmod.client.tileentity.renderer.ItemPedestalRenderer;
import com.turtywurty.tutorialmod.init.BlockInit;
import com.turtywurty.tutorialmod.init.ItemInit;
import com.turtywurty.tutorialmod.init.ModContainerTypes;
import com.turtywurty.tutorialmod.init.ModEntityTypes;
import com.turtywurty.tutorialmod.init.ModTileEntityTypes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ModContainerTypes.EXAMPLE_CHEST.get(), ExampleChestScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.ITEM_PEDESTAL.get(), ItemPedestalScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.EXAMPLE_FURNACE.get(), ExampleFurnaceScreen::new);

		RenderTypeLookup.setRenderLayer(BlockInit.JAZZ_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInit.EXAMPLE_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInit.JAZZ_DOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInit.EXAMPLE_TORCH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInit.EXAMPLE_WALL_TORCH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInit.EXAMPLE_LADDER.get(), RenderType.getCutout());

		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.EXAMPLE_ENTITY.get(), ExampleEntityRender::new);

		ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.ITEM_PEDESTAL.get(), ItemPedestalRenderer::new);

		ItemInit.CRYSTAL.get().addPropertyOverride(new ResourceLocation(TutorialMod.MOD_ID, "count"),
				new IItemPropertyGetter() {

					@Override
					public float call(ItemStack stack, World worldIn, LivingEntity entityIn) {
						switch (stack.getCount()) {
						case 1:
							return 0.25f;
						case 2:
							return 0.5f;
						case 3:
							return 0.75f;
						case 4:
							return 1.0f;
						default:
							return 0.0f;
						}
					}
				});
	}
}
