package com.turtywurty.tutorialmod.util;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import net.minecraft.util.IntReferenceHolder;

public class FunctionalIntReferenceHolder extends IntReferenceHolder {

	private final IntSupplier getter;
	private final IntConsumer setter;

	public FunctionalIntReferenceHolder(final IntSupplier getter, final IntConsumer setter) {
		this.getter = getter;
		this.setter = setter;
	}

	@Override
	public int get() {
		return this.getter.getAsInt();
	}

	@Override
	public void set(final int value) {
		this.setter.accept(value);
	}
}
