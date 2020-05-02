package com.turtywurty.tutorialmod.enchantments;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.init.EnchantmentInit;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class UpstepEnchantment extends Enchantment {

	public UpstepEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
		super(rarityIn, typeIn, slots);
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return ench.equals(Enchantments.DEPTH_STRIDER) ? false : true;
	}

	@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.FORGE)
	public static class UpstepEquipped {

		@SubscribeEvent
		public static void doStuff(PlayerTickEvent event) {
			PlayerEntity playerIn = event.player;
			World worldIn = playerIn.world;
			if (playerIn.hasItemInSlot(EquipmentSlotType.FEET)
					&& EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.UPSTEP.get(),
							playerIn.getItemStackFromSlot(EquipmentSlotType.FEET)) > 0) {
				if (playerIn.isCrouching()) {
					if (worldIn.getBlockState(playerIn.getPosition().down()) == Blocks.BARRIER.getDefaultState()) {
						worldIn.setBlockState(playerIn.getPosition(), Blocks.AIR.getDefaultState());
						worldIn.setBlockState(playerIn.getPosition().down().down(), Blocks.BARRIER.getDefaultState());
					}
				} else {
					if (worldIn.getBlockState(playerIn.getPosition().down()) == Blocks.AIR.getDefaultState()) {
						worldIn.setBlockState(playerIn.getPosition().down(), Blocks.BARRIER.getDefaultState());
					}
				}
			}
		}
	}
}
