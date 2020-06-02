package com.quantumsoul.binarymod.client.gui;

import com.quantumsoul.binarymod.BinaryMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import static com.quantumsoul.binarymod.util.BitcoinUtils.evaluateInventory;
import static com.quantumsoul.binarymod.util.BitcoinUtils.getBitcoinString;

public class BitcoinOverlay
{
    private final static ResourceLocation TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/widgets.png");

    public static void draw(RenderGameOverlayEvent.Post event)
    {
        int x = event.getWindow().getScaledWidth();
        int y = event.getWindow().getScaledHeight();

        Minecraft minecraft = Minecraft.getInstance();

        double count = evaluateInventory(minecraft.player.inventory);
        if (count > 0.0D)
        {
            minecraft.fontRenderer.drawString(getBitcoinString(count), x - 40, y - 15, 0xFFFFFF);
            GuiUtils.drawContinuousTexturedBox(TEXTURE, x - 55, y - 18, 0, 19, 14, 14, 256, 256, 0, 0.0F);
        }
    }
}
