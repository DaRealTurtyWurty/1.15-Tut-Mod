package com.turtywurty.tutorialmod.client.tileentity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.turtywurty.tutorialmod.tileentity.ItemPedestalTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class ItemPedestalRenderer extends TileEntityRenderer<ItemPedestalTileEntity> {

	private float degrees;

	public ItemPedestalRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		degrees = 0.0f;
	}

	/*
	 * This method is called every tick and is where all the rendering should take
	 * place. In here, I first lopp through all the stacks in the tile entity's
	 * items.
	 * 
	 * Then, if the stack is not empty, we push the matrix. This means that it is
	 * ready to start doing any transformations that you give it. We always push
	 * before doing any rendering, otherwise it will either not render, or act
	 * unpredicatably.
	 * 
	 * Then I am translating the matrix to the center, and 0.5D above the block.
	 * 
	 * Then I am getting the current world time from the game time plus the partial
	 * ticks.
	 * 
	 * Then I am translating the Y on a sine wave(taking in radians) so it's
	 * sin(3.14159... * (currentTime/16)). We divide the time by 16 to decrease the
	 * frequency(speed), and divide the whole thing by 4 to decrease the
	 * amplitude(size). And then I add 0.1D so that it offsets it up by 0.1!
	 * 
	 * Then I rotate on the Y positive(clockwise) with our degrees incremented,
	 * divided by 2 to constantly rotate it.
	 * 
	 * Then we call the renderItem method.
	 * 
	 * And finally we matrixStackIn.pop(), so that it knows that it is now done with
	 * rendering and doesn't cause any overflows or anything.
	 */
	@Override
	public void render(ItemPedestalTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		NonNullList<ItemStack> items = tileEntityIn.getItems();
		for (ItemStack stack : items) {
			if (!stack.isEmpty()) {
				matrixStackIn.push();
				matrixStackIn.translate(0.5D, 1.5D, 0.5D);
				float currentTime = tileEntityIn.getWorld().getGameTime() + partialTicks;
				matrixStackIn.translate(0D, (Math.sin(Math.PI * currentTime / 16) / 4) + 0.1D, 0D);
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degrees++ / 2));
				renderItem(stack, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
				matrixStackIn.pop();
			}
		}
	}

	/*
	 * This method calls the existing method that minecraft has, but with our stack.
	 * It also takes TransformType.FIXED as that is how the item will be
	 * transformed. We also put in OverlayTexture.NO_OVERLAY to ensure that no
	 * overlay textures are displayed.
	 */
	private void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn) {
		Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.FIXED, combinedLightIn,
				OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
	}
}
