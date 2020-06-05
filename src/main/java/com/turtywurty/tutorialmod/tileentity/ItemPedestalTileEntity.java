package com.turtywurty.tutorialmod.tileentity;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.container.ItemPedestalContainer;
import com.turtywurty.tutorialmod.init.ModTileEntityTypes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

public class ItemPedestalTileEntity extends LockableLootTileEntity implements IClearable, INamedContainerProvider {

	/*
	 * This is the list of items that is non null. We tell it to have a size of 1
	 * and by default fill it with empty item stacks.
	 */
	protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

	public ItemPedestalTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public ItemPedestalTileEntity() {
		this(ModTileEntityTypes.ITEM_PEDESTAL.get());
	}

	/*
	 * This method is for reading data from NBT. In here we first call the super
	 * method to make sure that anything important is still being read. Then we set
	 * the items to a new non null list that has a size of 1 and is filled with
	 * empty item stacks by default. Then we use the utility method from
	 * ItemStackHelper to load all the items from NBT into that list.
	 */
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.items);
	}

	/*
	 * This method is for writing data to NBT. In here we first call the super
	 * method to make sure that anything important is still being saved. Then we use
	 * the utility method from ItemStackHelper to save all the items to NBT that is
	 * in our items list.
	 */
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, this.items);
		return compound;
	}

	/*
	 * This method is a getter for our items variable and allows us to get the items
	 * from other classes.
	 */
	@Override
	public NonNullList<ItemStack> getItems() {
		return this.items;
	}

	/*
	 * This method is a setter for our items variable and allows us to set the items
	 * for this tile from other classes.
	 */
	@Override
	public void setItems(NonNullList<ItemStack> itemsIn) {
		this.items = itemsIn;
	}

	/*
	 * This method marks the tile as 'dirty' which basically tells the game that it
	 * needs to read and write. So we call the super so that it can do that. Then we
	 * notify the world of a block update so that the client can recieve the
	 * changes.
	 */
	@Override
	public void markDirty() {
		super.markDirty();
		this.world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(),
				Constants.BlockFlags.BLOCK_UPDATE);
	}

	/*
	 * This method gets the name of this tile that will be used in several places
	 * such as our screen class. We return a translation text component so that the
	 * name of this tile can be translated in our lang to other languages.
	 */
	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + TutorialMod.MOD_ID + ".item_pedestal");
	}

	/* This method creates the container for this tile entity */
	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ItemPedestalContainer(id, player, this);
	}

	/*
	 * This method returns the size of this tile's inventory from our 'items'
	 * variable.
	 */
	@Override
	public int getSizeInventory() {
		return this.items.size();
	}

	/*
	 * This method is for checking whether all of items that this tile can hold are
	 * empty or not. So we loop through all the stacks in the stored items and check
	 * if its not empty. If its not empty then we return false. And if it is empty,
	 * we return true.
	 */
	@Override
	public boolean isEmpty() {
		for (ItemStack stack : this.items) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * This method allows us to get the stack in a certain slot index, and we do
	 * that by just getting the item in our items list at that index.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.items.get(index);
	}

	/*
	 * This method is for decreasing the size of the stack in the slot. Here we can
	 * use the utility method in ItemStackHelper which does the splitting math for
	 * us.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	/*
	 * This method is for removing a stack from a certain slot. Here we can use the
	 * utility method in ItemStackHelper which does the removing math for us.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	/*
	 * This method is for setting the slot contents for a particular slot. We first
	 * get the stack that is currently at that slot. We then create a boolean which
	 * checks is that stack is not empty, whether the stacks are equal and whether
	 * the tags in the stacks are equal. Then we set the the stack in that slot the
	 * the stack that is parsed in. We can then check if the stack's count is
	 * greater than the stack limit. If it is then we just delete the items that are
	 * greater than the limit. Finally we check that the flag is false, and if so
	 * mark dirty so that data can be saved.
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemStack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack)
				&& ItemStack.areItemStackTagsEqual(stack, itemStack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (!flag) {
			this.markDirty();
		}
	}

	/*
	 * This method checks whether this tile is usable by a particular player. We
	 * first check that the tile is not equal to this, and if its not then we
	 * obviously return false. Else we want to check that the distance squared is
	 * less than or equal to 64 blocks.
	 */
	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
					(double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	/*
	 * This method is for checking whether the stack at the specified slot index
	 * would be valid. I am just checking that the slot is not damaged, however this
	 * code is not necessary.
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return !stack.isDamaged();
	}

	/*
	 * This method is for clearing this tile's inventory. So we just clear the items
	 * in the list.
	 */
	@Override
	public void clear() {
		super.clear();
		this.items.clear();
	}

	/*
	 * When we call this.world.notifyBlockUpdate, this method is called on the
	 * server to generate a packet to send to the client. If you have lots of data,
	 * it's a good idea to keep track of which data has changed since the last time
	 * this TE was synced, and then only send the changed data; this reduces the
	 * amount of packets sent, which is good we only have one value to sync so we'll
	 * just write everything into the NBT again.
	 */
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbt = new CompoundNBT();
		this.write(nbt);

		return new SUpdateTileEntityPacket(this.getPos(), 1, nbt);
	}

	/*
	 * This method gets called on the client when it receives the packet that was
	 * sent in getUpdatePacket(). And here we just read the data from the packet
	 * that was recieved.
	 */
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(pkt.getNbtCompound());
	}

	/*
	 * This method is called to generate NBT for syncing a packet when a client
	 * loads a chunk that this tile entity is in. We want to tell the client as much
	 * data as it needs to know since it doesn't know any data at this current
	 * stage. We usually just need to put write() in here. If you ever have data
	 * that would be written to the disk but the client doesn't ever need to know,
	 * you can just sync the need-to-know data instead of calling write() there's an
	 * equivalent method for reading the update tag but it just defaults to read()
	 * anyway.
	 */
	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	/*
	 * This method is called after it has recieved an update tag to tell it what to
	 * do with that tag. All we do in here is read as we don't need to do anything
	 * else with it.
	 */
	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		this.read(tag);
	}
}
