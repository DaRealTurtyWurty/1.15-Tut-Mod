package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.TutorialMod.TutorialItemGroup;
import com.turtywurty.tutorialmod.objects.items.SpecialItem;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInitNew {

	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS,
			TutorialMod.MOD_ID);

	public static final RegistryObject<Item> DEF_ITEM = ITEMS.register("def_item",
			() -> new SpecialItem(new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<Item> SEED_ITEM = ITEMS.register("seed_item",
			() -> new BlockItem(BlockInitNew.EXAMPLE_CROP.get(),
					new Item.Properties().group(TutorialItemGroup.instance)));
}
