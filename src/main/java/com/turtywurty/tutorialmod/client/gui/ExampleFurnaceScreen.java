package com.turtywurty.tutorialmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.container.ExampleFurnaceContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExampleFurnaceScreen extends ContainerScreen<ExampleFurnaceContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID,
			"textures/gui/example_furnace.png");

	public ExampleFurnaceScreen(ExampleFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		this.blit(this.guiLeft + 79, this.guiTop + 35, 176, 0, this.container.getSmeltProgressionScaled(), 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.font.drawString(this.title.getFormattedText(), 8.0f, 8.0f, 0x404040);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0f, 69.0f, 0x404040);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
