package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.TutorialMod.TutorialItemGroup;
import com.turtywurty.tutorialmod.objects.items.ModMusicDiscItem;
import com.turtywurty.tutorialmod.objects.items.ModSpawnEggItem;
import com.turtywurty.tutorialmod.objects.items.SpecialItem;
import com.turtywurty.tutorialmod.util.enums.ModArmorMaterials;
import com.turtywurty.tutorialmod.util.enums.ModItemTiers;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS,
			TutorialMod.MOD_ID);

	public static final RegistryObject<SpecialItem> DEF_ITEM = ITEMS.register("def_item",
			() -> new SpecialItem(new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<BlockItem> SEED_ITEM = ITEMS.register("seed_item",
			() -> new BlockItem(BlockInit.EXAMPLE_CROP.get(), new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
			() -> new Item(new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item",
			() -> new Item(new Item.Properties().group(TutorialItemGroup.instance).food(new Food.Builder().hunger(6)
					.saturation(1.2f).effect(new EffectInstance(Effects.ABSORPTION, 6000, 5), 0.7f).build())));

	public static final RegistryObject<SpecialItem> SPECIAL_ITEM = ITEMS.register("special_item",
			() -> new SpecialItem(new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<SwordItem> EXAMPLE_SWORD = ITEMS.register("example_sword",
			() -> new SwordItem(ModItemTiers.EXAMPLE, 7, 5.0f,
					new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<PickaxeItem> EXAMPLE_PICKAXE = ITEMS.register("example_pickaxe",
			() -> new PickaxeItem(ModItemTiers.EXAMPLE, 4, 5.0f,
					new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<ShovelItem> EXAMPLE_SHOVEL = ITEMS.register("example_shovel",
			() -> new ShovelItem(ModItemTiers.EXAMPLE, 2, 5.0f,
					new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<AxeItem> EXAMPLE_AXE = ITEMS.register("example_axe",
			() -> new AxeItem(ModItemTiers.EXAMPLE, 11, 3.0f, new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<HoeItem> EXAMPLE_HOE = ITEMS.register("example_hoe",
			() -> new HoeItem(ModItemTiers.EXAMPLE, 5.0f, new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<ArmorItem> TEST_HELMET = ITEMS.register("test_helmet",
			() -> new ArmorItem(ModArmorMaterials.TEST, EquipmentSlotType.HEAD,
					new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<ArmorItem> TEST_CHESTPLATE = ITEMS.register("test_chestplate",
			() -> new ArmorItem(ModArmorMaterials.TEST, EquipmentSlotType.CHEST,
					new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<ArmorItem> TEST_LEGGINGS = ITEMS.register("test_leggings",
			() -> new ArmorItem(ModArmorMaterials.TEST, EquipmentSlotType.LEGS,
					new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<ArmorItem> TEST_BOOTS = ITEMS.register("test_boots",
			() -> new ArmorItem(ModArmorMaterials.TEST, EquipmentSlotType.FEET,
					new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<Item> CRYSTAL = ITEMS.register("crystal",
			() -> new Item(new Item.Properties().group(TutorialItemGroup.instance).maxStackSize(4)));

	public static final RegistryObject<ModMusicDiscItem> PROMISES_DISC = ITEMS.register("disc_promises",
			() -> new ModMusicDiscItem(5, SoundInit.LAZY_PROMISES_MUSIC.get(),
					new Item.Properties().group(TutorialItemGroup.instance).maxStackSize(1).rarity(Rarity.RARE)));

	public static final RegistryObject<ModSpawnEggItem> EXAMPLE_SPAWN_EGG = ITEMS.register("example_spawn_egg",
			() -> new ModSpawnEggItem(ModEntityTypes.EXAMPLE_ENTITY, 0xFF329F, 0x16777119,
					new Item.Properties().group(TutorialItemGroup.instance).maxStackSize(16)));

	public static final RegistryObject<BucketItem> EXAMPLE_BUCKET = ITEMS.register("example_bucket",
			() -> new BucketItem(() -> FluidInit.MILK_FLUID.get(),
					new Item.Properties().group(TutorialItemGroup.instance).maxStackSize(1)));

	public static final RegistryObject<WallOrFloorItem> EXAMPLE_TORCH = ITEMS.register("example_torch",
			() -> new WallOrFloorItem(BlockInit.EXAMPLE_TORCH.get(), BlockInit.EXAMPLE_WALL_TORCH.get(),
					new Item.Properties().group(TutorialItemGroup.instance)));

	public static final RegistryObject<Item> CUSTOM_ITEM = ITEMS.register("custom_item",
			() -> new Item(new Item.Properties().group(TutorialItemGroup.instance)));
}
