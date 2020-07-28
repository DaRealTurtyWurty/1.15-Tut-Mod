package com.turtywurty.tutorialmod.entities;

import com.turtywurty.tutorialmod.init.ItemInit;
import com.turtywurty.tutorialmod.init.ModEntityTypes;
import com.turtywurty.tutorialmod.init.SoundInit;
import com.turtywurty.tutorialmod.particles.ColouredParticle.ColouredParticleData;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.event.ParticleKeyFrameEvent;
import software.bernie.geckolib.event.SoundKeyframeEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class ExampleEntity extends AnimalEntity implements IAnimatedEntity {

	private EatGrassGoal eatGrassGoal;
	private int exampleTimer;
	private EntityAnimationManager manager = new EntityAnimationManager();
	private AnimationController controller = new EntityAnimationController(this, "moveController", 20,
			this::animationPredicate);

	public ExampleEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		registerAnimationControllers();
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		ExampleEntity entity = new ExampleEntity(ModEntityTypes.EXAMPLE_ENTITY.get(), this.world);
		entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)),
				SpawnReason.BREEDING, (ILivingEntityData) null, (CompoundNBT) null);
		entity.setGlowing(true);
		return entity;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.eatGrassGoal = new EatGrassGoal(this);
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.fromItems(ItemInit.DEF_ITEM.get()), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, this.eatGrassGoal);
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	@Override
	protected void updateAITasks() {
		this.exampleTimer = this.eatGrassGoal.getEatingGrassTimer();
		super.updateAITasks();
	}

	@Override
	public void livingTick() {
		if (this.world.isRemote) {
			this.exampleTimer = Math.max(0, this.exampleTimer - 1);
		}
		super.livingTick();
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 10) {
			this.exampleTimer = 40;
		} else {
			super.handleStatusUpdate(id);
		}

	}

	@OnlyIn(Dist.CLIENT)
	public float getHeadRotationPointY(float p_70894_1_) {
		if (this.exampleTimer <= 0) {
			return 0.0F;
		} else if (this.exampleTimer >= 4 && this.exampleTimer <= 36) {
			return 1.0F;
		} else {
			return this.exampleTimer < 4 ? ((float) this.exampleTimer - p_70894_1_) / 4.0F
					: -((float) (this.exampleTimer - 40) - p_70894_1_) / 4.0F;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getHeadRotationAngleX(float p_70890_1_) {
		if (this.exampleTimer > 4 && this.exampleTimer <= 36) {
			float f = ((float) (this.exampleTimer - 4) - p_70890_1_) / 32.0F;
			return ((float) Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
		} else {
			return this.exampleTimer > 0 ? ((float) Math.PI / 5F) : this.rotationPitch * ((float) Math.PI / 180F);
		}
	}

	@Override
	public void onStruckByLightning(LightningBoltEntity lightningBolt) {
		this.setGlowing(true);
	}

	protected SoundEvent getEntityAmbientSound() {
		return SoundInit.AMBIENT.get();
	}

	@Override
	public EntityAnimationManager getAnimationManager() {
		return manager;
	}

	private <E extends ExampleEntity> boolean animationPredicate(AnimationTestEvent<E> event) {
		if (event.isWalking()) {
			controller.setAnimation(new AnimationBuilder().addAnimation("animation.tutorialmod.walk")
					.addAnimation("animation.tutorialmod.walk", true));
			return true;
		} else {
			controller.setAnimation(new AnimationBuilder().addAnimation("animation.tutorialmod.idle", true));
			return true;
		}
	}

	private <E extends Entity> SoundEvent soundListener(SoundKeyframeEvent<E> event) {
		if (event.sound.equals("test")) {
			return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValues().toArray()[rand
					.nextInt(ForgeRegistries.SOUND_EVENTS.getValues().size())];
		} else {
			return getEntityAmbientSound();
		}
	}

	private <E extends Entity> void particleListener(ParticleKeyFrameEvent<E> event) {
		if (event.effect.equals("test")) {
			event.getEntity().getEntityWorld().addParticle(new ColouredParticleData(0.2f, 0.7f, 0.5f, 1.0f),
					event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), 0.0f, 0.4f,
					0.0f);
		}
	}

	private void registerAnimationControllers() {
		manager.addAnimationController(controller);
		controller.registerSoundListener(this::soundListener);
		controller.registerParticleListener(this::particleListener);
	}
}
