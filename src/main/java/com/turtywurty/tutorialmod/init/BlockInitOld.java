package com.turtywurty.tutorialmod.init;

/*import java.util.Set;
import java.util.stream.Stream;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.TutorialMod.TutorialItemGroup;
import com.turtywurty.tutorialmod.objects.blocks.BlockQuarry;
import com.turtywurty.tutorialmod.objects.blocks.SpecalBlock;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
//import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(TutorialMod.MOD_ID)
@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.MOD)*/
public class BlockInitOld {
	/*public static final Block example_block = null;
	public static final Block specal_block = null;
	public static final Block quarry = null;

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry()
				.register(new Block(
						Block.Properties.create(Material.IRON).hardnessAndResistance(0.5f, 15.0f).sound(SoundType.SAND))
								.setRegistryName("example_block"));
		event.getRegistry()
				.register(new SpecalBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.0f, 10.0f)
						.harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.GLASS).lightValue(4)
						.slipperiness(1.2f).speedFactor(0.7f).noDrops()).setRegistryName("specal_block"));
		// event.getRegistry().register(new
		// BlockTest(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5f,
		// 18.0f).sound(SoundType.WOOD).harvestLevel(1).harvestTool(ToolType.AXE)));
		event.getRegistry().register(new BlockQuarry(Block.Properties.create(Material.IRON)).setRegistryName("quarry"));
	}

	@SubscribeEvent
	public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(
				new BlockItem(example_block, new Item.Properties().maxStackSize(16).group(TutorialItemGroup.instance))
						.setRegistryName("example_block"));
		event.getRegistry()
				.register(new BlockItem(specal_block, new Item.Properties().group(TutorialItemGroup.instance))
						.setRegistryName("specal_block"));
		event.getRegistry().register(new BlockItem(quarry, new Item.Properties().group(TutorialItemGroup.instance))
				.setRegistryName("quarry"));
		// event.getRegistry().register(new BlockItem(test_block, new
		// Item.Properties().group(TutorialItemGroup.instance)).setRegistryName("test_block"));
	}

	public static class ModWoodType extends WoodType {
		private static final Set<WoodType> VALUES = new ObjectArraySet<>();
		public static final ModWoodType TEST = register(new ModWoodType("test"));
		private String name;

		public ModWoodType(String nameIn) {
			super(nameIn);
			this.name = nameIn;
		}

		private static ModWoodType register(ModWoodType woodTypeIn) {
			VALUES.add(woodTypeIn);
			return woodTypeIn;
		}

		@OnlyIn(Dist.CLIENT)
		public static Stream<WoodType> getValues() {
			return VALUES.stream();
		}

		@OnlyIn(Dist.CLIENT)
		public String getName() {
			return this.name;
		}
	}*/
}
