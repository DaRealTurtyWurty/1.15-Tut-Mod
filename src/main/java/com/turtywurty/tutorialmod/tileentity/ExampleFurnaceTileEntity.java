package com.turtywurty.tutorialmod.tileentity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.turtywurty.tutorialmod.TutorialMod;
import com.turtywurty.tutorialmod.container.ExampleFurnaceContainer;
import com.turtywurty.tutorialmod.init.ModTileEntityTypes;
import com.turtywurty.tutorialmod.init.RecipeSerializerInit;
import com.turtywurty.tutorialmod.objects.blocks.ExampleFurnaceBlock;
import com.turtywurty.tutorialmod.recipes.ExampleRecipe;
import com.turtywurty.tutorialmod.util.ExampleItemHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ExampleFurnaceTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	private ITextComponent customName;
	public int currentSmeltTime;
	public final int maxSmeltTime = 100;
	private ExampleItemHandler inventory;

	public ExampleFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);

		this.inventory = new ExampleItemHandler(2);
	}

	public ExampleFurnaceTileEntity() {
		this(ModTileEntityTypes.EXAMPLE_FURNACE.get());
	}

	@Override
	public Container createMenu(final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
		return new ExampleFurnaceContainer(windowID, playerInv, this);
	}

	@Override
	public void tick() {
		boolean dirty = false;

		if (this.world != null && !this.world.isRemote) {
			if (this.world.isBlockPowered(this.getPos())) {
				if (this.getRecipe(this.inventory.getStackInSlot(0)) != null) {
					if (this.currentSmeltTime != this.maxSmeltTime) {
						this.world.setBlockState(this.getPos(),
								this.getBlockState().with(ExampleFurnaceBlock.LIT, true));
						this.currentSmeltTime++;
						dirty = true;
					} else {
						this.world.setBlockState(this.getPos(),
								this.getBlockState().with(ExampleFurnaceBlock.LIT, false));
						this.currentSmeltTime = 0;
						ItemStack output = this.getRecipe(this.inventory.getStackInSlot(0)).getRecipeOutput();
						this.inventory.insertItem(1, output.copy(), false);
						this.inventory.decrStackSize(0, 1);
						dirty = true;
					}
				}
			}
		}

		if (dirty) {
			this.markDirty();
			this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(),
					Constants.BlockFlags.BLOCK_UPDATE);
		}
	}

	public void setCustomName(ITextComponent name) {
		this.customName = name;
	}

	public ITextComponent getName() {
		return this.customName != null ? this.customName : this.getDefaultName();
	}

	private ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + TutorialMod.MOD_ID + ".example_furnace");
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.getName();
	}

	@Nullable
	public ITextComponent getCustomName() {
		return this.customName;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
			this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
		}

		NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, inv);
		this.inventory.setNonNullList(inv);

		this.currentSmeltTime = compound.getInt("CurrentSmeltTime");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (this.customName != null) {
			compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
		}

		ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());
		compound.putInt("CurrentSmeltTime", this.currentSmeltTime);

		return compound;
	}

	@Nullable
	private ExampleRecipe getRecipe(ItemStack stack) {
		if (stack == null) {
			return null;
		}

		Set<IRecipe<?>> recipes = findRecipesByType(RecipeSerializerInit.EXAMPLE_TYPE, this.world);
		for (IRecipe<?> iRecipe : recipes) {
			ExampleRecipe recipe = (ExampleRecipe) iRecipe;
			if (recipe.matches(new RecipeWrapper(this.inventory), this.world)) {
				return recipe;
			}
		}

		return null;
	}

	public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn, World world) {
		return world != null ? world.getRecipeManager().getRecipes().stream()
				.filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
	}

	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn) {
		ClientWorld world = Minecraft.getInstance().world;
		return world != null ? world.getRecipeManager().getRecipes().stream()
				.filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
	}

	public static Set<ItemStack> getAllRecipeInputs(IRecipeType<?> typeIn, World worldIn) {
		Set<ItemStack> inputs = new HashSet<ItemStack>();
		Set<IRecipe<?>> recipes = findRecipesByType(typeIn, worldIn);
		for (IRecipe<?> recipe : recipes) {
			NonNullList<Ingredient> ingredients = recipe.getIngredients();
			ingredients.forEach(ingredient -> {
				for (ItemStack stack : ingredient.getMatchingStacks()) {
					inputs.add(stack);
				}
			});
		}
		return inputs;
	}

	public final IItemHandlerModifiable getInventory() {
		return this.inventory;
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbt = new CompoundNBT();
		this.write(nbt);
		return new SUpdateTileEntityPacket(this.pos, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(pkt.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		this.write(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(CompoundNBT nbt) {
		this.read(nbt);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
	}
}
