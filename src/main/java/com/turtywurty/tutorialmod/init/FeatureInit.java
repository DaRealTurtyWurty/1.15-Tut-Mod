package com.turtywurty.tutorialmod.init;

import java.util.Locale;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.world.feature.structures.HousePieces;
import com.turtywurty.tutorialmod.world.feature.structures.HouseStructure;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Bus.MOD, modid = TutorialMod.MOD_ID)
public class FeatureInit {

	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<Feature<?>>(
			ForgeRegistries.FEATURES, TutorialMod.MOD_ID);
	
	public static IStructurePieceType HOUSE_PIECE = HousePieces.Piece::new;

	public static final RegistryObject<HouseStructure> HOUSE = FEATURES.register("house",
			() -> new HouseStructure(NoFeatureConfig::deserialize));

	@SubscribeEvent
	public static void registerStructurePieces(RegistryEvent.Register<Feature<?>> event) {
		Registry.register(Registry.STRUCTURE_PIECE, "HOUSE".toLowerCase(Locale.ROOT), HOUSE_PIECE);
	}
}
