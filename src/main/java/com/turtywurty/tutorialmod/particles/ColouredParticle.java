package com.turtywurty.tutorialmod.particles;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.turtywurty.tutorialmod.init.ParticleInit;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ColouredParticle extends SpriteTexturedParticle {

	private double posX, posY, posZ;

	public ColouredParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, ColouredParticleData data, IAnimatedSprite sprite) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

		this.motionX = xSpeedIn;
		this.motionY = ySpeedIn;
		this.motionZ = zSpeedIn;
		this.posX = xCoordIn;
		this.posY = yCoordIn;
		this.posZ = zCoordIn;
		this.particleScale = 0.1f * (this.rand.nextFloat() * 0.2f + 1.7f);
		float f = (float) Math.random() * 0.4F + 0.6F;
		this.particleRed = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getRed() * f;
		this.particleGreen = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getGreen() * f;
		this.particleBlue = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getBlue() * f;
		this.maxAge = (int) (Math.random() * 10.0d) + 40;
	}

	@Override
	public void tick() {
		this.prevPosX = posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		} else {
			float f = (float) this.age / (float) this.maxAge;
			float f1 = -f + f * f * 2.0F;
			float f2 = 1.0F - f1;
			this.posX = this.posX + this.motionX * (double) f2;
			this.posY = this.posY + this.motionY * (double) f2 + (double) (0.2F - f);
			this.posZ = this.posZ + this.motionZ * (double) f2;
		}
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<ColouredParticleData> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteIn) {
			this.spriteSet = spriteIn;
		}

		public Particle makeParticle(ColouredParticleData typeIn, World worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			ColouredParticle particle = new ColouredParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn,
					spriteSet);
			particle.selectSpriteRandomly(spriteSet);
			return particle;
		}
	}

	public static class ColouredParticleData implements IParticleData {
		public static final IParticleData.IDeserializer<ColouredParticleData> DESERIALIZER = new IParticleData.IDeserializer<ColouredParticleData>() {
			public ColouredParticleData deserialize(ParticleType<ColouredParticleData> particleTypeIn,
					StringReader reader) throws CommandSyntaxException {
				reader.expect(' ');
				float red = (float) reader.readDouble();
				reader.expect(' ');
				float green = (float) reader.readDouble();
				reader.expect(' ');
				float blue = (float) reader.readDouble();
				reader.expect(' ');
				float alpha = (float) reader.readDouble();
				return new ColouredParticleData(red, green, blue, alpha);
			}

			public ColouredParticleData read(ParticleType<ColouredParticleData> particleTypeIn, PacketBuffer buffer) {
				return new ColouredParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(),
						buffer.readFloat());
			}
		};

		private final float red;
		private final float green;
		private final float blue;
		private final float alpha;

		public ColouredParticleData(float redIn, float greenIn, float blueIn, float alphaIn) {
			this.red = redIn;
			this.green = greenIn;
			this.blue = blueIn;
			this.alpha = MathHelper.clamp(alphaIn, 0.01f, 4.0f);
		}

		@Override
		public void write(PacketBuffer buffer) {
			buffer.writeFloat(this.red);
			buffer.writeFloat(this.green);
			buffer.writeFloat(this.blue);
			buffer.writeFloat(this.alpha);
		}

		@SuppressWarnings("deprecation")
		@Override
		public String getParameters() {
			return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()),
					this.red, this.green, this.blue, this.alpha);
		}

		@Override
		public ParticleType<ColouredParticleData> getType() {
			return ParticleInit.COLOURED_PARTICLE.get();
		}

		@OnlyIn(Dist.CLIENT)
		public float getRed() {
			return this.red;
		}

		@OnlyIn(Dist.CLIENT)
		public float getGreen() {
			return this.green;
		}

		@OnlyIn(Dist.CLIENT)
		public float getBlue() {
			return this.blue;
		}

		@OnlyIn(Dist.CLIENT)
		public float getAlpha() {
			return this.alpha;
		}
	}

}
