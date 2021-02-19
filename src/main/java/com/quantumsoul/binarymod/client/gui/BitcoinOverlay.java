package com.quantumsoul.binarymod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.compat.config.GUIConfig;
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
        int x, y, j, k;

        GUIConfig.HUDPosition pos = GUIConfig.btcPosition.get();
        if (pos.left)
        {
            x = 0;
            j = GUIConfig.btcX.get();
        }
        else
        {
            x = event.getWindow().getScaledWidth();
            j = -GUIConfig.btcX.get();
        }

        if (pos.top)
        {
            y = 0;
            k = GUIConfig.btcY.get();
        }
        else
        {
            y = event.getWindow().getScaledHeight();
            k = -GUIConfig.btcY.get();
        }

        Minecraft minecraft = Minecraft.getInstance();
        MatrixStack matrixStack = event.getMatrixStack();

        double count = evaluateInventory(minecraft.player.inventory);
        if (count > 0.0D)
        {
            minecraft.fontRenderer.drawString(matrixStack, getBitcoinString(count), x + j, y + k, 0xFFFFFF);
            GuiUtils.drawContinuousTexturedBox(matrixStack, TEXTURE, x + j - 15, y + k - 3, 0, 19, 14, 14, 256, 256, 0, 0.0F);
        }
    }
}
