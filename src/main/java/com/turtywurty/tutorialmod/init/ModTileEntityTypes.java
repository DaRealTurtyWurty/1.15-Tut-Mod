package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.tileentity.ExampleChestTileEntity;
import com.turtywurty.tutorialmod.tileentity.ExampleFurnaceTileEntity;
import com.turtywurty.tutorialmod.tileentity.ItemPedestalTileEntity;
import com.turtywurty.tutorialmod.tileentity.QuarryTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(
			ForgeRegistries.TILE_ENTITIES, TutorialMod.MOD_ID);

	public static final RegistryObject<TileEntityType<QuarryTileEntity>> QUARRY = TILE_ENTITY_TYPES.register("quarry",
			() -> TileEntityType.Builder.create(QuarryTileEntity::new, BlockInit.QUARRY.get()).build(null));

	public static final RegistryObject<TileEntityType<ExampleChestTileEntity>> EXAMPLE_CHEST = TILE_ENTITY_TYPES
			.register("example_chest", () -> TileEntityType.Builder
					.create(ExampleChestTileEntity::new, BlockInit.EXAMPLE_CHEST.get()).build(null));

	public static final RegistryObject<TileEntityType<ItemPedestalTileEntity>> ITEM_PEDESTAL = TILE_ENTITY_TYPES
			.register("item_pedestal", () -> TileEntityType.Builder
					.create(ItemPedestalTileEntity::new, BlockInit.ITEM_PEDESTAL.get()).build(null));

	public static final RegistryObject<TileEntityType<ExampleFurnaceTileEntity>> EXAMPLE_FURNACE = TILE_ENTITY_TYPES
			.register("example_furnace", () -> TileEntityType.Builder
					.create(ExampleFurnaceTileEntity::new, BlockInit.EXAMPLE_FURNACE.get()).build(null));
}
