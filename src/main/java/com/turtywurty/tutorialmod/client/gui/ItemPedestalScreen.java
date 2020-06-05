package com.turtywurty.tutorialmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.container.ItemPedestalContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ItemPedestalScreen extends ContainerScreen<ItemPedestalContainer> {

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(TutorialMod.MOD_ID,
			"textures/gui/item_pedestal.png");

	/*
	 * In this constructor we set the gui's top left position to (0, 0); where we
	 * want to start drawing the GUI from. Then we set the X size and Y size to the
	 * size of the GUI that we want to be drawn.
	 */
	public ItemPedestalScreen(ItemPedestalContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
	}

	/*
	 * This method is called every tick and does the basic rendering of the GUI,
	 * rendering the background, and rendering any hovering tool tips, if the user
	 * does happen to have their mouse over it.
	 */
	@Override
	public void render(final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	/*
	 * This method is called every tick and is for drawing everything in front of
	 * the background, that being slots, tooltips, and anything that is infront. In
	 * here we draw the 2 strings for the name of this screen and the name of the
	 * player inventory.
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.font.drawString(this.title.getFormattedText(), 8.0f, 8.0f, 4210752);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F,
				(float) (this.ySize - 96 + 2), 4210752);
	}

	/*
	 * This method is called every tick and is for drawing everything in the
	 * background(behind the slots and any popups). In here we first use
	 * RenderSystem.color4f, which makes it do any following rendering operations
	 * with full red, green, blue and alpha. Then we bind the background texture to
	 * this screen. Then we get the X and Y by taking the xSize and ySize from the
	 * width and height, and dividing those values by 2. This allows us to get the
	 * position of where we should start drawing the texture. Then we call the blit
	 * method which takes in the X and Y positions that we want to start drawing at.
	 * Then the location in the texture that we want to start drawing from, in our
	 * case the top left(0, 0). Then the width and height that we are drawing(which
	 * is our X and Y size).
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(x, y, 0, 0, this.xSize, this.ySize);
	}
}
