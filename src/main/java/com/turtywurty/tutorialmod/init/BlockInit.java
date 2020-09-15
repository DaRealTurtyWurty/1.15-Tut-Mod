package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.objects.blocks.BlockQuarry;
import com.turtywurty.tutorialmod.objects.blocks.ExampleChestBlock;
import com.turtywurty.tutorialmod.objects.blocks.ExampleCrop;
import com.turtywurty.tutorialmod.objects.blocks.ExampleFurnaceBlock;
import com.turtywurty.tutorialmod.objects.blocks.ItemPedestalBlock;
import com.turtywurty.tutorialmod.objects.blocks.JazzDoor;
import com.turtywurty.tutorialmod.objects.blocks.ModLadderBlock;
import com.turtywurty.tutorialmod.objects.blocks.ModPressurePlateBlock;
import com.turtywurty.tutorialmod.objects.blocks.ModSaplingBlock;
import com.turtywurty.tutorialmod.objects.blocks.ModTorchBlock;
import com.turtywurty.tutorialmod.objects.blocks.ModWallTorchBlock;
import com.turtywurty.tutorialmod.objects.blocks.ModWoodButtonBlock;
import com.turtywurty.tutorialmod.objects.blocks.SpecalBlock;
import com.turtywurty.tutorialmod.world.feature.JazzTree;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,
			TutorialMod.MOD_ID);

	public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(
			Block.Properties.create(Material.IRON).hardnessAndResistance(0.5f, 15.0f).sound(SoundType.SAND)));

	public static final RegistryObject<Block> SPECAL_BLOCK = BLOCKS.register("specal_block",
			() -> new SpecalBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.0f, 10.0f)
					.harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.GLASS).lightValue(4)
					.slipperiness(1.2f).speedFactor(0.7f).noDrops()));

	public static final RegistryObject<Block> QUARRY = BLOCKS.register("quarry",
			() -> new BlockQuarry(Block.Properties.create(Material.IRON)));

	public static final RegistryObject<Block> DEF_BLOCK = BLOCKS.register("def_block",
			() -> new Block(Block.Properties.create(Material.IRON)));

	public static final RegistryObject<Block> EXAMPLE_STAIRS = BLOCKS.register("example_stairs",
			() -> new StairsBlock(() -> BlockInit.EXAMPLE_BLOCK.get().getDefaultState(),
					Block.Properties.create(Material.SAND, MaterialColor.GOLD)));
	public static final RegistryObject<Block> EXAMPLE_FENCE = BLOCKS.register("example_fence",
			() -> new FenceBlock(Block.Properties.create(Material.SAND, MaterialColor.GOLD)));
	public static final RegistryObject<Block> EXAMPLE_BUTTON = BLOCKS.register("example_button",
			() -> new ModWoodButtonBlock(Block.Properties.create(Material.SAND, MaterialColor.GOLD)));
	public static final RegistryObject<Block> EXAMPLE_PRESSURE_PLATE = BLOCKS.register("example_pressure_plate",
			() -> new ModPressurePlateBlock(Sensitivity.EVERYTHING,
					Block.Properties.create(Material.SAND, MaterialColor.GOLD)));

	public static final RegistryObject<Block> EXAMPLE_CHEST = BLOCKS.register("example_chest",
			() -> new ExampleChestBlock(Block.Properties.from(BlockInit.DEF_BLOCK.get())));

	public static final RegistryObject<Block> JAZZ_PLANKS = BLOCKS.register("jazz_planks",
			() -> new Block(Block.Properties.from(Blocks.OAK_PLANKS)));

	public static final RegistryObject<Block> JAZZ_LOG = BLOCKS.register("jazz_log",
			() -> new LogBlock(MaterialColor.WOOD, Block.Properties.from(Blocks.OAK_LOG)));

	public static final RegistryObject<Block> JAZZ_LEAVES = BLOCKS.register("jazz_leaves",
			() -> new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES)));

	public static final RegistryObject<Block> JAZZ_SAPLING = BLOCKS.register("jazz_sapling",
			() -> new ModSaplingBlock(() -> new JazzTree(), Block.Properties.from(Blocks.OAK_SAPLING)));

	public static final RegistryObject<Block> JAZZ_SLAB = BLOCKS.register("jazz_slab",
			() -> new SlabBlock(Block.Properties.from(BlockInit.JAZZ_PLANKS.get())));

	public static final RegistryObject<Block> EXAMPLE_CROP = BLOCKS.register("example_crop",
			() -> new ExampleCrop(Block.Properties.from(Blocks.WHEAT)));

	public static final RegistryObject<Block> JAZZ_DOOR = BLOCKS.register("jazz_door",
			() -> new JazzDoor(Block.Properties.from(Blocks.OAK_DOOR)));

	public static final RegistryObject<Block> ITEM_PEDESTAL = BLOCKS.register("item_pedestal",
			() -> new ItemPedestalBlock(Block.Properties.from(Blocks.ANVIL)));

	public static final RegistryObject<Block> EXAMPLE_FURNACE = BLOCKS.register("example_furnace",
			() -> new ExampleFurnaceBlock(Block.Properties.from(Blocks.FURNACE)));

	public static final RegistryObject<FenceGateBlock> EXAMPLE_FENCE_GATE = BLOCKS.register("example_fence_gate",
			() -> new FenceGateBlock(Block.Properties.from(Blocks.ACACIA_FENCE_GATE)));

	public static final RegistryObject<WallBlock> EXAMPLE_WALL = BLOCKS.register("example_wall",
			() -> new WallBlock(Block.Properties.from(Blocks.BRICK_WALL)));

	public static final RegistryObject<ModTorchBlock> EXAMPLE_TORCH = BLOCKS.register("example_torch",
			() -> new ModTorchBlock(Block.Properties.from(Blocks.TORCH)));

	public static final RegistryObject<ModWallTorchBlock> EXAMPLE_WALL_TORCH = BLOCKS.register("example_wall_torch",
			() -> new ModWallTorchBlock(Block.Properties.from(Blocks.WALL_TORCH)));

	public static final RegistryObject<ModLadderBlock> EXAMPLE_LADDER = BLOCKS.register("example_ladder",
			() -> new ModLadderBlock(Block.Properties.from(Blocks.LADDER)));
}
