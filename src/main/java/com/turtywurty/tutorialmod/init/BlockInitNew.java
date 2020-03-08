package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInitNew {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

	public static final RegistryObject<Block> DEF_BLOCK = BLOCKS.register("def_block", () -> new Block(Block.Properties.create(Material.IRON)));
}
