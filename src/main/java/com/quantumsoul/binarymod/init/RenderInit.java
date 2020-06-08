package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.client.render.entity.*;
import com.quantumsoul.binarymod.client.render.tileentity.BitcoinTileRenderer;
import com.quantumsoul.binarymod.client.render.tileentity.BlockProgTileRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderInit
{
    public static void initEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ONE.get(), OneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ZERO.get(), ZeroRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BUG.get(), BugRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.VIRUS.get(), VirusRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.VOID_SOUL.get(), VoidSoulRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WORM.get(), WormRenderer::new);
    }

    public static void initTileEntityRenders()
    {
        ClientRegistry.bindTileEntityRenderer(TileEntityInit.BITCOIN_MINER.get(), BitcoinTileRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityInit.BLOCK_PROGRAMMER.get(), BlockProgTileRenderer::new);
    }
}
