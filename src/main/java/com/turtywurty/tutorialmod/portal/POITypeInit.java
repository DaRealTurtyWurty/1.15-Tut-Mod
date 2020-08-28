package com.turtywurty.tutorialmod.portal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;
import com.turtywurty.tutorialmod.TutorialMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class POITypeInit {

	public static final DeferredRegister<PointOfInterestType> POI_TYPES = new DeferredRegister<PointOfInterestType>(
			ForgeRegistries.POI_TYPES, TutorialMod.MOD_ID);

	public static RegistryObject<PointOfInterestType> registerPOIFromStates(String name,
			Supplier<Set<BlockState>> states, int maxUsers, int minStopDistance) {
		return POI_TYPES.register(name, () -> {
			PointOfInterestType poi = null;
			try {
				poi = ObfuscationReflectionHelper
						.findConstructor(PointOfInterestType.class,
								new Class[] { String.class, Set.class, int.class, int.class })
						.newInstance(new Object[] { name, (Set<BlockState>) ImmutableSet.copyOf(states.get()), maxUsers,
								minStopDistance });
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
			if (poi == null)
				throw new RuntimeException("Unable to construct Point of Interest");

			return poi;
		});
	}

	public static RegistryObject<PointOfInterestType> registerPOIFromBlocks(String name, Supplier<Set<Block>> allBlocks,
			int maxUsers, int minStopDistance) {

		return registerPOIFromStates(name, () -> {
			List<BlockState> states = new ArrayList<>();
			allBlocks.get().forEach((bl) -> {
				states.addAll(ImmutableSet.copyOf(bl.getStateContainer().getValidStates()));
			});
			return (Set<BlockState>) ImmutableSet.copyOf(states);
		}, maxUsers, minStopDistance);
	}

	public static RegistryObject<PointOfInterestType> registerPOIFromBlocks(String name, Supplier<Set<Block>> blocks,
			Predicate<PointOfInterestType> predicate, int maxUsers, int minStopDistance) {
		return registerPOIFromPredicateAndStates(name, () -> {
			List<BlockState> states = new ArrayList<BlockState>();
			blocks.get().forEach(block -> {
				states.addAll(ImmutableSet.copyOf(block.getStateContainer().getValidStates()));
			});
			return (Set<BlockState>) ImmutableSet.copyOf(states);
		}, predicate, maxUsers, minStopDistance);
	}

	public static RegistryObject<PointOfInterestType> registerPOIFromPredicateAndStates(String name,
			Supplier<Set<BlockState>> states, Predicate<PointOfInterestType> predicate, int maxUsers,
			int minStopDistance) {
		return POI_TYPES.register(name, () -> {
			PointOfInterestType poi = null;
			try {
				poi = ObfuscationReflectionHelper
						.findConstructor(PointOfInterestType.class,
								new Class[] { String.class, Set.class, Predicate.class, int.class })
						.newInstance(new Object[] { name, (Set<BlockState>) ImmutableSet.copyOf(states.get()), maxUsers,
								predicate, minStopDistance });
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}

			if (poi == null) {
				throw new RuntimeException("Unable to construct Point of Interest");
			}

			return poi;
		});
	}
}
