package com.quantumsoul.binarymod.client.gui.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.quantumsoul.binarymod.BinaryMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.util.TriConsumer;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class UnloadButton extends Button
{
    private final static ResourceLocation TEXTURE = new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/widgets.png");
    private final ToolTipRenderer toolTip;

    public UnloadButton(int xIn, int yIn, @Nullable ToolTipRenderer renderToolTip, Button.IPressable onPress)
    {
        super(xIn, yIn, 18, 18, new StringTextComponent(""), onPress);
        toolTip = renderToolTip;
    }

    @Override
    public void render(MatrixStack matrixStack, int xMouse, int yMouse, float p3)
    {
        isHovered = x <= xMouse && xMouse < x + width && y <= yMouse && yMouse < y + height;

        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        this.blit(matrixStack, this.x, this.y, isHovered() ? this.width : 0, 0, this.width, this.height);
    }

    @Override
    public void renderToolTip(MatrixStack matrixStack, int x, int y)
    {
        if(toolTip != null)
            toolTip.render(matrixStack, new TranslationTextComponent("gui.binarymod.computer_unload"), x, y);
    }

    public static interface ToolTipRenderer
    {
        void render(MatrixStack matrixStack, ITextComponent text, int x, int y);
    }
}