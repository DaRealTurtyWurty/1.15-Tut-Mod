package com.turtywurty.tutorialmod.client.entity.model;

import com.turtywurty.tutorialmod.entities.ExampleEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class ExampleEntityModel extends AnimatedEntityModel<ExampleEntity> {

	private final AnimatedModelRenderer Body;
	private final AnimatedModelRenderer Legs;
	private final AnimatedModelRenderer Back;
	private final AnimatedModelRenderer RightBack;
	private final AnimatedModelRenderer LeftBack;
	private final AnimatedModelRenderer Front;
	private final AnimatedModelRenderer RightFront;
	private final AnimatedModelRenderer LeftFront;
	private final AnimatedModelRenderer Head;

	public ExampleEntityModel() {
		textureWidth = 64;
		textureHeight = 64;
		Body = new AnimatedModelRenderer(this);
		Body.setRotationPoint(0.0F, 14.6667F, -2.3333F);
		Body.setTextureOffset(0, 0).addBox(-6.0F, -4.6667F, -7.6667F, 12.0F, 8.0F, 18.0F, 0.0F, false);
		Body.setModelRendererName("Body");
		this.registerModelRenderer(Body);

		Legs = new AnimatedModelRenderer(this);
		Legs.setRotationPoint(0.0F, 1.3333F, 2.3333F);
		Body.addChild(Legs);

		Legs.setModelRendererName("Legs");
		this.registerModelRenderer(Legs);

		Back = new AnimatedModelRenderer(this);
		Back.setRotationPoint(0.0F, 0.0F, 0.0F);
		Legs.addChild(Back);

		Back.setModelRendererName("Back");
		this.registerModelRenderer(Back);

		RightBack = new AnimatedModelRenderer(this);
		RightBack.setRotationPoint(-5.0F, 2.0F, 7.0F);
		Back.addChild(RightBack);
		RightBack.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		RightBack.setModelRendererName("RightBack");
		this.registerModelRenderer(RightBack);

		LeftBack = new AnimatedModelRenderer(this);
		LeftBack.setRotationPoint(5.0F, 2.0F, 7.0F);
		Back.addChild(LeftBack);
		LeftBack.setTextureOffset(6, 6).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		LeftBack.setModelRendererName("LeftBack");
		this.registerModelRenderer(LeftBack);

		Front = new AnimatedModelRenderer(this);
		Front.setRotationPoint(0.0F, 0.0F, 0.0F);
		Legs.addChild(Front);

		Front.setModelRendererName("Front");
		this.registerModelRenderer(Front);

		RightFront = new AnimatedModelRenderer(this);
		RightFront.setRotationPoint(-5.0F, 2.0F, -9.0F);
		Front.addChild(RightFront);
		RightFront.setTextureOffset(24, 26).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		RightFront.setModelRendererName("RightFront");
		this.registerModelRenderer(RightFront);

		LeftFront = new AnimatedModelRenderer(this);
		LeftFront.setRotationPoint(5.0F, 2.0F, -9.0F);
		Front.addChild(LeftFront);
		LeftFront.setTextureOffset(30, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
		LeftFront.setModelRendererName("LeftFront");
		this.registerModelRenderer(LeftFront);

		Head = new AnimatedModelRenderer(this);
		Head.setRotationPoint(0.0F, -3.6667F, -7.6667F);
		Body.addChild(Head);
		Head.setTextureOffset(0, 26).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
		Head.setModelRendererName("Head");
		this.registerModelRenderer(Head);

		this.rootBones.add(Body);
	}

	@Override
	public ResourceLocation getAnimationFileLocation() {
		return new ResourceLocation("tutorialmod", "animations/example_entity.json");
	}
}