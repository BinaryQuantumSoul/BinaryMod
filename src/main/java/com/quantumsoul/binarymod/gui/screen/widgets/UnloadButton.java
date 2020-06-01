package com.quantumsoul.binarymod.gui.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.gui.screen.ComputerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.util.TriConsumer;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

@OnlyIn(Dist.CLIENT)
public class UnloadButton extends Button
{
    private final static ResourceLocation TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/widgets.png");
    private final TriConsumer<String, Integer, Integer> toolTip;

    public UnloadButton(int xIn, int yIn, @Nullable TriConsumer<String, Integer, Integer> renderToolTip, Button.IPressable onPress)
    {
        super(xIn, yIn, 18, 18, "", onPress);
        toolTip = renderToolTip;
    }

    @Override
    public void render(int xMouse, int yMouse, float p3)
    {
        isHovered = x <= xMouse && xMouse < x + width && y <= yMouse && yMouse < y + height;

        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        this.blit(this.x, this.y, isHovered() ? this.width : 0, 0, this.width, this.height);
    }

    @Override
    public void renderToolTip(int x, int y)
    {
        if(toolTip != null)
            toolTip.accept(I18n.format("gui.binarymod.computer_unload"), x, y);
    }
}