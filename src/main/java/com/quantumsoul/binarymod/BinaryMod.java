package com.quantumsoul.binarymod;

import com.quantumsoul.binarymod.init.*;
import com.quantumsoul.binarymod.init.NetworkInit;
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

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        SoundInit.SOUNDS.register(eventBus);
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

    private void commonSetup(final FMLCommonSetupEvent event)
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