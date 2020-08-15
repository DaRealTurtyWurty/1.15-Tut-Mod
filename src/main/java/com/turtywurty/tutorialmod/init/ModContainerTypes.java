package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.container.ExampleChestContainer;
import com.turtywurty.tutorialmod.container.ExampleFurnaceContainer;
import com.turtywurty.tutorialmod.container.ItemPedestalContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(
			ForgeRegistries.CONTAINERS, TutorialMod.MOD_ID);

	public static final RegistryObject<ContainerType<ExampleChestContainer>> EXAMPLE_CHEST = CONTAINER_TYPES
			.register("example_chest", () -> IForgeContainerType.create(ExampleChestContainer::new));

	public static final RegistryObject<ContainerType<ItemPedestalContainer>> ITEM_PEDESTAL = CONTAINER_TYPES
			.register("item_pedestal", () -> IForgeContainerType.create(ItemPedestalContainer::new));

	public static final RegistryObject<ContainerType<ExampleFurnaceContainer>> EXAMPLE_FURNACE = CONTAINER_TYPES
			.register("example_furnace", () -> IForgeContainerType.create(ExampleFurnaceContainer::new));
}
