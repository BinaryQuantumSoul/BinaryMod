package com.quantumsoul.binarymod;

import com.quantumsoul.binarymod.init.*;
import com.quantumsoul.binarymod.init.NetworkInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.quantumsoul.binarymod.BinaryMod.MOD_ID;

@Mod(MOD_ID)
public class BinaryMod
{
    public static final String MOD_ID = "binarymod";

    public BinaryMod()
    {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        BlockInit.BLOCKS.register(eventBus);
        BlockInit.MACHINES.register(eventBus);
        ItemInit.ITEMS.register(eventBus);
        RecipeInit.RECIPES.register(eventBus);
        ContainerInit.CONTAINERS.register(eventBus);
        TileEntityInit.TILE_ENTITIES.register(eventBus);
        EntityInit.ENTITIES.register(eventBus);
        BiomeInit.BIOMES.register(eventBus);
        DimensionInit.MOD_DIMENSIONS.register(eventBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        NetworkInit.initPackets();
        DeferredWorkQueue.runLater(GenerationInit::initOres);
        DeferredWorkQueue.runLater(GenerationInit::initSpawns);
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        RenderInit.initBlockRenders();
        RenderInit.initEntityRenders();
        RenderInit.initTileEntityRenders();
        ScreenInit.initScreens();
    }
}

//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

/*
    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }*/

//MinecraftForge.EVENT_BUS.register(this);

/*
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }*/
