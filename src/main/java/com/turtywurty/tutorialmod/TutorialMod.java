package com.turtywurty.tutorialmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.turtywurty.tutorialmod.init.BlockInit;
import com.turtywurty.tutorialmod.init.ModTileEntityTypes;
import com.turtywurty.tutorialmod.world.gen.TutorialOreGen;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("tutorialmod")
@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Bus.MOD)
public class TutorialMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "tutorialmod"; 
    public static TutorialMod instance;

    public TutorialMod() 
    {
    	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	modEventBus.addListener(this::setup);
    	modEventBus.addListener(this::doClientStuff);
    	
    	ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	
    }

    private void doClientStuff(final FMLClientSetupEvent event) 
    {
        
    }
    
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) 
    {
        
    }
    
    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) 
    {
    	TutorialOreGen.generateOre();
    }
    
    public static class TutorialItemGroup extends ItemGroup 
    {
    	public static final ItemGroup instance = new TutorialItemGroup(ItemGroup.GROUPS.length, "tutorialtab");
    	
    	private TutorialItemGroup(int index, String label)
    	{
    		super(index, label);
    	}
    	
    	@Override
    	public ItemStack createIcon() 
    	{
    		return new ItemStack(BlockInit.example_block);
    	}
    }
} 
