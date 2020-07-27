package com.turtywurty.tutorialmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.turtywurty.tutorialmod.init.BiomeInit;
import com.turtywurty.tutorialmod.init.BlockInit;
import com.turtywurty.tutorialmod.init.DimensionInit;
import com.turtywurty.tutorialmod.init.EnchantmentInit;
import com.turtywurty.tutorialmod.init.FluidInit;
import com.turtywurty.tutorialmod.init.ItemInit;
import com.turtywurty.tutorialmod.init.ModContainerTypes;
import com.turtywurty.tutorialmod.init.ModEntityTypes;
import com.turtywurty.tutorialmod.init.ModTileEntityTypes;
import com.turtywurty.tutorialmod.init.ParticleInit;
import com.turtywurty.tutorialmod.init.PotionInit;
import com.turtywurty.tutorialmod.init.SoundInit;
import com.turtywurty.tutorialmod.objects.blocks.ExampleCrop;
import com.turtywurty.tutorialmod.world.gen.TutorialOreGen;

import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
//import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("deprecation") //Supresses warnings for DeferredWorkQueue, cpw literally forgot why he deprecated it, it's pointless.
@Mod("tutorialmod")
@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.MOD)
public class TutorialMod {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "tutorialmod";
	public static TutorialMod instance;
	// public static final WorldType EXAMPLE_WORLDTYPE = new ExampleWorldType();
	public static final ResourceLocation EXAMPLE_DIM_TYPE = new ResourceLocation(MOD_ID, "example");

	public TutorialMod() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);

		ParticleInit.PARTICLE_TYPES.register(modEventBus);
		SoundInit.SOUNDS.register(modEventBus);
		PotionInit.POTIONS.register(modEventBus);
		PotionInit.POTION_EFFECTS.register(modEventBus);
		EnchantmentInit.ENCHANTMENTS.register(modEventBus);
		ItemInit.ITEMS.register(modEventBus);
		FluidInit.FLUIDS.register(modEventBus);
		BlockInit.BLOCKS.register(modEventBus);
		ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
		ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
		ModEntityTypes.ENTITY_TYPES.register(modEventBus);
		BiomeInit.BIOMES.register(modEventBus);
		DimensionInit.MOD_DIMENSIONS.register(modEventBus);

		instance = this;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		BlockInit.BLOCKS.getEntries().stream()
				.filter(block -> !(block.get() instanceof ExampleCrop) && !(block.get() instanceof FlowingFluidBlock))
				.map(RegistryObject::get).forEach(block -> {
					final Item.Properties properties = new Item.Properties().group(TutorialItemGroup.instance);
					final BlockItem blockItem = new BlockItem(block, properties);
					blockItem.setRegistryName(block.getRegistryName());
					registry.register(blockItem);
				});

		LOGGER.debug("Registered BlockItems!");
	}

	@SubscribeEvent
	public static void onRegisterBiomes(final RegistryEvent.Register<Biome> event) {
		BiomeInit.registerBiomes();
	}

	private void setup(final FMLCommonSetupEvent event) {// K9#8016 or the official IRC channel (link at http://mcpbot.bspk.rs/, under "How do I use MCPBot?").
		DeferredWorkQueue.runLater(() -> ComposterBlock.registerCompostable(0.6f, BlockInit.JAZZ_LEAVES.get()));
		DeferredWorkQueue.runLater(() -> ComposterBlock.registerCompostable(0.4f, ItemInit.SEED_ITEM.get()));
		DeferredWorkQueue.runLater(TutorialOreGen::generateOre);
		/*
		 * DeferredWorkQueue.runLater(() -> { for (Biome biome : ForgeRegistries.BIOMES)
		 * { if (biome instanceof ExampleBiome) {
		 * biome.getSpawns(EntityClassification.MONSTER) .add(new
		 * Biome.SpawnListEntry(EntityType.ZOMBIE, 1000, 1, 4)); } } });
		 */
	}

	/*
	 * public static void registerPlacementType(EntityType type,
	 * EntitySpawnPlacementRegistry.PlacementType placementType) {
	 * EntitySpawnPlacementRegistry.register(type, placementType,
	 * Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
	 * MonsterEntity::canMonsterSpawnInLight); }
	 */

	@SubscribeEvent
	public static void onServerStarting(FMLServerStartingEvent event) { //This is fired on the MinecraftForge#EVENT_BUS, not the Mod bus,

	}

	@SubscribeEvent
	public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
		// This doesnt work anymore
		// TutorialOreGen.generateOre();
	}

	public static class TutorialItemGroup extends ItemGroup {
		public static final ItemGroup instance = new TutorialItemGroup(ItemGroup.GROUPS.length, "tutorialtab");

		private TutorialItemGroup(int index, String label) {
			super(index, label);
		}

		@Override
		public ItemStack createIcon() {
			return new ItemStack(BlockInit.EXAMPLE_BLOCK.get());
		}
	}
}
