package com.turtywurty.tutorialmod.init;

import com.turtywurty.tutorialmod.TutorialMod;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit {

	public static final ResourceLocation MILK_STILL_RL = new ResourceLocation(TutorialMod.MOD_ID, "blocks/milk_still");
	public static final ResourceLocation MILK_FLOWING_RL = new ResourceLocation(TutorialMod.MOD_ID,
			"blocks/milk_flowing");
	public static final ResourceLocation MILK_OVERLAY_RL = new ResourceLocation(TutorialMod.MOD_ID,
			"blocks/milk_overlay");

	public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<Fluid>(ForgeRegistries.FLUIDS,
			TutorialMod.MOD_ID);

	public static final RegistryObject<FlowingFluid> MILK_FLUID = FLUIDS.register("milk_fluid",
			() -> new ForgeFlowingFluid.Source(FluidInit.MILK_PROPERTIES));

	public static final RegistryObject<FlowingFluid> MILK_FLOWING = FLUIDS.register("milk_flowing",
			() -> new ForgeFlowingFluid.Flowing(FluidInit.MILK_PROPERTIES));

	public static final ForgeFlowingFluid.Properties MILK_PROPERTIES = new ForgeFlowingFluid.Properties(
			() -> MILK_FLUID.get(), () -> MILK_FLOWING.get(),
			FluidAttributes.builder(MILK_STILL_RL, MILK_FLOWING_RL).density(5).luminosity(10).rarity(Rarity.RARE)
					.sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).overlay(MILK_OVERLAY_RL))
							.block(() -> FluidInit.MILK_BLOCK.get()).bucket(() -> ItemInit.EXAMPLE_BUCKET.get());

	public static final RegistryObject<FlowingFluidBlock> MILK_BLOCK = BlockInit.BLOCKS.register("milk",
			() -> new FlowingFluidBlock(() -> FluidInit.MILK_FLUID.get(), Block.Properties.create(Material.WATER)
					.doesNotBlockMovement().hardnessAndResistance(100.0f).noDrops()));
}
