package com.turtywurty.tutorialmod.container;

import java.util.Objects;

import com.turtywurty.tutorialmod.init.BlockInit;
import com.turtywurty.tutorialmod.init.ModContainerTypes;
import com.turtywurty.tutorialmod.tileentity.ItemPedestalTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class ItemPedestalContainer extends Container {

	public final ItemPedestalTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	/*
	 * In this constructor we first add the slot for our showcase item to go in.
	 * When we add a slot, it first takes in the inventory that we add it to, then
	 * the index of the slot. Then it takes the left and top positions of the slot.
	 * 
	 * We then do this for the main inventory but instead we loop through the 3 rows
	 * and 9 columns. We add this slot to the player inventory, with an index of 9 +
	 * (row * 9) + column. The reason we do that is because it has to be greater
	 * than 9, as 0 - 9 is the hotbar. Then we do row multiplied by 9, so this knows
	 * to increase by another 9 for each row that it goes down. Finally we add the
	 * column so that we can do how far along the row we are. Then the x position is
	 * the starting X position of the first slot in the inventory, plus the number
	 * of the column that we are in multiplied slotSizePlus2(18) so that we know how
	 * many pixels there are between slots. For the Y position, it is the same, but
	 * with rows instead of columns.
	 * 
	 * Then we do the same for the hotbar except we only need 1 for loop, and thats
	 * for the 9 columns. We don't need a loop for the rows as there is only the 1
	 * row. We once again add a slot in the player's inventory with an index of the
	 * column. The X position that we set it so is the starting X position of the
	 * first slot in the hotbar plus slotSizePlus2(18 - which is the number of
	 * pixels between slots), multiplied by the column that we are currently in.
	 * Then we can just hardcode the Y position as 142 as that doesn't change in the
	 * hotbar.
	 */
	public ItemPedestalContainer(final int windowId, final PlayerInventory playerInv,
			final ItemPedestalTileEntity tileEntityIn) {
		super(ModContainerTypes.ITEM_PEDESTAL.get(), windowId);
		this.tileEntity = tileEntityIn;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntityIn.getWorld(), tileEntityIn.getPos());

		this.addSlot(new Slot(tileEntityIn, 0, 81, 36));

		// Main Inventory
		int startX = 8;
		int startY = 84;
		int slotSizePlus2 = 18;
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
						startY + (row * slotSizePlus2)));
			}
		}

		// Hotbar
		for (int column = 0; column < 9; column++) {
			this.addSlot(new Slot(playerInv, column, startX + (column * slotSizePlus2), 142));
		}
	}

	public ItemPedestalContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}

	/*
	 * This method allows us to get the tile entity from the packet buffer. So we
	 * first check that the packet buffer and player inventory are not null. Then we
	 * get the tile from the data's stored position, and check if that tile is an
	 * instance of ours. If its not we want to throw an exception as that shouldnt
	 * happen.
	 */
	private static ItemPedestalTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
		Objects.requireNonNull(playerInv, "playerInv cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof ItemPedestalTileEntity) {
			return (ItemPedestalTileEntity) tileAtPos;
		}
		throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
	}

	/*
	 * This method checks whether the player can interact with this tile entity or
	 * not.
	 */
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.ITEM_PEDESTAL.get());
	}

	/*
	 * This method is for transfering the stack into a slot(for example when you
	 * shift click). We have to override this method as the vanilla one wasn't built
	 * to work with modded containers. So we first check that the slot from the
	 * index is not null and the slot has a stack. Then we copy the stack and check
	 * that the index is less than the max index size(which in our case is just 1).
	 * Then we check that we cannot merge the stack and if we can't, we return an
	 * empty stack. If the index isn't less than 1 then we check that we cannot
	 * merge the stack once again and if we can't return we return an empty stack
	 * again. If the stack is empty then we put an empty stack in the slot. Else we
	 * call slot.onSlotChanged(), which runs any necessary code such as events for
	 * when a slot is changed. And in the end we just return the stack.
	 */
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 1) {
				if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}
