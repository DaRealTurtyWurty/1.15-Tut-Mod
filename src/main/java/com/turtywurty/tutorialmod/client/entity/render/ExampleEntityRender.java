package com.turtywurty.tutorialmod.client.entity.render;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.client.entity.model.ExampleEntityModel;
import com.turtywurty.tutorialmod.entities.ExampleEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ExampleEntityRender extends MobRenderer<ExampleEntity, ExampleEntityModel> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID,
			"textures/entity/example_entity.png");

	public ExampleEntityRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new ExampleEntityModel(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(ExampleEntity entity) {
		return TEXTURE;
	}
}
