package com.turtywurty.tutorialmod.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ExampleEffect extends Effect {

	public ExampleEffect(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		entityLivingBaseIn.setHealth(amplifier * 5.0F);
		entityLivingBaseIn.setJumping(true);
		entityLivingBaseIn.setBeeStingCount(5 * amplifier);
	}
}
