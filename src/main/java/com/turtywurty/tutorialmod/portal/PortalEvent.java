package com.turtywurty.tutorialmod.portal;

import com.turtywurty.tutorialmod.TutorialMod;

import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.FORGE)
public class PortalEvent {

	@SubscribeEvent
	public static void createPortal(BlockEvent.EntityPlaceEvent event) {
		BlockState state = event.getPlacedBlock();
		if (state.getBlock() instanceof FireBlock) {
			PortalBlockInit.PORTAL.get().trySpawnPortal(event.getWorld(), event.getPos());
		}
	}
}
