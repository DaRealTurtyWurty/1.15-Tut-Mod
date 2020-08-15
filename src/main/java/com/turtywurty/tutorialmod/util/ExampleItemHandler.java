package com.turtywurty.tutorialmod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class ExampleItemHandler extends ItemStackHandler {

	public ExampleItemHandler(int size, ItemStack... stacks) {
		super(size);

		for (int index = 0; index < stacks.length; index++) {
			this.stacks.set(index, stacks[index]);
		}
	}

	public void clear() {
		for (int index = 0; index < this.getSlots(); index++) {
			this.stacks.set(index, ItemStack.EMPTY);
			this.onContentsChanged(index);
		}
	}

	public boolean isEmpty() {
		for (ItemStack stack : this.stacks) {
			if (stack.isEmpty() || stack.getItem() == Items.AIR) {
				return true;
			}
		}
		return false;
	}

	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		stack.shrink(count);
		this.onContentsChanged(index);
		return stack;
	}

	public void removeStackFromSlot(int index) {
		this.stacks.set(index, ItemStack.EMPTY);
		this.onContentsChanged(index);
	}

	public NonNullList<ItemStack> toNonNullList() {
		NonNullList<ItemStack> items = NonNullList.create();
		for (ItemStack stack : this.stacks) {
			items.add(stack);
		}
		return items;
	}

	public void setNonNullList(NonNullList<ItemStack> items) {
		if (items.size() == 0)
			return;
		if (items.size() != this.getSlots())
			throw new IndexOutOfBoundsException("NonNullList must be same size as ItemStackHandler!");
		for (int index = 0; index < items.size(); index++) {
			this.stacks.set(index, items.get(index));
		}
	}

	@Override
	public String toString() {
		return this.stacks.toString();
	}
}
