package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.render.*;
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
    }
}
