package com.quantumsoul.binarymod.gui;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.item.BitcoinItem;
import com.quantumsoul.binarymod.client.render.tileentity.BitcoinTileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class BitcoinOverlay
{
    private final static Minecraft MC = Minecraft.getInstance();
    private final static ResourceLocation TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/widgets.png");

    public static void draw(RenderGameOverlayEvent.Post event)
    {
        int x = event.getWindow().getScaledWidth();
        int y = event.getWindow().getScaledHeight();

        double count = evaluateInventory();
        if (count > 0.0D)
        {
            MC.fontRenderer.drawString(BitcoinTileRenderer.getBitcoinString(count), x - 40, y - 15, 0xFFFFFF);
            GuiUtils.drawContinuousTexturedBox(TEXTURE, x - 55, y - 18, 0, 19, 14, 14, 256, 256, 0, 0.0F);
        }
    }

    private static double evaluateInventory()
    {
        PlayerInventory inv = MC.player.inventory;

        int count = 0;
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            Item item = stack.getItem();
            if(item instanceof BitcoinItem)
                count += ((BitcoinItem) item).getValue() * stack.getCount();
        }

        return count;
    }
}
