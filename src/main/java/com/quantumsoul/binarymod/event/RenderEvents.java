package com.quantumsoul.binarymod.event;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.gui.BitcoinOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID, value = Dist.CLIENT)
public class RenderEvents
{
    @SubscribeEvent
    public static void renderGameOverlayPost(RenderGameOverlayEvent.Post event)
    {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.ALL))
            BitcoinOverlay.draw(event);
    }
}
