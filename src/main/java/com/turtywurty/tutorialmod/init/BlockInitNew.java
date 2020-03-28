package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.objects.blocks.ExampleChestBlock;
import com.turtywurty.tutorialmod.objects.blocks.ModPressurePlateBlock;
import com.turtywurty.tutorialmod.objects.blocks.ModSaplingBlock;
import com.turtywurty.tutorialmod.objects.blocks.ModWoodButtonBlock;
import com.turtywurty.tutorialmod.world.feature.JazzTree;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInitNew {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,
			TutorialMod.MOD_ID);

	public static final RegistryObject<Block> DEF_BLOCK = BLOCKS.register("def_block",
			() -> new Block(Block.Properties.create(Material.IRON)));

	public static final RegistryObject<Block> EXAMPLE_STAIRS = BLOCKS.register("example_stairs",
			() -> new StairsBlock(() -> BlockInit.example_block.getDefaultState(),
					Block.Properties.create(Material.SAND, MaterialColor.GOLD)));
	public static final RegistryObject<Block> EXAMPLE_FENCE = BLOCKS.register("example_fence",
			() -> new FenceBlock(Block.Properties.create(Material.SAND, MaterialColor.GOLD)));
	public static final RegistryObject<Block> EXAMPLE_BUTTON = BLOCKS.register("example_button",
			() -> new ModWoodButtonBlock(Block.Properties.create(Material.SAND, MaterialColor.GOLD)));
	public static final RegistryObject<Block> EXAMPLE_PRESSURE_PLATE = BLOCKS.register("example_pressure_plate",
			() -> new ModPressurePlateBlock(Sensitivity.EVERYTHING,
					Block.Properties.create(Material.SAND, MaterialColor.GOLD)));

	public static final RegistryObject<Block> EXAMPLE_CHEST = BLOCKS.register("example_chest",
			() -> new ExampleChestBlock(Block.Properties.from(BlockInitNew.DEF_BLOCK.get())));

	public static final RegistryObject<Block> JAZZ_PLANKS = BLOCKS.register("jazz_planks",
			() -> new Block(Block.Properties.from(Blocks.OAK_PLANKS)));

	public static final RegistryObject<Block> JAZZ_LOG = BLOCKS.register("jazz_log",
			() -> new LogBlock(MaterialColor.WOOD, Block.Properties.from(Blocks.OAK_LOG)));

	public static final RegistryObject<Block> JAZZ_LEAVES = BLOCKS.register("jazz_leaves",
			() -> new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES)));

	public static final RegistryObject<Block> JAZZ_SAPLING = BLOCKS.register("jazz_sapling",
			() -> new ModSaplingBlock(() -> new JazzTree(), Block.Properties.from(Blocks.OAK_SAPLING)));
}
