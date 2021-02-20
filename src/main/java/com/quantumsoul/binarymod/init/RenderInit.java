package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.client.render.block.BackdoorLoader;
import com.quantumsoul.binarymod.client.render.entity.*;
import com.quantumsoul.binarymod.client.render.tileentity.BitcoinTileRenderer;
import com.quantumsoul.binarymod.client.render.tileentity.BlockProgTileRenderer;
import com.quantumsoul.binarymod.client.render.tileentity.FactoryTileRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderInit
{
    @SubscribeEvent
    public static void initBlockRenders(final ModelRegistryEvent event)
    {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(BinaryMod.MOD_ID + ":backdoor_loader"), new BackdoorLoader());
    }

    public static void initEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ONE.get(), OneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ZERO.get(), ZeroRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BUG.get(), BugRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.VIRUS.get(), VirusRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.VOID_SOUL.get(), VoidSoulRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WORM.get(), WormRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BULLET.get(), BulletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.FLYER.get(), FlyerRenderer::new);
    }

    public static void initTileEntityRenders()
    {
        ClientRegistry.bindTileEntityRenderer(TileEntityInit.FEEDER.get(), r -> new FactoryTileRenderer(r, "feeder"));
        ClientRegistry.bindTileEntityRenderer(TileEntityInit.HEALER.get(), r -> new FactoryTileRenderer(r, "healer"));
        ClientRegistry.bindTileEntityRenderer(TileEntityInit.REPAIRER.get(), r -> new FactoryTileRenderer(r, "repairer"));

        ClientRegistry.bindTileEntityRenderer(TileEntityInit.BITCOIN_MINER.get(), BitcoinTileRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityInit.BLOCK_PROGRAMMER.get(), BlockProgTileRenderer::new);
    }
}
